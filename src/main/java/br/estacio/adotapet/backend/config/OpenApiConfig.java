package br.estacio.adotapet.backend.config;

import br.estacio.adotapet.backend.dto.out.ApiInfoDto;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger 3 com OpenAPI 3 (http://[host:port]/swagger-ui/index.html).
 */
@Configuration
@SecurityScheme(name = OpenApiConfig.SECURITYSCHEME_NAME,
        description = "JSON Web Token para autenticação",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenApiConfig {

    public static final String SECURITYSCHEME_NAME = "Bearer JWT Authentication";

    @Value("${spring.profiles.active}")
    private String profileAtivo;

    @Value("${adotapet.versao}")
    private String versaoProjeto;

    /* @Value("${spring.datasource.url}")
    private String datasourceJdbc;

    @Value("${spring.datasource.username}")
    private String datasourceUser */;

    @Bean
    protected OpenAPI openAPI() {

        Info info = new Info().title("AdotaPet - " + obtemNomeAmbiente())
                .description("API REST do sistema AdotaPet.")
                .version("Versão: " + versaoProjeto)
                .license(new License().name("Estácio (RJ)").url("https://estacio.br"))
                .contact(new Contact().name("AdotaPet").email("adotapet@estacio.br"));

        return new OpenAPI().info(info);
    }

    private String obtemNomeAmbiente() {

        return switch (profileAtivo) {
            case "local" -> "Local";
            case "dev" -> "Desenvolvimento";
            case "hml" -> "Homologação";
            case "prd" -> "Produção";
            default -> "Ambiente desconhecido";
        };
    }

    @Bean
    protected ApiInfoDto apiInfoDto() {
        return new ApiInfoDto(obtemNomeAmbiente(), versaoProjeto /*, datasourceJdbc, datasourceUser*/);
    }
}
