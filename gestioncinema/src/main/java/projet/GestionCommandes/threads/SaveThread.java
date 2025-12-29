package projet.GestionCommandes.threads;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SaveThread extends Thread {
    private List<?> data;
    private String path;
    private volatile boolean completed = false;
    private volatile Exception exception = null;

    public SaveThread(List<?> data, String path) {
        this.data = data;
        this.path = path;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
            oos.flush();
        } catch (Exception e) {
            exception = e;
        } finally {
            completed = true;
        }
    }

    public boolean waitForCompletion(long timeoutMillis) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (!completed && (System.currentTimeMillis() - startTime) < timeoutMillis) {
            Thread.sleep(100);
        }
        return completed;
    }

    public boolean hasError() {
        return exception != null;
    }

    public Exception getException() {
        return exception;
    }

    public boolean isCompleted() {
        return completed;
    }
}