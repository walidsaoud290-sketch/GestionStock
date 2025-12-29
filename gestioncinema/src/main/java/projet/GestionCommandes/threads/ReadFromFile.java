package projet.GestionCommandes.threads;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

// waliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiid
public class ReadFromFile extends Thread {
    private String path;
    private List<Object> result;
    private boolean completed = false;
    private Exception exception = null;

    public ReadFromFile(String path) {
        this.path = path;
        this.result = new ArrayList<>();
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object data = ois.readObject();
            // le premiere chose hya n3rfou wach data hiya liste wela wahda
            if (data instanceof List) {
                synchronized (this) {
                    result = new ArrayList<>((List<?>) data);
                }
            }// hna ki tkon wahda ghir zednaha l liste
             else {
                synchronized (this) {
                    result.add(data);
                }
            }
        } catch (Exception e) {
            exception = e;
        } finally {
            completed = true;
            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    public List<Object> waitForResult() throws InterruptedException {
        synchronized (this) {
            while (!completed) {
                this.wait();
            }
        }
        
        if (exception != null) {
            throw new RuntimeException("Erreur lors de la lecture", exception);
        }
        
        return new ArrayList<>(result);
    }

    public List<Object> getResult() {
        if (!completed) {
            throw new IllegalStateException("Lecture pas encore terminée");
        }
        
        if (exception != null) {
            throw new RuntimeException("Erreur lors de la lecture", exception);
        }
        
        return new ArrayList<>(result);
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean hasError() {
        return exception != null;
    }

    public Exception getException() {
        return exception;
    }
}