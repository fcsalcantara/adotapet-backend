:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

docker login -u adotapet repo-harbor.estacio.br
docker push repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%
docker logout

pause
