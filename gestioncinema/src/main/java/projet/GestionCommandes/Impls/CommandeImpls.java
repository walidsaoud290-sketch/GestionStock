package projet.GestionCommandes.Impls;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import projet.GestionCommandes.Entities.Commande;
import projet.GestionCommandes.Repositorys.CommandeRepository;
import projet.GestionCommandes.Services.CommandeService;
import projet.GestionCommandes.dto.CommandeDTO;
import projet.GestionCommandes.threads.ReadFromFile;
import projet.GestionCommandes.threads.SaveIntoFile;

@Service
public class CommandeImpls implements CommandeService {
	@Autowired
	private CommandeRepository cr;

	public CommandeImpls(CommandeRepository cr) {
		this.cr = cr;
	}

	@Override
	public ResponseEntity createCommande(Commande commande) {
		cr.save(commande);
		return ResponseEntity.ok("ADD commande successfuly");
	}

	@Override
	public ResponseEntity deleteCommandeById(Long id) {
		Commande commandes = cr.findById(id).orElse(null);
		if (commandes != null) {
			cr.deleteById(id);
			return ResponseEntity.ok("Removed Commande successfuly ");
		}
		return ResponseEntity.status(404).body("Not found commande id:" + id);
	}

	@Override
	public ResponseEntity getByStatus(String status) {
		List<Commande> commandesByStatus = cr.findAll().stream().filter(e -> e.getStatus().equalsIgnoreCase(status))
				.toList();
		if (commandesByStatus.isEmpty()) {
			return ResponseEntity.status(404).body("status not found ");
		}
		return ResponseEntity.ok(commandesByStatus);
	}

	@Override
	public ResponseEntity updateCommande(Long id, Commande commande) {
		return cr.findById(id).map(e -> {
			e.setClient(commande.getClient());
			e.setDate_commande(commande.getDate_commande());
			e.setStatus(commande.getStatus());
			e.setId(commande.getId());
			e.setLignes(commande.getLignes());
			return ResponseEntity.ok(cr.save(e));
		}).orElse((ResponseEntity) ResponseEntity.status(404).body("Not found id" + id));
	}

	@Override
	public List<Commande> displayAllCommandes() {
		return cr.findAll();
	}

	@Override
	public ResponseEntity displayCommandeById(Long id) {
		Commande commande = cr.findById(id).orElse(null);
		if (commande != null) {
			return ResponseEntity.ok(commande);
		}
		return ResponseEntity.status(404).body("id not found for the commande");
	}

	@Override
	public ResponseEntity readFromFile(String path) {
		try {
			ReadFromFile readThread = new ReadFromFile(path);
			readThread.start();

			List<Object> data = readThread.waitForResult();

			if (readThread.hasError()) {
				return ResponseEntity.status(500)
						.body("Erreur lors de la lecture: " + readThread.getException().getMessage());
			}

			// Traiter les données lues (DTOs maintenant)
			if (data != null && !data.isEmpty()) {
				String builder = "";
				
				for (Object obj : data) {
					// Les objets ===> DTOs
					builder += obj.toString() + "\n";
				}
				
				return ResponseEntity.ok(builder.isEmpty() ? "Aucune donnée trouvée" : builder);
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
	public ResponseEntity saveIntoFile(String path) {
		try {
			List<Commande> commandes = cr.findAll();
			// Convertir en DTOs
			List<CommandeDTO> dtos = commandes.stream()
				.map(CommandeDTO::new)
				.collect(Collectors.toList());
			
			SaveIntoFile saveThread = new SaveIntoFile(dtos, path);
			saveThread.start();

			// Attendre la fin de l'écriture
			saveThread.waitForCompletion();

			if (saveThread.hasError()) {
				return ResponseEntity.status(500)
						.body("Erreur lors de l'écriture: " + saveThread.getException().getMessage());
			}
			return ResponseEntity.ok("Données sauvegardées avec succès dans " + path);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return ResponseEntity.status(500)
					.body("Écriture interrompue: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body("Erreur: " + e.getMessage());
		}
	}
}
