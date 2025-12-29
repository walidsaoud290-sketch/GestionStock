package projet.GestionCommandes.Entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
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
    private double prixTotal = quantite*product.getPrix();
}
