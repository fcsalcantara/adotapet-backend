package br.estacio.adotapet.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "adotapet", name = "tb_animais")
@DynamicInsert
public class TbAnimal {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 20)
    private TbAnimalTipoEnum tipo;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "sexo", nullable = false, length = 20)
    private TbAnimalSexoEnum sexo;

    @NotNull
    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "porte", nullable = false, length = 20)
    private TbAnimalPorteEnum porte;

    @NotNull
    @Column(name = "foto", nullable = false, length = Integer.MAX_VALUE)
    private String foto;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'DISPONIVEL'")
    @Column(name = "status", nullable = false, length = 20)
    private TbAnimalStatusEnum status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private TbUsuario usuario;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "dt_criacao", nullable = false)
    private LocalDateTime dtCriacao;

    @ColumnDefault("true")
    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;
}