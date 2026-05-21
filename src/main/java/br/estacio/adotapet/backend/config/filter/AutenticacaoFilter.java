package br.estacio.adotapet.backend.config.filter;

import br.estacio.adotapet.backend.config.service.JwtService;
import br.estacio.adotapet.backend.config.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro executado uma única vez por requisição, antes da validação de autorização no acesso à URL.
 * A finalidade principal é validar o token recebido no header da requisição, e em seguida, autenticar o usuário
 * proprietário do token.
 */
//@RequiredArgsConstructor
public class AutenticacaoFilter extends OncePerRequestFilter {

    // Contém os métodos para manuseio dos JSON Web Tokens.
    private final JwtService JwtService;

    // Para obter uma instância de UserDetails, necessária para autenticação com Spring Security.
    private final UsuarioService usuarioService;

    public AutenticacaoFilter(JwtService JwtService, UsuarioService usuarioService) {
        this.JwtService = JwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = obtemTokenNoRequestHeader(request);
        if (token != null && JwtService.tokenValido(token))
            autenticaUsandoToken(token);

        filterChain.doFilter(request, response);
    }

    /**
     * Obtém o token de autenticação no header da requisição.
     *
     * @param request Requisição interceptada pelo filtro.
     * @return O token presente no header da requisição.
     */
    private String obtemTokenNoRequestHeader(@NonNull HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer "))
            return null;

        return token;
    }

    /**
     * Autentica o usuário proprietário do token.
     *
     * @param token O token de autenticação.
     */
    private void autenticaUsandoToken(@NonNull String token) {

        String subject = JwtService.obtemSubject(token);
        UserDetails usuario;
        try {
            usuario = usuarioService.loadUserByUsername(subject);
        } catch (UsernameNotFoundException e) {
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

