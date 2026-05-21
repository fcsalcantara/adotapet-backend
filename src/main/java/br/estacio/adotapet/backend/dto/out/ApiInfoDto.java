package br.estacio.adotapet.backend.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Dados da API para integração e informação
 */
@Schema(description = "Contém dados básicos da API")
public record ApiInfoDto(@Schema(description = "Tipo de ambiente")
                         String ambiente,

                         @Schema(description = "Versão do serviço")
                         String versao /*,

                         @Schema(description = "String de conexção JDBC em uso")
                         String datasourceJdbc,

                         @Schema(description = "Nome de usuário da String JDBC")
                         String datasourceUser */) {

}
