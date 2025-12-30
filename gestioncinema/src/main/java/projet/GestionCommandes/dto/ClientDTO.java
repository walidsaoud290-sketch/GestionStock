package projet.GestionCommandes.dto;

import java.io.Serializable;

import projet.GestionCommandes.Entities.Client;


public class ClientDTO implements Serializable {
    private Long id;
    private String nom_client;
    private String email;
    private String ville;
    
    // Pas de commandes lazy ici !
    
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.nom_client = client.getNom_client();
        this.email = client.getEmail();
        this.ville = client.getVille();
    }

    @Override
    public String toString() {
        return "ClientDTO [id=" + id + ", nom_client=" + nom_client + ", email=" + email + ", ville=" + ville + "]";
    }
}
