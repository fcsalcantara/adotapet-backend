package br.estacio.adotapet.backend.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Recebe do cliente os dados para autenticar o acesso à API")
public record AutenticacaoDto(
        @Schema(description = "O login do usuário da API")
        @NotEmpty(message = "O login de usuário deve ser preenchido.")
        String login,

        @Schema(description = "A senha de acesso do usuário da API")
        @NotEmpty(message = "A senha deve ser preenchida")
        String senha) {

}
