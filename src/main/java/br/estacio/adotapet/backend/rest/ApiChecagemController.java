package br.estacio.adotapet.backend.rest;

import br.estacio.adotapet.backend.dto.out.ApiInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiChecagemController.BASE_MAPPING)
@Tag(name = ApiChecagemController.BASE_MAPPING, description = "Checagem do funcionamento da API")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
public class ApiChecagemController {

    static final String BASE_MAPPING = "/api";
    private static final String INFO_MAPPING = "info";

    private final ApiInfoDto apiInfo;

    @GetMapping(INFO_MAPPING)
    @Operation(summary = "Verifica se a aplicação está online e obtém informações da API")
    protected ResponseEntity<ApiInfoDto> info() {
        return ResponseEntity.ok(apiInfo);
    }
}
