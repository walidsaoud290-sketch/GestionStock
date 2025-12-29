package projet.GestionCommandes.Impls;

import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import projet.GestionCommandes.Entities.RowCommande;
import projet.GestionCommandes.Repositorys.RowCommandeRepository;
import projet.GestionCommandes.Services.RowCommandeService;
import projet.GestionCommandes.threads.ReadThread;
import projet.GestionCommandes.threads.SaveThread;

@Service
@AllArgsConstructor
public class RowCommandeImpls implements RowCommandeService{
    private RowCommandeRepository rcr;

	@Override
	public ResponseEntity displayAllRows() {
		return ResponseEntity.ok(rcr.findAll());
	}

	@Override
	public ResponseEntity createRowCommande(RowCommande Rcommande) {
		rcr.save(Rcommande);
		return ResponseEntity.ok("Created rowCommande successfuly");
	}

	@Override
	public ResponseEntity deleteRowCommandeById(Long id) {
		if(rcr.existsById(id)){
			rcr.deleteById(id);
			return ResponseEntity.ok("Deleted succssfuly id "+id);
		}
		return ResponseEntity.status(404).body("doesnt existe id"+id);
	}

	@Override
	public ResponseEntity updateRowCommandes(Long id, RowCommande rowCommande) {
		return rcr.findById(id).map(e->{
            e.setCommande(rowCommande.getCommande());
            e.setId(rowCommande.getId());
            e.setProduct(rowCommande.getProduct());
            e.setQuantite(rowCommande.getQuantite());
            e.setPrixTotal(rowCommande.getPrixTotal());
			rcr.save(e);
            return (ResponseEntity) ResponseEntity.ok("Update successfuly Row Commande id :"+id);
        }).orElse((ResponseEntity)ResponseEntity.status(404).body("doesnt existe rowCommande id :"+id));
	}

	@Override
	public ResponseEntity displayRowById(Long id) {
		if(rcr.existsById(id)){
			return ResponseEntity.ok(rcr.findById(id).get());
		}
		return ResponseEntity.status(404).body("doesnt existe rowCommande id "+id);
	}

	@Override
	public ResponseEntity sortRowCommandesByTotal(boolean isASC) {
		List<RowCommande> rowCommandes;
		if(isASC){
			rowCommandes = rcr.findAll().stream().sorted(Comparator.comparing(RowCommande::getPrixTotal)).toList();
		}else{
			rowCommandes = rcr.findAll().stream().sorted(Comparator.comparing(RowCommande::getPrixTotal).reversed()).toList();	
		}
		return rowCommandes.size()>0 ? ResponseEntity.ok(rowCommandes) : ResponseEntity.ok("the table rowCommande is empty");
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
                        rcr.save((RowCommande) obj);
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
	public void savInFile(String path) {
		new SaveThread(rcr.findAll(), path).start();
	}
}
