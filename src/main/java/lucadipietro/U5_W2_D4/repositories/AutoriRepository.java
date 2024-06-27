package lucadipietro.U5_W2_D4.repositories;

import lucadipietro.U5_W2_D4.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutoriRepository extends JpaRepository<Autore, UUID> {
    Optional<Autore> findByEmail(String email);
}
