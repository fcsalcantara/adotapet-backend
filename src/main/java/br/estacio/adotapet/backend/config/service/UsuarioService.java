package br.estacio.adotapet.backend.config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Para retornar uma implementação de UserDetails.
 * <br>
 * A implementação de UserDetails é necessária na autenticação com Spring Security.
 */
@Service
public class UsuarioService implements UserDetailsService {

    public static final String ROLE_ACESSOAPI = "ACESSOAPI";
    public static final String ROLE_SWAGGER = "SWAGGER";

    // Nome identificador da autenticação para acesso à API.
    @Value("${adotapet.api.nome-acesso}")
    private String apiAcessoNome;

    // A senha de acesso à API.
    @Value("${adotapet.api.senha-acesso}")
    private String apiAcessoSenha;

    // Nome identificador da autenticação para acesso ao Swagger.
    @Value("${adotapet.api.swagger.nome-acesso}")
    private String swaggerAcessoNome;

    // Senha de acesso ao Swagger
    @Value("${adotapet.api.swagger.senha-acesso}")
    private String swaggerAcessoSenha;

    /**
     * Sobrescrito para obter uma instância de UserDetails, que representa um usuário da API.
     *
     * @param username O username ou login do usuário.
     * @return Uma implementação de UserDetails, com os dados do usuário retornado na busca.
     * @throws UsernameNotFoundException Lançada quando o username do usuário não foi encontrado na busca.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (apiAcessoNome.equals(username))
            return User.builder()
                    .username(apiAcessoNome)
                    .password(passwordEncoder.encode(apiAcessoSenha))
                    .roles(ROLE_ACESSOAPI)
                    .build();

        if (swaggerAcessoNome.equals(username))
            return User.builder()
                    .username(swaggerAcessoNome)
                    .password(passwordEncoder.encode(swaggerAcessoSenha))
                    .roles(ROLE_SWAGGER)
                    .build();

        throw new UsernameNotFoundException("Dados do usuário não encontrados.");
    }
}
