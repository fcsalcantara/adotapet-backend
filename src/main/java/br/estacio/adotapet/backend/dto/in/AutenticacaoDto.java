package br.estacio.adotapet.backend.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Recebe do cliente os dados para autenticar o acesso à API")
public record AutenticacaoDto(
        @Schema(description = "O email do usuário da API")
        @Email(message = "E-mail com formato inválido.")
        @NotBlank(message = "O email de usuário deve ser preenchido.")
        String email,

        @Schema(description = "A senha de acesso do usuário da API")
        @NotBlank(message = "A senha deve ser preenchida.")
        String senha) {

}
