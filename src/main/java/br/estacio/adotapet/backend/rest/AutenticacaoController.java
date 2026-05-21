package br.estacio.adotapet.backend.rest;

import br.estacio.adotapet.backend.dto.in.AutenticacaoDto;
import br.estacio.adotapet.backend.dto.out.TokenAutenticacaoDto;
import br.estacio.adotapet.backend.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para endpoints relacionados à autenticação.
 */
@RequestMapping(AutenticacaoController.BASE_MAPPING)
@Tag(name = AutenticacaoController.BASE_MAPPING, description = "Endpoints relacionados à autenticação")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class AutenticacaoController {

    /*
     * Constantes para mapeamento dos endpoints:
     */
    static final String BASE_MAPPING = "/autenticacao";
    private static final String LOGIN_MAPPING = "login";

    private final AutenticacaoService autenticacaoService;

    /**
     * Endpoint para obter um token de autenticação para acessar a API.
     *
     * @param dto Um JSON (no corpo da requisição) contendo os dados para autenticação.
     * @return O token (com o prefixo "Bearer" incluso) no corpo da resposta.
     */
    @PostMapping(LOGIN_MAPPING)
    @Operation(summary = "Obter um token de autenticação",
            description = "Autenticar o acesso à API e obter um JSON Web Token")
    protected ResponseEntity<TokenAutenticacaoDto> login(@RequestBody @Valid AutenticacaoDto dto) {
        return ResponseEntity.ok(autenticacaoService.obtemTokenAutenticacao(dto));
    }
}
