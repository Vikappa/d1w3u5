package d1w3u5.d1w3u5.DAO;

import d1w3u5.d1w3u5.entities.Dispositivo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DispositivoDAO extends JpaRepository<Dispositivo, Integer> {

    @EntityGraph(attributePaths = {"dipendente"})
    List<Dispositivo> findAllBy();

    @Query("SELECT d FROM Dispositivo d JOIN FETCH d.dipendente")
    List<Dispositivo> findAllWithDipendenteEager();

}
