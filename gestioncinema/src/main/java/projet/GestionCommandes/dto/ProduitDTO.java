package projet.GestionCommandes.dto;

import java.io.Serializable;

import projet.GestionCommandes.Entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO implements Serializable {
    private Long id;
    private String libelle;
    private Double prix;
    private Integer stock;
    
    public ProduitDTO(Product product) {
        this.id = product.getId();
        this.libelle = product.getLibelle();
        this.prix = product.getPrix();
        this.stock = product.getStock();
    }
}
