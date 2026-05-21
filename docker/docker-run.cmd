:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

:: Porta alternativa para rodar em local ou dev
for /f "tokens=1-2 delims==" %%a in ('findstr ADOTAPET_SRV_PORT vars.env') do (set ADOTAPET_SRV_PORT=%%b)

docker run --name adotapet-backend-v%VERSION% --env-file .\vars.env -p %ADOTAPET_SRV_PORT%:%ADOTAPET_SRV_PORT% -d "repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%" .

pause
