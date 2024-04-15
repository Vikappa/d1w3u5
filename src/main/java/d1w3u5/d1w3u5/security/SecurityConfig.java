package d1w3u5.d1w3u5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable()); // Non voglio il form di login (avremo React per quello)
        httpSecurity.csrf(http -> http.disable()); // Non voglio la protezione da CSRF (per l'applicazione media non è necessaria e complicherebbe tutta la faccenda, anche lato FE)
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Non voglio le sessioni (perché utilizzeremo la token based authentication con JWT)

        // Aggiungere/Rimuovere determinate regole di protezione per gli endpoint
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/login", "/registrazione").permitAll().anyRequest().authenticated()); //Autorizzati login e registraiozione senza filtri, gli altri richiedono autenticazione


        return httpSecurity.build();
    }
}
