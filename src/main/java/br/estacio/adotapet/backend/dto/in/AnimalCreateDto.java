package br.estacio.adotapet.backend.dto.in;

import br.estacio.adotapet.backend.model.TbAnimalPorteEnum;
import br.estacio.adotapet.backend.model.TbAnimalSexoEnum;
import br.estacio.adotapet.backend.model.TbAnimalTipoEnum;

public record AnimalCreateDto(
        String nome,
        TbAnimalTipoEnum tipo,
        TbAnimalSexoEnum sexo,
        Integer idade,
        TbAnimalPorteEnum porte,
        String foto,
        String descricao,
        Long usuarioId) {
}
