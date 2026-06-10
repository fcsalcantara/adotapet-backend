package br.estacio.adotapet.backend.repository;

import br.estacio.adotapet.backend.model.TbAnimal;
import br.estacio.adotapet.backend.model.TbAnimalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository da tabela "adotapet.tb_animal".
 */
public interface TbAnimalRepository extends JpaRepository<TbAnimal, Long> {

    List<TbAnimal> findAllByStatusAndHabilitado(TbAnimalStatusEnum status, Boolean habilitado);
}
