package projet.GestionCommandes.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import projet.GestionCommandes.Entities.Client;

public interface ClientService {
    ResponseEntity createClient(Client client);
    List<Client> displayAllClients();
    ResponseEntity displayClientById(Long id);
    ResponseEntity updateClient(Long id,Client client);
    ResponseEntity deleteClient(Long id);
    ResponseEntity getClientsByVille(String ville);
    ResponseEntity getClientByEmail(String email);
    ResponseEntity saviInFile(String path);
    ResponseEntity readFromFile(String path);
}
