package d1w3u5.d1w3u5.services;
import d1w3u5.d1w3u5.DAO.DipendenteDAO;
import d1w3u5.d1w3u5.DAO.DispositivoDAO;
import d1w3u5.d1w3u5.entities.Dipendente;
import d1w3u5.d1w3u5.entities.Dispositivo;
import d1w3u5.d1w3u5.exceptions.DipendenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoDAO dispositivoRepository;

    @Autowired
    private DipendenteDAO dipendenteRepository;
    public Dispositivo addDispositivo(Dispositivo dispositivo, String emailDipendente) {
        if (emailDipendente != null && !emailDipendente.isEmpty()) {
            Dipendente dipendente = dipendenteRepository.findByEmail(emailDipendente)
                    .orElseThrow(() -> new DipendenteNotFoundException("Dipendente with email " + emailDipendente + " not found"));
            dispositivo.setDipendente(dipendente);
        }
        return dispositivoRepository.save(dispositivo);
    }

    public List<Dispositivo> getAllDispositiviWithDipendenti() {
        return dispositivoRepository.findAllWithDipendenteEager();
    }
}
