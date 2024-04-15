package d1w3u5.d1w3u5.security;


import d1w3u5.d1w3u5.entities.Dipendente;
import d1w3u5.d1w3u5.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Dipendente dipendente){
        return Jwts.builder()
                .issuedAt(
                        new Date(
                                System
                                        .currentTimeMillis())) // Data di emissione del token (IAT - Issued AT) in
                // millisecondi
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24
                                        * 7)) // Data di scadenza del token (Expiration Date) in millisecondi
                .subject(
                                dipendente.getEmail()) // Subject, ovvero a chi appartiene il token (Attenzione a non mettere info sensibili)
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes())) // Firmo il token con algoritmo HMAC passandogli il SEGRETO
                .compact();
    }

    public void verificaToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
            // Il metodo .parse(token) mi lancerà delle eccezioni in caso di token scaduto o token manipolato
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Per favore effettua di nuovo il login!");
            // Non importa quale eccezione verrà lanciata da .parse(), a me alla fine interessa che tutte come risultato abbiano 401
        }
    }



}
