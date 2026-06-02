package br.estacio.adotapet.backend.model;

import br.estacio.adotapet.backend.config.service.UsuarioAutenticacaoService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "adotapet", name = "tb_usuarios")
@DynamicInsert
public class TbUsuario implements UserDetails {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 150)
    @NotNull
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @EqualsAndHashCode.Include
    @Size(max = 254)
    @NotNull
    @Column(name = "email", unique = true, nullable = false, length = 254)
    private String email;

    @Size(max = 60)
    @NotNull
    @Column(name = "senha", nullable = false, length = 60)
    private String senha;

    @EqualsAndHashCode.Include
    @Size(max = 11)
    @NotNull
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Size(max = 11)
    @NotNull
    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "dt_criacao", nullable = false)
    private LocalDateTime dtCriacao;

    @ColumnDefault("true")
    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    /*
     * Implementações dos métodos necessários ao UserDetails
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                (GrantedAuthority) () -> "ROLE_".concat(UsuarioAutenticacaoService.ROLE_ACESSOAPI),
                (GrantedAuthority) () -> "ROLE_".concat(UsuarioAutenticacaoService.ROLE_USUARIO));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isEnabled() {
        return habilitado;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}
