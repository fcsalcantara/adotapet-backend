package br.estacio.adotapet.backend.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Recebe os dados para atualização de um usuário")
public record UsuarioUpdateDto(
        @Schema(description = "Identificador do usuário")
        @NotNull(message = "O id deve ser fornecido.")
        Long id,

        @Schema(description = "Novo nome do usuário")
        String nome,

        @Schema(description = "Novo e-mail do usuário")
        @Email(message = "E-mail com formato inválido.")
        String email,

        @Schema(description = "Novo telefone do usuário")
        String telefone) {

}
