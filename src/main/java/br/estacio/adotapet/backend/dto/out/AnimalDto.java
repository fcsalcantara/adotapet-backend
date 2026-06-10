package br.estacio.adotapet.backend.dto.out;

import br.estacio.adotapet.backend.model.TbAnimalPorteEnum;
import br.estacio.adotapet.backend.model.TbAnimalSexoEnum;
import br.estacio.adotapet.backend.model.TbAnimalTipoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AnimalDto(
        Long id,
        String nome,
        TbAnimalTipoEnum tipo,
        TbAnimalSexoEnum sexo,
        Integer idade,
        TbAnimalPorteEnum porte,
        String foto,
        String descricao,
        Long usuarioId,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dtCriacao) {

}
