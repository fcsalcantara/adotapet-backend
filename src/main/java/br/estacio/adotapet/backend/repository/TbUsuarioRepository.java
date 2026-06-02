package br.estacio.adotapet.backend.repository;

import br.estacio.adotapet.backend.model.TbUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository da tabela "adotapet.tb_usuario".
 */
public interface TbUsuarioRepository extends JpaRepository<TbUsuario, Long> {

    /**
     * Busca um usuário utilizando o e-mail.
     *
     * @param email E-mail do usuário buscado.
     * @return Um container Optional, que pode guardar um usuário, caso seja encontrado.
     */
    Optional<TbUsuario> findByEmail(String email);

    /**
     * Verifica se o e-mail já foi cadastrado por algum usuário.
     *
     * @param email O e-mail que será verificado.
     * @return Verdadeiro, caso o e-mail já tenha sido cadastrado, ou falso, caso ainda não.
     */
    @Query("select case when (count(u.email) > 0) then true else false end from TbUsuario u where u.email = :email")
    boolean emailJaCadastrado(String email);

    /**
     * Busca um usuário utilizando o CPF.
     *
     * @param cpf CPF do usuário buscado.
     * @return Um container Optional, que pode guardar um usuário, caso seja encontrado.
     */
    Optional<TbUsuario> findByCpf(String cpf);

    /**
     * Verifica se o CPF já foi cadastrado por algum usuário.
     *
     * @param cpf O CPF que será verificado.
     * @return Verdadeiro, caso o e-mail já tenha sido cadastrado, ou falso, caso ainda não.
     */
    @Query("select case when (count(u.cpf) > 0) then true else false end from TbUsuario u where u.cpf = :email")
    boolean cpfJaCadastrado(String cpf);

    /**
     * Verifica, utilizando o e-mail, se um usuário está desabilitado.
     *
     * @param email E-mail do usuário.
     * @return Verdadeiro, caso o usuário esteja desabilitado, ou falso, caso não.
     */
    @Query("select case when (count(u.email) > 0) then true else false end from TbUsuario u where u.email = :email and u.habilitado = false")
    boolean usuarioDesabilitado(String email);
}
