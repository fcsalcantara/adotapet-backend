package br.estacio.adotapet.backend.service;

import br.estacio.adotapet.backend.dto.in.AnimalCreateDto;
import br.estacio.adotapet.backend.dto.out.AnimalDto;
import br.estacio.adotapet.backend.exception.ExcecaoApi;
import br.estacio.adotapet.backend.exception.ExcecaoApiEnum;
import br.estacio.adotapet.backend.model.TbAnimal;
import br.estacio.adotapet.backend.model.TbAnimalStatusEnum;
import br.estacio.adotapet.backend.model.TbUsuario;
import br.estacio.adotapet.backend.repository.TbAnimalRepository;
import br.estacio.adotapet.backend.repository.TbUsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contém a lógica relacionada ao CRUD de Animais (Pets).
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class AnimalService {

    private final TbAnimalRepository tbAnimalRepository;
    private final TbUsuarioRepository tbUsuarioRepository;

    public AnimalDto create(@NonNull AnimalCreateDto dto) {

        TbAnimal animal = new TbAnimal();
        animal.setNome(dto.nome());
        animal.setTipo(dto.tipo());
        animal.setSexo(dto.sexo());
        animal.setIdade(dto.idade());
        animal.setPorte(dto.porte());
        animal.setFoto(dto.foto());
        animal.setDescricao(dto.descricao());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long usuarioId;
        if (userDetails instanceof TbUsuario) {
            usuarioId = ((TbUsuario) userDetails).getId();
            if (usuarioId.equals(dto.usuarioId()))
                animal.setUsuario(tbUsuarioRepository.findById(usuarioId)
                        .orElseThrow(() -> new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosUsuario)));
        } else
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosUsuario);

        animal.setDtCriacao(LocalDateTime.now());

        animal = tbAnimalRepository.save(animal);

        return new AnimalDto(
                animal.getId(),
                animal.getNome(),
                animal.getTipo(),
                animal.getSexo(),
                animal.getIdade(),
                animal.getPorte(),
                animal.getFoto(),
                animal.getDescricao(),
                animal.getUsuario().getId(),
                animal.getDtCriacao());
    }

    public List<AnimalDto> readAll() {

        List<TbAnimal> animais =
                tbAnimalRepository.findAllByStatusAndHabilitado(TbAnimalStatusEnum.DISPONIVEL, true);

        if (animais.isEmpty())
            throw new ExcecaoApi(ExcecaoApiEnum.ListaAnimaisVazia);

        List<AnimalDto> animaisDtos = new ArrayList<>();
        for (TbAnimal animal : animais)
            animaisDtos.add(new AnimalDto(
                    animal.getId(),
                    animal.getNome(),
                    animal.getTipo(),
                    animal.getSexo(),
                    animal.getIdade(),
                    animal.getPorte(),
                    animal.getFoto(),
                    animal.getDescricao(),
                    animal.getUsuario().getId(),
                    animal.getDtCriacao()));

        return animaisDtos;
    }

    public AnimalDto read(@NotNull Long id) {

        TbAnimal animal = tbAnimalRepository.findById(id)
                .orElseThrow(() -> new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosAnimal));

        return new AnimalDto(
                animal.getId(),
                animal.getNome(),
                animal.getTipo(),
                animal.getSexo(),
                animal.getIdade(),
                animal.getPorte(),
                animal.getFoto(),
                animal.getDescricao(),
                animal.getUsuario().getId(),
                animal.getDtCriacao());
    }

    @Transactional
    public Object update(@NonNull Object dto) {

        // TODO: implementar...

        return null;
    }

    @Transactional
    public String delete(Long id) {

        // TODO: implementar...

        return null;
    }
}
