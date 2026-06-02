package br.estacio.adotapet.backend.config;

import br.estacio.adotapet.backend.config.filter.AutenticacaoFilter;
import br.estacio.adotapet.backend.config.service.JwtService;
import br.estacio.adotapet.backend.config.service.UsuarioAutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configurações de autorização e acesso à API.
 */
@Configuration
public class SecurityConfig {

    private final AutenticacaoFilter autenticacaoFilter;

    protected SecurityConfig(JwtService jwtService,
                             UsuarioAutenticacaoService usuarioAutenticacaoService,
                             AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        autenticacaoFilter = new AutenticacaoFilter(jwtService, usuarioAutenticacaoService);
        authenticationManagerBuilder.userDetailsService(usuarioAutenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private static final String[] SWAGGER_PATTERNS = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
    private static final String LOGIN_PATTERN = "/autenticacao/login";
    private static final String CHECK_PATTERN = "/api/info";

    /**
     * Determina regras de acesso e autorização aos endpoints do serviço.
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(SWAGGER_PATTERNS).hasRole(UsuarioAutenticacaoService.ROLE_SWAGGER))
                .httpBasic(withDefaults())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, CHECK_PATTERN).permitAll()
                        .requestMatchers(HttpMethod.POST, LOGIN_PATTERN).permitAll()
                        .anyRequest().hasAnyRole(UsuarioAutenticacaoService.ROLE_ACESSOAPI,
                                UsuarioAutenticacaoService.ROLE_USUARIO))
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
