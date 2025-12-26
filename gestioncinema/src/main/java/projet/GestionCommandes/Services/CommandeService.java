package projet.GestionCommandes.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import projet.GestionCommandes.Entities.Commande;


public interface CommandeService {
    ResponseEntity createCommande(Commande commande);
    ResponseEntity deleteCommandeById(Long id);
    void saviInFile(String path);
    ResponseEntity getByStatus(String status);
    ResponseEntity updateCommande(Long id,Commande commande);
    List<Commande> displayAllCommandes();
    ResponseEntity displayCommandeById(Long id);
    ResponseEntity readFromFile(String path);
}
