package br.estacio.adotapet.backend.config.service;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * Contém os métodos necessários para a utilização do JWT na autenticação.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class JwtService {

    // Chave RSA privada, utilizada para assinar um token JWT.
    private final RSAPrivateKey rsaPrivateKey;

    // Chave RSA pública, utilizada para validar a assinatura de um token.
    // *Está chave pública não deve ser fornecida ao cliente da API.
    private final RSAPublicKey rsaPublicKey;

    // Tempo de vida, em milissegundos, de um token.
    @Value("${adotapet.jwt.expiracao-milisegundos}")
    private String expiration;

    /**
     * Gera um novo token de autenticação.
     *
     * @param login Um email válido.
     * @return O token de autenticação com o prefixo "Bearer" já adicionado.
     */
    public String geraToken(@NonNull String login) {

        Date inicioValidade = new Date();
        Date finalValidade = new Date(inicioValidade.getTime() + Long.parseLong(expiration));

        JwtBuilder jwtBuilder = Jwts.builder()
                .issuer("backend.adotapet.estacio.br")
                .subject(login)
                .notBefore(inicioValidade)
                .issuedAt(inicioValidade)
                .expiration(finalValidade)
                .signWith(rsaPrivateKey, Jwts.SIG.RS256);

        return "Bearer " + jwtBuilder.compact();
    }

    /**
     * Determina a validade de um token.
     *
     * @param token Um token de autenticação.
     * @return Verdadeiro quando um token é válido, ou falso quando não é.
     */
    public boolean tokenValido(String token) {
        return obtemClaimsJwt(token) != null;
    }

    /**
     * Obtém o subject nas claims de um JWT, que corresponde ao username do proprietário do token.
     *
     * @param token Um token de autenticação.
     * @return O subject (username) do proprietário do token.
     */
    public String obtemSubject(String token) {

        Jws<Claims> claimsJws = obtemClaimsJwt(retiraBearerDoToken(token));

        return claimsJws != null ? claimsJws.getPayload().getSubject() : null;
    }

    /**
     * Obtém as claims de um token JWT assinado.
     *
     * @param token Um token JWT.
     * @return As claims presentes no token JWT.
     */
    private Jws<Claims> obtemClaimsJwt(String token) {

        try {
            return Jwts.parser().verifyWith(rsaPublicKey).build().parseSignedClaims(retiraBearerDoToken(token));
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Remove o prefixo "Bearer" do token de autenticação.
     *
     * @param token O token com o prefixo "Bearer" incluso.
     * @return O token sem o prefixo "Bearer".
     */
    private String retiraBearerDoToken(String token) {
        return token != null ? token.replace("Bearer ", "") : null;
    }
}

