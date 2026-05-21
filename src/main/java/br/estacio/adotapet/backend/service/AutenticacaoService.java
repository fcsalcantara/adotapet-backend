package br.estacio.adotapet.backend.service;

import br.estacio.adotapet.backend.config.service.JwtService;
import br.estacio.adotapet.backend.dto.in.AutenticacaoDto;
import br.estacio.adotapet.backend.dto.out.TokenAutenticacaoDto;
import br.estacio.adotapet.backend.exception.ExcecaoApi;
import br.estacio.adotapet.backend.exception.ExcecaoApiEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Contém a lógica relacionada à autenticação para acesso à API.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Nome identificador da autenticação para acesso ao Swagger.
    @Value("${adotapet.api.swagger.nome-acesso}")
    private String swaggerAcessoNome;

    /**
     * Obtém um token de autenticação para acesso ao serviço.
     *
     * @param dto Objeto do tipo AutenticacaoDto, contendo os dados para autenticação.
     * @return Objeto do tipo TokenAutenticacaoDto, contendo o token de autenticação, com o prefixo "Bearer" incluso.
     */
    public TokenAutenticacaoDto obtemTokenAutenticacao(AutenticacaoDto dto) {

        // O usuário de acesso ao Swagger não poderá obter token de autenticação.
        // Seu acesso será somente por HTTP Basic à página do Swagger.
        if (dto.login().equals(swaggerAcessoNome))
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaAutenticacaoAcessoApi);

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        try {

            // Aqui é executada a autenticação com o Spring Security.
            Authentication authentication = authenticationManager.authenticate(usernamePassword);

            return new TokenAutenticacaoDto(jwtService.geraToken(authentication));
        } catch (AuthenticationException e) {
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaAutenticacaoAcessoApi);
        }
    }
}
