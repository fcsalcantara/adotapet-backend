package br.estacio.adotapet.backend.rest.advice;

import br.estacio.adotapet.backend.dto.out.ErroValidacaoDto;
import br.estacio.adotapet.backend.dto.out.ExcecaoApiDto;
import br.estacio.adotapet.backend.exception.ExcecaoApi;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Monitora os lançamentos de exceção, devolvendo um JSON no corpo resposta ao cliente da API.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestControllerAdvice
public class ExceptionAdvice {

    private final MessageSource messageSource;

    /**
     * Executado quando há exceção lançada por uma falha na validação de campos recebidos em um DTO.
     * Devolve ao cliente uma resposta com código 400 (Bad Request).
     *
     * @param exception A exceção contendo as falhas na validação dos campos.
     * @return Uma lista JSON com os erros de validação em cada campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected List<ErroValidacaoDto> respondeExcecaoValidacao(MethodArgumentNotValidException exception) {

        List<FieldError> listaErrosCampos = exception.getBindingResult().getFieldErrors();
        List<ErroValidacaoDto> listaErrosValidacao = new ArrayList<>();

        listaErrosCampos.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroValidacaoDto erro = new ErroValidacaoDto(e.getField(), mensagem);
            listaErrosValidacao.add(erro);
        });

        return listaErrosValidacao;
    }

    /**
     * Executado quando há lançamento de exceção da API.
     * Devolve ao cliente uma resposta com código 400 (Bad Request).
     *
     * @param excecaoApi A exceção lançada na API, e capturada no @ExceptionHandler.
     * @return O DTO, em JSON, no corpo da resposta, contendo os detalhes da exceção.
     */
    @ExceptionHandler(ExcecaoApi.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ExcecaoApiDto respondeExcecaoApi(ExcecaoApi excecaoApi) {
        return new ExcecaoApiDto(excecaoApi.getTipo(), excecaoApi.getMensagem());
    }
}
