-- PARTE 1:
-- Criação do banco de dados e usuários (roles)
-- Os comandos devem ser executados com superuser
create role adotapet with
    login
    nosuperuser
    inherit
    nocreatedb
    nocreaterole
    noreplication;
alter
role adotapet password 'adotapet'; -- colocar a senha

create
database adotapet
	with
	encoding = 'utf8'
	lc_collate = 'pt_br.utf-8'
	lc_ctype = 'pt_br.utf-8'
	connection limit = -1;
alter
database adotapet owner to adotapet;
grant all
on database adotapet to adotapet;
