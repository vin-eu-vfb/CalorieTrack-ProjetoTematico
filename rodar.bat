@echo off
echo ========================================
echo   CalorieTrack - Sistema Completo
echo ========================================
echo.

REM Definir caminhos
set JAVAFX_PATH=C:\Users\Usuario\javafx\javafx-sdk-25\lib
set JAVAFX_MODULES=javafx.controls,javafx.fxml,javafx.graphics
set MYSQL_JAR=lib\mysql-connector-j-9.4.0.jar


REM Criar pastas bin se nao existirem
echo [0/3] Preparando ambiente...
if not exist "bin" mkdir bin
if not exist "interface\bin" mkdir interface\bin
if not exist "lib" mkdir lib
echo [OK] Pastas criadas/verificadas!
echo.

REM Compilar backend
echo [1/3] Compilando classes do backend...
if "%MYSQL_JAR%"=="" (
    javac -d bin -sourcepath src src\CalorieTrack\Classes\*.java
) else (
    javac -cp "%MYSQL_JAR%" -d bin -sourcepath src src\CalorieTrack\Classes\*.java
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERRO] Falha na compilacao do backend!
    pause
    exit /b 1
)

echo [OK] Backend compilado com sucesso!
echo.

REM Compilar interface
echo [2/3] Compilando interface JavaFX...
if "%MYSQL_JAR%"=="" (
    javac --module-path "%JAVAFX_PATH%" --add-modules %JAVAFX_MODULES% -cp "bin" -d interface\bin interface\src\MainApp.java
) else (
    javac --module-path "%JAVAFX_PATH%" --add-modules %JAVAFX_MODULES% -cp "bin;%MYSQL_JAR%" -d interface\bin interface\src\MainApp.java
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERRO] Falha na compilacao da interface!
    echo.
    echo Detalhes: Verifique se o JavaFX esta corretamente instalado.
    pause
    exit /b 1
)

echo [OK] Interface compilada com sucesso!
echo.

REM Executar aplicacao
echo [3/3] Iniciando aplicacao JavaFX...
echo.
echo ========================================
echo   Aplicacao Iniciada!
echo ========================================
echo.

if "%MYSQL_JAR%"=="" (
    java --module-path "%JAVAFX_PATH%" --add-modules %JAVAFX_MODULES% -cp "bin;interface\bin;interface\src" MainApp
) else (
    java --module-path "%JAVAFX_PATH%" --add-modules %JAVAFX_MODULES% -cp "bin;interface\bin;interface\src;%MYSQL_JAR%" MainApp
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERRO] Falha ao executar a aplicacao!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Aplicacao Encerrada
echo ========================================
pause
