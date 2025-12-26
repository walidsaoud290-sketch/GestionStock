package projet.GestionCommandes.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projet.GestionCommandes.Entities.RowCommande;

@Repository
public interface RowCommandeRepository extends JpaRepository<RowCommande,Long>{
    
}
