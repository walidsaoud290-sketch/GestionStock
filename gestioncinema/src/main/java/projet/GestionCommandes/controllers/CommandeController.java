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

import projet.GestionCommandes.Entities.Commande;
import projet.GestionCommandes.Services.CommandeService;

@RestController
@RequestMapping("/commande")

public class CommandeController {
    @Autowired
    private CommandeService cs;

    @GetMapping("/list")
    public List<Commande> displayAllCommandes(){
        return cs.displayAllCommandes();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity displayCommandeById(@PathVariable Long id){
        return cs.displayCommandeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCommandeById(@PathVariable Long id){
       return cs.deleteCommandeById(id);
    }

    @PostMapping("/create")
    public ResponseEntity createCommande(@RequestBody Commande commande){
        return cs.createCommande(commande);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCommande(@RequestBody Commande commande,@PathVariable Long id){
        return cs.updateCommande(id, commande);
    }

    @GetMapping("/listStatus")
    public ResponseEntity getByStatuList(@RequestParam String status){
        return cs.getByStatus(status);
    }

    @GetMapping("/save")
    public void saveInFile(@RequestParam String path){
        cs.saviInFile(path);
    }
}
