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
