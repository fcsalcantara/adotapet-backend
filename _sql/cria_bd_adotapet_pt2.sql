-- PARTE 2:
-- Criação do schema e das tabelas
-- *Executar os comandos conectado diretamente ao adotapet
create schema adotapet authorization adotapet;
comment on schema adotapet is 'Schema para o sistema AdotaPet.';
grant all on schema adotapet to adotapet;


-- Tabela de Usuários:
create table adotapet.tb_usuarios
(
    id         bigint generated always as identity primary key,
    nome       varchar(150) not null, -- Tamanho de nome para padrão brasileiro.
    email      varchar(254) not null unique,
    senha      varchar(60)  not null,
    cpf        varchar(11)  not null unique,
    telefone   varchar(11)  not null,
    dt_criacao timestamptz  not null default current_timestamp,
    habilitado boolean      not null default true
);
alter table adotapet.tb_usuarios
    owner to adotapet;
grant all on table adotapet.tb_usuarios to adotapet;


create table adotapet.tb_animais
(
    id         bigint generated always as identity primary key,
    nome       varchar(100) not null,
    tipo       varchar(20)  not null check (tipo in ('CAO', 'GATO')),
    sexo       varchar(20)  not null check (sexo in ('MACHO', 'FEMEA')),
    idade      integer      not null,
    porte      varchar(20)  not null check (porte in ('PEQUENO', 'MEDIO', 'GRANDE')),
    foto       text         not null,
    descricao  text,
    status     varchar(20)  not null default 'DISPONIVEL' check (status in ('DISPONIVEL', 'ADOTADO')),
    usuario_id bigint       not null,
    dt_criacao timestamptz  not null default current_timestamp,
    habilitado boolean      not null default true,

    constraint fk_animal_usuario
        foreign key (usuario_id)
            references adotapet.tb_usuarios (id)
            on delete cascade
);
alter table adotapet.tb_animais
    owner to adotapet;
grant all on table adotapet.tb_animais to adotapet;
