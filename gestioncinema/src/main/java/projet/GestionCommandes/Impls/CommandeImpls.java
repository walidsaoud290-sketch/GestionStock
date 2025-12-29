package projet.GestionCommandes.Impls;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import projet.GestionCommandes.Entities.Commande;
import projet.GestionCommandes.Entities.RowCommande;
import projet.GestionCommandes.Repositorys.CommandeRepository;
import projet.GestionCommandes.Services.CommandeService;
import projet.GestionCommandes.threads.ReadThread;
import projet.GestionCommandes.threads.SaveThread;
@Service
public class CommandeImpls implements CommandeService{
	@Autowired
    private CommandeRepository cr;
    public CommandeImpls(CommandeRepository cr){
        this.cr=cr;
    }
	@Override
	public ResponseEntity createCommande(Commande commande) {
	 	cr.save(commande);
		return ResponseEntity.ok("ADD commande successfuly");
	}

	@Override
	public ResponseEntity deleteCommandeById(Long id) {
		Commande commandes = cr.findById(id).orElse(null);
		if(commandes!=null){
			cr.deleteById(id);
			return ResponseEntity.ok("Removed Commande successfuly ");
		}
		return ResponseEntity.status(404).body("Not found commande id:"+id);
	}

	@Override
	public ResponseEntity getByStatus(String status) {
		List<Commande> commandesByStatus = cr.findAll().stream().filter(e->e.getStatus().equalsIgnoreCase(status)).toList();
		if(commandesByStatus.isEmpty()){
			return ResponseEntity.status(404).body("status not found ");
		}
		return ResponseEntity.ok(commandesByStatus);
	}

	@Override
	public ResponseEntity updateCommande(Long id,Commande commande) {
		return cr.findById(id).map(e->{
			e.setClient(commande.getClient());
			e.setDate_commande(commande.getDate_commande());
			e.setStatus(commande.getStatus());
			e.setId(commande.getId());
			e.setLignes(commande.getLignes());
		 	return ResponseEntity.ok(cr.save(e));
		}).
		orElse((ResponseEntity) ResponseEntity.status(404).body("Not found id"+id));
	}

	@Override
	public List<Commande> displayAllCommandes() {
		return cr.findAll();
	}

	@Override
	public ResponseEntity displayCommandeById(Long id) {
		Commande commande = cr.findById(id).orElse(null);
		if(commande!=null){
			return ResponseEntity.ok(commande);
		}
		return ResponseEntity.status(404).body("id not found for the commande");
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
                        cr.save((Commande) obj);
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
	public void saviInFile(String path) {
		new SaveThread(cr.findAll(), path).start();
	}
    
}
