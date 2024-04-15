package d1w3u5.d1w3u5.services;
import d1w3u5.d1w3u5.DAO.DipendenteDAO;
import d1w3u5.d1w3u5.entities.Dipendente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteDAO dipendenteRepository;

    public Optional<Dipendente> getDipendenteByEmail(String email) {
        return dipendenteRepository.findByEmail(email);
    }

    public void addDipendente(Dipendente dipendente) {
        dipendenteRepository.save(dipendente);
    }

    public void deleteDipendente(String email) {
        dipendenteRepository.deleteByEmail(email);
    }

    public void updateDipendente(Dipendente dipendente) {
        dipendenteRepository.save(dipendente);
    }

    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }

    public List<Dipendente> getDipendentiByNome(String nome) {
        return dipendenteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Dipendente> getDipendentiByCognome(String cognome) {
        return dipendenteRepository.findByCognomeContainingIgnoreCase(cognome);
    }
}

