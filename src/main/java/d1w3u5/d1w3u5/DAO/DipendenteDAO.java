package d1w3u5.d1w3u5.DAO;

import d1w3u5.d1w3u5.entities.Dipendente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DipendenteDAO extends JpaRepository<Dipendente, String> {
    Optional<Dipendente> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    List<Dipendente> findByNomeContainingIgnoreCase(String nome);

    List<Dipendente> findByCognomeContainingIgnoreCase(String cognome);
}
