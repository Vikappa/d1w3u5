package d1w3u5.d1w3u5.controllers;
import d1w3u5.d1w3u5.DAO.DipendenteDAO;
import d1w3u5.d1w3u5.DAO.DispositivoDAO;
import d1w3u5.d1w3u5.entities.Dipendente;
import d1w3u5.d1w3u5.entities.Dispositivo;
import d1w3u5.d1w3u5.exceptions.DipendenteNotFoundException;
import d1w3u5.d1w3u5.payloads.DTODispositivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

    @Autowired
    private DispositivoDAO dispositivoRepository;

    @Autowired
    private DipendenteDAO dipendenteRepository;

    @PostMapping
    public ResponseEntity<Dispositivo> addDispositivo(@RequestBody Dispositivo dispositivo, @RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            Dipendente dipendente = dipendenteRepository.findByEmail(email)
                    .orElseThrow(() -> new DipendenteNotFoundException("Dipendente con email " + email + " non trovato"));
            dispositivo.setDipendente(dipendente);
            dispositivo.setStato("Assegnato");
        } else {
            dispositivo.setStato("Libero");
        }

        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispositivo);
    }

    @PostMapping("/{email}")
    public ResponseEntity<Dispositivo> addDispositivoWithDipendente(
            @PathVariable String email,
            @RequestBody DTODispositivo dto) {

        Dipendente dipendente = dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new DipendenteNotFoundException("Dipendente con email " + email + " non trovato"));

        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setTipo(dto.getTipo());
        newDispositivo.setStato("Assegnato");
        newDispositivo.setDipendente(dipendente);

        Dispositivo savedDispositivo = dispositivoRepository.save(newDispositivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispositivo);
    }

    @GetMapping
    public ResponseEntity<List<Dispositivo>> getAllDispositiviWithDipendenti() {
        List<Dispositivo> dispositivi = dispositivoRepository.findAllWithDipendenteEager();
        if (dispositivi.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dispositivi);
    }

    @PutMapping("/{id}/associate/{email}")
    public ResponseEntity<Dispositivo> associateDispositivo(@PathVariable int id, @PathVariable String email) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo con id: " + id + " non trovato"));

        Dipendente dipendente = dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new DipendenteNotFoundException("Dipendente con email: " + email + " non trovato"));

        dispositivo.setDipendente(dipendente);
        dispositivo.setStato("Assegnato");

        Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
        return ResponseEntity.ok(updatedDispositivo);
    }

    @PutMapping("/{id}/disassociate")
    public ResponseEntity<Dispositivo> disassociateDispositivo(@PathVariable int id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo con id: " + id + " non trovato"));

        dispositivo.setDipendente(null);
        dispositivo.setStato("Libero");

        Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
        return ResponseEntity.ok(updatedDispositivo);
    }
}

