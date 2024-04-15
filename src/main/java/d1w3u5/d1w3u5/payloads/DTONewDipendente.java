package d1w3u5.d1w3u5.payloads;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
    public record DTONewDipendente(
            @NotEmpty(message = "Email non valida")
            String email,

            @NotEmpty(message = "Nome non valido")
            @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra i 3 e i 30 caratteri")
            String nome,

            @NotEmpty(message = "Cognome non valido")
            @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra i 3 e i 30 caratteri")
            String cognome,

            @NotEmpty(message = "La password è obbligatoria")
            @Size(min = 4, message = "La password deve avere come minimo 8 caratteri")
            String password) {
    }

