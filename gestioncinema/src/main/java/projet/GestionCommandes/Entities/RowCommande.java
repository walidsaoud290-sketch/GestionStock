package projet.GestionCommandes.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString
public class RowCommande implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
    
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Product product;

    @Column(name = "prix_total")
    private double prixTotal;

    public RowCommande(int quantite, Commande commande, Product product) {
        this.quantite = quantite;
        this.commande = commande;
        this.product = product;
        // Avoid NPE when product is null; compute total when product is available
        this.prixTotal = (product != null) ? quantite * product.getPrix() : 0;
    }
}
