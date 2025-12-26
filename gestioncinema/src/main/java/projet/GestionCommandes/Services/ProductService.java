package projet.GestionCommandes.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import projet.GestionCommandes.Entities.Product;

public interface ProductService {
    ResponseEntity createProduct(Product product);
    ResponseEntity deleteProduct(Long id);
    void saviInFile(String path);
    ResponseEntity displayAllProducts();
    ResponseEntity updateProduct(Long id,Product product);
    ResponseEntity sortByPrice(boolean isASC);
    ResponseEntity displayProductById(Long id);
    ResponseEntity readFromFile(String path);
}
