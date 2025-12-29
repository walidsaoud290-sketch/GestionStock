package projet.GestionCommandes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projet.GestionCommandes.Entities.Product;
import projet.GestionCommandes.Services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService ps;

    @GetMapping("/list")
    public ResponseEntity displayProducts(){
        return ps.displayAllProducts();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity displayProductById(@PathVariable Long id){
        return ps.displayProductById(id);
    }

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody Product product){
        return ps.createProduct(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id,@RequestBody Product product){
        return ps.updateProduct(id, product);
    }

    @GetMapping("/sort")
    public ResponseEntity sortProductsByPrice(@RequestParam Long etat){
        if(etat==1)return ps.sortByPrice(true);
        return ps.sortByPrice(false);
    }

    @GetMapping("/save")
    public void saveInFile(@RequestParam String path){
        ps.saviInFile(path);
    }

}
