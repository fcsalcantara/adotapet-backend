package br.estacio.adotapet.backend.rest;

import br.estacio.adotapet.backend.config.OpenApiConfig;
import br.estacio.adotapet.backend.dto.in.AnimalCreateDto;
import br.estacio.adotapet.backend.dto.out.AnimalDto;
import br.estacio.adotapet.backend.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller para endpoints relacionados ao CRUD de Usuários.
 */
@RequestMapping(AnimalController.BASE_MAPPING)
@SecurityRequirement(name = OpenApiConfig.SECURITYSCHEME_NAME)
@Tag(name = AnimalController.BASE_MAPPING, description = "Endpoints relacionados ao CRUD de Animais (Pets)")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class AnimalController {

    /*
     * Constantes para mapeamento dos endpoints:
     */
    static final String BASE_MAPPING = "/animais";
    private static final String CREATE_MAPPING = "create";
    private static final String READ_MAPPING = "read";
    private static final String UPDATE_MAPPING = "update";
    private static final String DELETE_MAPPING = "delete";

    private final AnimalService animalService;

    @PostMapping(CREATE_MAPPING)
    @Operation(summary = "Criação de animal (pet)",
            description = "Cria um novo animal (pet)")
    protected ResponseEntity<AnimalDto> create(@RequestBody @Valid @NonNull AnimalCreateDto dto) {

        AnimalDto resp = animalService.create(dto);

        return ResponseEntity.ok(resp);
    }

    @GetMapping(READ_MAPPING)
    @Operation(summary = "Obter lista de todos os animais (pets)",
            description = "Obtem a lista dos dados dos animais (pets)")
    protected ResponseEntity<List<AnimalDto>> readAll() {

        List<AnimalDto> resp = animalService.readAll();

        return ResponseEntity.ok(resp);
    }

    @GetMapping(READ_MAPPING + "/{id}")
    @Operation(summary = "Obter os dados do animal (pet)",
            description = "Obtem os dados de um animal (pet)")
    protected ResponseEntity<AnimalDto> read(@PathVariable @NotNull Long id) {

        AnimalDto resp = animalService.read(id);

        return ResponseEntity.ok(resp);
    }

    @PatchMapping(UPDATE_MAPPING)
    @Operation(summary = "Atualização de animal (pet)",
            description = "Atualiza os dados de um animal (pet)")
    protected ResponseEntity<?> update(@RequestBody @Valid @NonNull Object dto) {

        // TODO: implementar...
        Object o = animalService.update(dto);

        return ResponseEntity.ok("");
    }

    @DeleteMapping(DELETE_MAPPING + "{id}")
    @Operation(summary = "Exclusão de animal (pet)",
            description = "Exclui os dados de um animal (pet)")
    protected ResponseEntity<?> delete(@PathVariable @NotNull Long id) {

        // TODO: implementar...
        Object o = animalService.delete(id);

        return ResponseEntity.ok("");
    }
}
