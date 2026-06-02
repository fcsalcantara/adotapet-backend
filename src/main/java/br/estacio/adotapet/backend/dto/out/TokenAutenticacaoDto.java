package br.estacio.adotapet.backend.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contém o JSON Web Token de um usuário, após sua autenticação.
 *
 * @param token O JSON Web Token.
 */
@Schema(description = "Retorna ao cliente o JSON Web Token para autenticação nas requisições")
public record TokenAutenticacaoDto(
        @Schema(description = "JWT com o prefixo \"Bearer\" incluso")
        String token) {

}
