package projet.GestionCommandes.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import projet.GestionCommandes.Entities.RowCommande;

public interface RowCommandeService {
    ResponseEntity displayAllRows();
    ResponseEntity createRowCommande(RowCommande Rcommande);
    ResponseEntity deleteRowCommandeById(Long id);
    ResponseEntity updateRowCommandes(Long id,RowCommande rowCommande);
    ResponseEntity displayRowById(Long id);
    ResponseEntity sortRowCommandesByTotal(boolean isASC);
    void savInFile(String path);
    ResponseEntity readFromFile(String path);

}
