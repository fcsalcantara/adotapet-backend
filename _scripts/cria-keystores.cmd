:: Para criar as Key Stores auto assinadas, nos formatos JKS e PKCS12, utilizando a KeyTool do JDK. O formato PKCS12
:: será usado para configurações de SSL da API (Local e Dev), e o JKS para assinatura do token de autenticação (JWT).
:: @echo off

:: A Keytool usada é do Java 8, para usar Java 21 seria necessário reescrever esse script
set KEYTOOL=%userprofile%\.jdks\corretto-1.8.0_462\bin\keytool.exe
set PROJECT_NAME=adotapet-backend
set KEYSTORES_DIR=C:\Projects\estacio\%PROJECT_NAME%\src\main\resources\keystores

:: Senhas de acesso às key stores e às chaves do formato JKS:
set KEYSTORE_JKS_PASS=pFqYc4v6SD6XMpl5EeOJF3JA7umP57CWbGp55GvFZAweHRGH
set DEV_KEY_JKS_PASS=x49DErj2Lc9Gdt4DE57t6yQY
set HML_KEY_JKS_PASS=JtCbG4fvZyIi11ygMiqA1K0z
set PRD_KEY_JKS_PASS=Ovd6zXDH3p3TgaJ95a7AVwdU
set KEYSTORE_P12_PASS=uHTYdyJP7TzQZidEipAZAV89gBc9R4hi0hHEFZgrXR5oTWFP

:: :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

set KEYSTORE_NAME=keystore-%PROJECT_NAME%
set KEYSTORE_JKS_FULL_PATH=%KEYSTORES_DIR%\%KEYSTORE_NAME%.jks
set DEV_KEYALIAS_JKS=dev-jwt-key-%PROJECT_NAME%
set HML_KEYALIAS_JKS=hml-jwt-key-%PROJECT_NAME%
set PRD_KEYALIAS_JKS=prd-jwt-key-%PROJECT_NAME%

set KEYSTORE_P12_FULL_PATH=%KEYSTORES_DIR%\%KEYSTORE_NAME%.p12
set DEV_KEYALIAS_P12=dev-ssl-key-%PROJECT_NAME%

set DATETIME=_%DATE:~-4%%DATE:~3,2%%DATE:~0,2%%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%%TIME:~9%
set LEIAME_TXT="%KEYSTORES_DIR%\leia-me.txt"

:: Caso o diretório que guardará as key stores geradas já exista, será recriado:
if exist %KEYSTORES_DIR% rmdir /S /Q %KEYSTORES_DIR%
mkdir %KEYSTORES_DIR%

%KEYTOOL% -genkeypair -keyalg RSA -keysize 4096 -sigalg SHA256withRSA -validity 365000 -alias %DEV_KEYALIAS_JKS% -keypass %DEV_KEY_JKS_PASS% -keystore %KEYSTORE_JKS_FULL_PATH% -storepass %KEYSTORE_JKS_PASS% -dname "CN="%PROJECT_NAME%%DATETIME%", OU=ADOTAPET, O=ESTACIO, L=Rio de Janeiro, ST=RJ, C=BR"
%KEYTOOL% -genkeypair -keyalg RSA -keysize 4096 -sigalg SHA256withRSA -validity 365000 -alias %HML_KEYALIAS_JKS% -keypass %HML_KEY_JKS_PASS% -keystore %KEYSTORE_JKS_FULL_PATH% -storepass %KEYSTORE_JKS_PASS% -dname "CN="%PROJECT_NAME%%DATETIME%", OU=ADOTAPET, O=ESTACIO, L=Rio de Janeiro, ST=RJ, C=BR"
%KEYTOOL% -genkeypair -keyalg RSA -keysize 4096 -sigalg SHA256withRSA -validity 365000 -alias %PRD_KEYALIAS_JKS% -keypass %PRD_KEY_JKS_PASS% -keystore %KEYSTORE_JKS_FULL_PATH% -storepass %KEYSTORE_JKS_PASS% -dname "CN="%PROJECT_NAME%%DATETIME%", OU=ADOTAPET, O=ESTACIO, L=Rio de Janeiro, ST=RJ, C=BR"

%KEYTOOL% -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -validity 365000 -alias %DEV_KEYALIAS_P12% -keystore %KEYSTORE_P12_FULL_PATH% -storepass %KEYSTORE_P12_PASS% -storetype PKCS12 -dname "CN="%PROJECT_NAME%%DATETIME%", OU=ADOTAPET, O=ESTACIO, L=Rio de Janeiro, ST=RJ, C=BR"

echo AS KEYSTORES SÃO UTILIZADAS NAS CONFIGURAÇÕES DE SSL DA API, ASSIM COMO NA EMISSÃO DE TOKENS DE AUTENTICAÇÃO JWT.>%LEIAME_TXT%
echo DATA DE CRIAÇÃO DAS KEY STORES: %DATE%>>%LEIAME_TXT%
echo. >>%LEIAME_TXT%
echo key-store-password:>>%LEIAME_TXT%
echo   JKS: %KEYSTORE_JKS_PASS%>>%LEIAME_TXT%
echo   PKCS12: %KEYSTORE_P12_PASS%>>%LEIAME_TXT%
echo. >>%LEIAME_TXT%
echo key-alias:>>%LEIAME_TXT%
echo   JKS:>>%LEIAME_TXT%
echo       %DEV_KEYALIAS_JKS%>>%LEIAME_TXT%
echo       %HML_KEYALIAS_JKS%>>%LEIAME_TXT%
echo       %PRD_KEYALIAS_JKS%>>%LEIAME_TXT%
echo   PKCS12:>>%LEIAME_TXT%
echo       %DEV_KEYALIAS_P12%>>%LEIAME_TXT%
echo. >>%LEIAME_TXT%
echo key-password (apenas no formato JKS):>>%LEIAME_TXT%
echo 	dev: %DEV_KEY_JKS_PASS%>>%LEIAME_TXT%
echo 	hml: %HML_KEY_JKS_PASS%>>%LEIAME_TXT%
echo 	prd: %PRD_KEY_JKS_PASS%>>%LEIAME_TXT%

pause
exit
