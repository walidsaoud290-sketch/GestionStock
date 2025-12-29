package projet.GestionCommandes.Impls;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import projet.GestionCommandes.Entities.Product;
import projet.GestionCommandes.Entities.RowCommande;
import projet.GestionCommandes.Repositorys.ProductRepository;
import projet.GestionCommandes.Services.ProductService;
import projet.GestionCommandes.threads.ReadThread;
import projet.GestionCommandes.threads.SaveThread;
@Service
public class ProductImpls implements ProductService{
	@Autowired
    private ProductRepository pr;
	
    public ProductImpls(ProductRepository pr){
        this.pr=pr;
    }

	@Override
	public ResponseEntity createProduct(Product product) {
		pr.save(product);
		return ResponseEntity.ok("ADD PRODUCT SUCCESSFULY");
	}

	@Override
	public ResponseEntity deleteProduct(Long id) {
		if(pr.existsById(id)){
			pr.deleteById(id);
			return ResponseEntity.ok("Deleted product succssefuly with id "+id);
		}
		return ResponseEntity.status(404).body("Product id not found "+id);
	}

	@Override
	public ResponseEntity displayAllProducts() {
		return ResponseEntity.ok(pr.findAll());
	}

	@Override
	public ResponseEntity updateProduct(Long id,Product product) {
		return pr.findById(id).map(e->{
			e.setId(id);
			e.setLibelle(product.getLibelle());
			e.setStock(product.getStock());
			e.setPrix(product.getPrix());
			e.setLignes(product.getLignes());
			pr.save(e);
			return (ResponseEntity) ResponseEntity.ok("Updated product successfuly id "+id);
		}).orElse( (ResponseEntity) ResponseEntity.status(404).body("Not found product id "+id));
	}

	@Override
	public ResponseEntity sortByPrice(boolean isASC) {
		List<Product> products;
		if(isASC)
			products = pr.findAll().stream().sorted(Comparator.comparing(Product::getPrix)).toList();
		else
			products = pr.findAll().stream().sorted(Comparator.comparing(Product::getPrix).reversed()).toList();
		return products.size()>0 ? ResponseEntity.ok(products) : ResponseEntity.status(404).body("List product is empty");
	}

	@Override
	public ResponseEntity displayProductById(Long id) {
		Product product = pr.findById(id).orElse(null);
		return product!=null ? ResponseEntity.ok(product) : ResponseEntity.status(404).body("Not found product id "+id);
	}

	@Override
    public ResponseEntity readFromFile(String path) {
        try {
            ReadThread readThread = new ReadThread(path);
            readThread.start();
            
            // Attendre le résultat avec timeout (5 secondes)
            List<Object> data = readThread.waitForResult();
            
            if (readThread.hasError()) {
                return ResponseEntity.status(500)
                    .body("Erreur lors de la lecture: " + readThread.getException().getMessage());
            }
            
            // Traiter les données lues
            if (data != null && !data.isEmpty()) {
                // Supposons que vous sauvegardez les données dans la base
                for (Object obj : data) {
                    if (obj instanceof RowCommande) {
                        pr.save((Product) obj);
                    }
                }
                return ResponseEntity.ok("Données restaurées avec succès: " + data.size() + " éléments");
            }
            
            	return ResponseEntity.ok("Fichier vide");
            
        	} catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            	return ResponseEntity.status(500)
                	.body("Lecture interrompue: " + e.getMessage());
        	} catch (Exception e) {
            	return ResponseEntity.status(500)
                	.body("Erreur: " + e.getMessage());
        	}
    	}

	@Override
public void saviInFile(String path) {
    new SaveThread(pr.findAll(), path).start();;
}
}
