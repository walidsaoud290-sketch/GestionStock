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

import projet.GestionCommandes.Entities.RowCommande;
import projet.GestionCommandes.Services.RowCommandeService;

@RestController
@RequestMapping("/rowCommande")
public class RowCommandeController {
    @Autowired
    private RowCommandeService rcr;

    @GetMapping("/list")
    public ResponseEntity displayAllRows(){
        return rcr.displayAllRows();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity displayRowCommande(@PathVariable Long id){
        return rcr.displayRowById(id);
    }

    @PostMapping("/create")
    public ResponseEntity createRowCommande(@RequestBody RowCommande rowCommande){
        return rcr.createRowCommande(rowCommande);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatRowCommandes(@PathVariable Long id,@RequestBody RowCommande rowCommande){
        return rcr.updateRowCommandes(id, rowCommande);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRowCommandeById(@PathVariable Long id){
        return rcr.deleteRowCommandeById(id);
    }

    @GetMapping("/sort")
    public ResponseEntity sortRowCommandesByTotal(@PathVariable Integer etat){
        if(etat==1){
            return rcr.sortRowCommandesByTotal(true);
        }
       return rcr.sortRowCommandesByTotal(false);
    }

    @GetMapping("/save")
    public void save(@RequestParam String path){
        rcr.savInFile(path);
    }

}
