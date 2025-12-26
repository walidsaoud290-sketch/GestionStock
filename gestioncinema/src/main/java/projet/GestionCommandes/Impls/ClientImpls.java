package projet.GestionCommandes.Impls;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import projet.GestionCommandes.Entities.Client;
import projet.GestionCommandes.Entities.RowCommande;
import projet.GestionCommandes.Repositorys.ClientRepository;
import projet.GestionCommandes.Services.ClientService;
import projet.GestionCommandes.threads.ReadThread;
import projet.GestionCommandes.threads.SaveThread;
@Service
public class ClientImpls implements ClientService{

	@Autowired
    private ClientRepository cr;

    public ClientImpls(ClientRepository cr){
        this.cr=cr;
    }

	@Override
	public ResponseEntity createClient(Client client) {
		List<Client> clients = cr.findAll().stream().filter(e->e.getEmail().equalsIgnoreCase(client.getEmail())).toList();
		if(clients.isEmpty()){
			cr.save(client);
			return ResponseEntity.ok("ADD CLIENT SUCCESSFULY");
		}
		return ResponseEntity.status(404).body("Email already existe");
	}

	@Override
	public List<Client> displayAllClients() {
		return cr.findAll();
	}

	@Override
	public ResponseEntity displayClientById(Long id) {
        return cr.findById(id)
            .map(client -> ResponseEntity.ok(client))
            .orElse((ResponseEntity)ResponseEntity
                    .status(404)
                    .body("Client with id " + id + " not found"));
    }

	@Override
    public ResponseEntity updateClient(Long id, Client client) {
        return cr.findById(id)
            .map(e -> {
                e.setEmail(client.getEmail());
                e.setCommandes(client.getCommandes());
                e.setNom_client(client.getNom_client());
                e.setVille(client.getVille());
                cr.save(e);
                return ResponseEntity.ok("Client with id "+id+" is Updated successfuly");
            })
            .orElse((ResponseEntity)ResponseEntity
                    .status(404)
                    .body("Client with id " + id + " not found"));
    }

	@Override
    public ResponseEntity<?> deleteClient(Long id) {
        if (!cr.existsById(id)) {
            return ResponseEntity
                .status(404)
                .body("Client with id " + id + " not found");
        }

        cr.deleteById(id);
        return ResponseEntity.ok("Removed successfully");
    }

	@Override
    public ResponseEntity<?> getClientsByVille(String ville) {
        List<Client> clients = cr.findAll().stream()
            .filter(e -> e.getVille().equalsIgnoreCase(ville))
            .toList();

        if (clients.isEmpty()) {
            return ResponseEntity
                .status(404)
                .body("No clients found in ville: " + ville);
        }
        return ResponseEntity.ok(clients);
    }
	
    @Override
	public ResponseEntity<?> getClientByEmail(String email) {
        return cr.findAll().stream()
            .filter(e -> e.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .map(client -> ResponseEntity.ok(client)).orElse((ResponseEntity) ResponseEntity
                    .status(404)
                    .body("Client with email " + email + " not found"));
    }

    @Override
    public ResponseEntity readFromFile(String path) {
        try {
            ReadThread readThread = new ReadThread(path);
            readThread.start();
            
            // Attendre le résultat avec timeout (5 secondes)
            List<Object> data = readThread.waitForResult();
            
            if (readThread.hasError()) {
                return ResponseEntity.status(500)
                    .body("Erreur lors de la lecture: " + readThread.getException().getMessage());
            }
            
            // Traiter les données lues
            if (data != null && !data.isEmpty()) {
                // Supposons que vous sauvegardez les données dans la base
                for (Object obj : data) {
                    if (obj instanceof RowCommande) {
                        cr.save((Client) obj);
                    }
                }
                return ResponseEntity.ok("Données restaurées avec succès: " + data.size() + " éléments");
            }
            
            	return ResponseEntity.ok("Fichier vide");
            
        	} catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            	return ResponseEntity.status(500)
                	.body("Lecture interrompue: " + e.getMessage());
        	} catch (Exception e) {
            	return ResponseEntity.status(500)
                	.body("Erreur: " + e.getMessage());
        	}
    	}

    @Override
	public ResponseEntity saviInFile(String path) {
    	SaveThread saveThread = new SaveThread(cr.findAll(), path);
        saveThread.start();
        return ResponseEntity.ok("the data is sauvegarted in the file :"+path);
	}

	
}
