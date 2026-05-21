package br.estacio.adotapet.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Deve ser usado pelos repositories que devem conter apenas métodos de leitura.
 *
 * @param <T>  O tipo da entity para o qual será destinado o repository.
 * @param <ID> O tipo do id da entity para a qual está destinada o repository.
 */
@NoRepositoryBean
interface SomenteLeituraRepository<T, ID> extends /* Repository<T, ID>, */ JpaRepository<T, ID> {

    long count();

    boolean existsById(ID id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    List<T> findAll(Sort sort);

    List<T> findAllById(Iterable<ID> ids);

    Optional<T> findById(ID id);

    T getById(ID id);
}
