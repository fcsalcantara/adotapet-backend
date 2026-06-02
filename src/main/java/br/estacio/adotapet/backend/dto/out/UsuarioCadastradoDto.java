package br.estacio.adotapet.backend.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UsuarioCadastradoDto(
        @Schema(description = "Identificador do usuário cadastrado")
        Long id,

        @Schema(description = "Nome do usuário cadastrado")
        String nome,

        @Schema(description = "E-mail do usuário cadastrado")
        String email,

        @Schema(description = "CPF do usuário cadastrado")
        String cpf,

        @Schema(description = "Telefone do usuário cadastrado")
        String telefone,

        @Schema(description = "Data de criação do usuário cadastrado")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dtCriacao) {

}
