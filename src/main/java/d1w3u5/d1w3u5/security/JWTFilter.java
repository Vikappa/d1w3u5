package d1w3u5.d1w3u5.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;



@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // 1. Controlliamo se nella richiesta corrente ci sia un Authorization Header, se non c'è --> 401

        if(authHeader == null || !authHeader.startsWith("Bearer ")) { // 2. Se non c'è o non inizia con Bearer --> 401
        String accessToken = authHeader.substring(7); // Bearer + spazio = 7 caratteri

            jwtTools.verificaToken(accessToken); // 3. Se non è valido --> 401

            filterChain.doFilter(request, response); // Vado al prossimo elemento della catena, passandogli gli oggetti request e response
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        String[] endpointAutorizzati = {"/login", "/registrazione"};

        for (String path : endpointAutorizzati) {
            if (pathMatcher.match(path, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

}
