package projet.GestionCommandes.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import projet.GestionCommandes.Entities.Commande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDTO implements Serializable {
    private Long id;
    private LocalDateTime date_commande;
    private String status;
    private Long clientId;
    
    public CommandeDTO(Commande commande) {
        this.id = commande.getId();
        this.date_commande = commande.getDate_commande();
        this.status = commande.getStatus();
        this.clientId = commande.getClient() != null ? commande.getClient().getId() : null;
    }
}
