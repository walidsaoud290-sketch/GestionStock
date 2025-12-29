package projet.GestionCommandes.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projet.GestionCommandes.Entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
}
