:: Versão do AdotaPet
for /f "tokens=1" %%a in (version.info) do (set VERSION=%%a)

:: Data e horário do build
for /f "tokens=1-3 delims=/ " %%a in ('date /t') do (set BUILDTIME=%%c-%%b-%%a)
for /f "tokens=1-2 delims=:" %%a in ('time /t') do (set BUILDTIME=%BUILDTIME%_%%a:%%b)

cd ..\

docker build --no-cache --build-arg VERSION_="%VERSION%" --build-arg BUILDTIME_="%BUILDTIME%" -t "repo-harbor.estacio.br/adotapet/adotapet-backend:%VERSION%" .

pause
