package projet.GestionCommandes.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projet.GestionCommandes.Entities.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long>{
    
}
