package d1w3u5.d1w3u5.controllers;

import d1w3u5.d1w3u5.DAO.DipendenteDAO;
import d1w3u5.d1w3u5.entities.Dipendente;
import d1w3u5.d1w3u5.exceptions.DipendenteNotFoundException;
import d1w3u5.d1w3u5.exceptions.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Autowired
    private DipendenteDAO dipendenteRepository;

     //GET {baseUrl}/dipendenti
    @GetMapping
    public List<Dipendente> getAllDipendenti() {
        List<Dipendente> result = dipendenteRepository.findAll();
        if (result.isEmpty()) {
            throw new DipendenteNotFoundException("Nessun dipendente trovato");
        }
        return result;
    }

     // GET {baseUrl}/dipendenti/{email}
    @GetMapping("/{email}")
    public Dipendente getByEmail(@PathVariable String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException("Email non valida: " + email);
        }
        return dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new DipendenteNotFoundException("Dipendente con email " + email + " non trovato"));
    }

    //POST {baseUrl}/dipendenti
    @PostMapping
    public Dipendente addDipendente(@RequestBody Dipendente dipendente) {
        if (!EMAIL_PATTERN.matcher(dipendente.getEmail()).matches()) {
            throw new InvalidEmailException("Email non valida: " + dipendente.getEmail());
        }
        return dipendenteRepository.save(dipendente);
    }
}
