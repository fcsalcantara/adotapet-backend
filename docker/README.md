## Versão da imagem

O arquivo "version.info" deve conter a versão atual do projeto, a mesma que está no "pom.xml", que também será a versão
da imagem.

## Variáveis de ambiente possíveis

As variáveis de ambiente que serão utilizadas devem se configuradas no arquivo "vars.env".

| NOME                         | TIPO    | VALOR DEFAULT        | DESCRIÇÃO                                                       |
|------------------------------|---------|----------------------|-----------------------------------------------------------------|
| XMS                          | String  | "2048m"              |                                                                 |
| XMX                          | String  | "2048m"              |                                                                 |
| MAX_METASPACE_SIZE           | String  | "512m"               |                                                                 |
| TZ                           | String  | "America/Sao_Paulo"  |                                                                 |
| JAVA_EXTRA_OPTS              | String  | ""                   |                                                                 |
| ADOTAPET_ENV                 | String  | ""                   | Ambientes possíveis: "local", "dev", "hml" ou "prd".            |
| ADOTAPET_DB_JDBC_URL         | String  | ""                   | URL JDBC de conexão ao banco do AdotaPet.                       |
| ADOTAPET_DB_USERNAME         | String  | ""                   | Usuário para acesso ao banco do AdotaPet.                       |
| ADOTAPET_DB_PASSWORD         | String  | ""                   | Senha do usuário para acesso ao banco do AdotaPet.              |
| ADOTAPET_SRV_PORT            | Integer | 8080                 | Porta utilizada pelo sistema AdotaPet.                          |
| ADOTAPET_SRV_SSL_ENABLE      | Boolean | false                | Se o sistema AdotaPet utilizará SSL em sua URL (HTTPS).         |
| ADOTAPET_SSL_KEYSTORE        | String  | *Depende do ambiente | Keystore para utilizar SSL.                                     |
| ADOTAPET_SSL_KEYSTORE_PASS   | String  | *Depende do ambiente | Password da Keystore usada para o SSL.                          |
| ADOTAPET_JWT_EXP_MILISEC     | Integer | 900000               | Tempo de expiração do do token de autenticação em milisegundos. |
| ADOTAPET_API_SENHAACESSO     | String  | ""                   | Senha de acesso à API.                                          |
| ADOTAPET_SWAGGER_SENHAACESSO | String  | *Depende do ambiente | Senha de acesso ao Swagger.                                     |

## Maven install do projeto

É recomendável rodar o install do Maven antes de fazer build da imagem no Docker.\
O arquivo "maven-install.cmd" executa o "clean install" do Maven usando o Maven Wrapper do projeto.

## Build da imagem no Docker (Windows)

É necessário executar o arquivo "docker-build.cmd".\
Conteúdo do arquivo:

```
:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

:: Data e horário do build
for /f "tokens=1-3 delims=/ " %%a in ('date /t') do (set BUILDTIME=%%c-%%b-%%a)
for /f "tokens=1-2 delims=:" %%a in ('time /t') do (set BUILDTIME=%BUILDTIME%_%%a:%%b)

cd ..\

docker build --no-cache --build-arg VERSION_="%VERSION%" --build-arg BUILDTIME_="%BUILDTIME%" -t "repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%" .

pause
```

## Rodar a imagem no Docker (Windows)

É necessário executar o arquivo "docker-run.cmd".\
Conteúdo do arquivo:

```
:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

:: Porta alternativa para rodar em local ou dev
for /f "tokens=1-2 delims==" %%a in ('findstr ADOTAPET_SRV_PORT vars.env') do (set ADOTAPET_SRV_PORT=%%b)

docker run --name adotapet-backend-v%VERSION% --env-file .\vars.env -p %ADOTAPET_SRV_PORT%:%ADOTAPET_SRV_PORT% -d "repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%" .

pause
```

## Enviar a imagem ao Harbor (Windows)

É necessário executar o arquivo "docker-push.cmd".\
Conteúdo do arquivo:

```
:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

docker login -u adotapet repo-harbor.estacio.br
docker push repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%
docker logout

pause
```
