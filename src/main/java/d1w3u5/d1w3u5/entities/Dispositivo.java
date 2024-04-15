package d1w3u5.d1w3u5.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dispositivi")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String stato;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dipendente_email")
    private Dipendente dipendente;

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", stato='" + stato + '\'' +
                ", dipendenteEmail=" + (dipendente != null ? dipendente.getEmail() : "None") +
                '}';
    }
}

