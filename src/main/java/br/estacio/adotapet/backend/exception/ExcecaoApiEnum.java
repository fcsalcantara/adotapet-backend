package br.estacio.adotapet.backend.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Tipos das exceções da API, com suas respectivas mensagens, que serão retornadas ao cliente.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExcecaoApiEnum {

    FalhaAutenticacaoAcessoApi("Falha na autenticação do acesso ao serviço."),
    FalhaBuscaDadosUsuario("Falha ao tentar obter os dados do usuário autenticado."),
    FalhaAoCriarUsuario("Falha ao criar um novo usuário."),
    FalhaAoAtualizarUsuario("Falha ao atualizar os dados do usuário autenticado."),
    FalhaAoExcluirrUsuario("Falha ao excluir os dados do usuário autenticado."),
    ListaAnimaisVazia("A lista de animais cadastrados está vazia."),
    FalhaBuscaDadosAnimal("Falha ao tentar obter os dados do animal cadastrado.");

    private final String mensagem;
}
