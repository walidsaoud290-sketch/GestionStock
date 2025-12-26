package projet.GestionCommandes.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projet.GestionCommandes.Entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
    Client findByEmail(String email);
}
