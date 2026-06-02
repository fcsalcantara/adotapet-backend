package br.estacio.adotapet.backend.rest;

import br.estacio.adotapet.backend.dto.in.UsuarioCreateDto;
import br.estacio.adotapet.backend.dto.in.UsuarioUpdateDto;
import br.estacio.adotapet.backend.dto.out.TokenAutenticacaoDto;
import br.estacio.adotapet.backend.dto.out.UsuarioCadastradoDto;
import br.estacio.adotapet.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller para endpoints relacionados ao CRUD de Usuários.
 */
@RequestMapping(UsuarioController.BASE_MAPPING)
@Tag(name = UsuarioController.BASE_MAPPING, description = "Endpoints relacionados ao CRUD de Usuários")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class UsuarioController {

    /*
     * Constantes para mapeamento dos endpoints:
     */
    static final String BASE_MAPPING = "/usuario";
    private static final String CREATE_MAPPING = "create";
    private static final String READ_MAPPING = "read";
    private static final String UPDATE_MAPPING = "update";
    private static final String DELETE_MAPPING = "delete";

    private final UsuarioService usuarioService;

    @PostMapping(CREATE_MAPPING)
    @Operation(summary = "Criação de usuário",
            description = "Cria um novo usuário")
    protected ResponseEntity<TokenAutenticacaoDto> create(@RequestBody @Valid @NonNull UsuarioCreateDto dto) {

        TokenAutenticacaoDto token = usuarioService.create(dto);

        return ResponseEntity.ok(token);
    }

    @GetMapping(READ_MAPPING)
    @Operation(summary = "Obter os dados do usuário",
            description = "Obtem os dados de um usuário autenticado")
    protected ResponseEntity<UsuarioCadastradoDto> read() {

        UsuarioCadastradoDto usuario = usuarioService.read();

        return ResponseEntity.ok(usuario);
    }

    @PatchMapping(UPDATE_MAPPING)
    @Operation(summary = "Atualização de usuário",
            description = "Atualiza os dados de um usuário autenticado")
    protected ResponseEntity<TokenAutenticacaoDto> update(@RequestBody @Valid @NonNull UsuarioUpdateDto dto) {

        TokenAutenticacaoDto token = usuarioService.update(dto);

        return ResponseEntity.ok(token);
    }

    @DeleteMapping(DELETE_MAPPING + "{id}")
    @Operation(summary = "Exclusão de usuário",
            description = "Exclui os dados de um usuário autenticado")
    protected ResponseEntity<?> delete(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(usuarioService.delete(id));
    }
}
