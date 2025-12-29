package projet.GestionCommandes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projet.GestionCommandes.Entities.Client;
import projet.GestionCommandes.Services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService cs;

    @GetMapping("/list")
    public List<Client> displayAllClients(){
        return cs.displayAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id){
        return cs.deleteClient(id);
    }

    @PostMapping("/create")
    public ResponseEntity createClient(@RequestBody Client client){
        return cs.createClient(client);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable Long id,@RequestBody Client client){
        ResponseEntity r =  cs.updateClient(id, client);
        return r;
    }

    @GetMapping("/list/{id}")
    public ResponseEntity displayClient(@PathVariable Long id){
        return cs.displayClientById(id);
    }

    @GetMapping("/ville")
    public ResponseEntity getClientsByVille(@RequestParam String ville) {
        return cs.getClientsByVille(ville);
    }

    @GetMapping("/email")
    public ResponseEntity getClientsByEmail(@RequestParam String email) {
        return cs.getClientByEmail(email);
    }
    

    @GetMapping("/save")
    public ResponseEntity savInFile(@RequestParam String path){
        return cs.saviInFile(path);
    }
}
