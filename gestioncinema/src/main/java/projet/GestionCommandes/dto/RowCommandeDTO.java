package projet.GestionCommandes.dto;

import java.io.Serializable;

import projet.GestionCommandes.Entities.RowCommande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowCommandeDTO implements Serializable {
    private Long id;
    private int quantite;
    private double prixTotal;
    private Long commandeId;
    private Long productId;
    
    public RowCommandeDTO(RowCommande rowCommande) {
        this.id = rowCommande.getId();
        this.quantite = rowCommande.getQuantite();
        this.prixTotal = rowCommande.getPrixTotal();
        this.commandeId = rowCommande.getCommande() != null ? rowCommande.getCommande().getId() : null;
        this.productId = rowCommande.getProduct() != null ? rowCommande.getProduct().getId() : null;
    }
}
