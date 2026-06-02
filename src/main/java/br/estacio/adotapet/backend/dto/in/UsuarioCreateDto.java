package br.estacio.adotapet.backend.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Schema(description = "Recebe os dados para criação de um novo usuário")
public record UsuarioCreateDto(
        @Schema(description = "Nome do novo usuário")
        @NotBlank(message = "O nome deve ser preenchido.")
        String nome,

        @Schema(description = "E-mail do novo usuário")
        @Email(message = "E-mail com formato inválido.")
        @NotBlank(message = "O e-mail deve ser preenchido.")
        String email,

        @Schema(description = "Senha do novo usuário")
        @NotEmpty(message = "A senha deve ser preenchida.")
        String senha,

        @Schema(description = "CPF do novo usuário")
        @NotBlank(message = "O CPF deve ser preenchido.")
        String cpf,

        @Schema(description = "Telefone do novo usuário")
        @NotBlank(message = "O telefone deve ser preenchido.")
        String telefone) {

    public String senha() {
        return (new BCryptPasswordEncoder()).encode(senha);
    }
}
