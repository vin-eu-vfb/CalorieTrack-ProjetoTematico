import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import java.io.File;

// Importar classes locais do projeto
import CalorieTrack.Classes.Funcoes;
import CalorieTrack.Classes.Usuario;

public class MainApp extends Application {
    
    // Instância de Funcoes para gerenciar usuários
    private Funcoes funcoes;
    private int tentativasLogin = 0;
    private static final int MAX_TENTATIVAS = 3;

    @Override
    public void start(Stage primaryStage) {
        funcoes = new Funcoes();
        ImageView logo = new ImageView();

        File logoFile = new File("interface/src/logo.png");
        if (logoFile.exists()) {
            Image logoImage = new Image(logoFile.toURI().toString());
            logo.setImage(logoImage);
            logo.setFitWidth(150);
            logo.setFitHeight(150);
            logo.setPreserveRatio(true);
        } else {
            System.out.println("Logo não encontrado no caminho: " + logoFile.getAbsolutePath());
        }
           
        Label titulo = new Label("Informe seus dados de login:");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        emailField.setPrefWidth(300);

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Digite sua senha");
        senhaField.setPrefWidth(300);

        Label mensagem = new Label();
        mensagem.setVisible(false);
        mensagem.setWrapText(true);
        mensagem.setMaxWidth(300);

        Button loginBtn = new Button("Entrar");
        loginBtn.setPrefWidth(300);
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();
            
            if (email.isEmpty() || senha.isEmpty()) {
                mostrarMensagem(mensagem, "Preencha todos os campos!", "erro");
                return;
            }
            
            if (tentativasLogin >= MAX_TENTATIVAS) {
                mostrarMensagem(mensagem, "Número máximo de tentativas excedido. Reinicie o aplicativo.", "erro");
                loginBtn.setDisable(true);
                return;
            }
            
            loginBtn.setDisable(true);
            mensagem.setVisible(false);
            
            Usuario usuario = funcoes.buscarUsuario(email, senha);
            
            if (usuario != null) {
                tentativasLogin = 0;
                mostrarMensagem(mensagem, "Login realizado com sucesso! Bem-vindo(a), " + usuario.getNome() + "!", "sucesso");
                
            abrirTelaMenu(primaryStage, usuario);
            
            } else {
                tentativasLogin++;
                int tentativasRestantes = MAX_TENTATIVAS - tentativasLogin;
                
                if (tentativasRestantes > 0) {
                    mostrarMensagem(mensagem, "Email ou senha incorretos. " + tentativasRestantes + " tentativas restantes.", "erro");
                } else {
                    mostrarMensagem(mensagem, "Número máximo de tentativas excedido. Reinicie o aplicativo.", "erro");
                }
            }
            
            loginBtn.setDisable(false);
        });

        Hyperlink cadastroLink = new Hyperlink("Não tem conta? Cadastre-se");
        cadastroLink.setOnAction(e -> {
            abrirTelaCadastro(primaryStage);
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: white;");
        root.getChildren().addAll(
            logo, titulo,
            emailLabel, emailField,
            senhaLabel, senhaField,
            mensagem, loginBtn, cadastroLink
        );

        Scene scene = new Scene(root, 550, 800);
        primaryStage.setTitle("Sistema de Saúde e Nutrição");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    
    private void abrirTelaMenu(Stage stage, Usuario usuario) {
        ImageView logo = new ImageView();
        File logoFile = new File("interface/src/logo.png");
        if (logoFile.exists()) {
            Image logoImage = new Image(logoFile.toURI().toString());
            logo.setImage(logoImage);
            logo.setFitWidth(100);
            logo.setFitHeight(100);
            logo.setPreserveRatio(true);
        }
        
        Label bemVindo = new Label("Olá, " + usuario.getNome() + "!");
        bemVindo.setStyle(
            "-fx-font-size: 24px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #2C3E50;"
        );
        
        Label subtitulo = new Label("O que você gostaria de fazer?");
        subtitulo.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-text-fill: #7F8C8D;"
        );
        
        String baseButtonStyle = 
            "-fx-background-radius: 12px; " +
            "-fx-cursor: hand; " +
            "-fx-font-size: 15px; " +
            "-fx-font-weight: 600; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);";
        
        Button btnConsultarAlimentos = new Button("🍎 Consultar Alimentos");
        btnConsultarAlimentos.setPrefWidth(380);
        btnConsultarAlimentos.setPrefHeight(65);
        btnConsultarAlimentos.setStyle(
            baseButtonStyle +
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white;"
        );
        btnConsultarAlimentos.setOnMouseEntered(e -> 
            btnConsultarAlimentos.setStyle(baseButtonStyle + "-fx-background-color: #2980B9; -fx-text-fill: white;")
        );
        btnConsultarAlimentos.setOnMouseExited(e -> 
            btnConsultarAlimentos.setStyle(baseButtonStyle + "-fx-background-color: #3498DB; -fx-text-fill: white;")
        );
        
        Button btnRegistrarRefeicao = new Button("🍽️ Registrar Refeição");
        btnRegistrarRefeicao.setPrefWidth(380);
        btnRegistrarRefeicao.setPrefHeight(65);
        btnRegistrarRefeicao.setStyle(
            baseButtonStyle +
            "-fx-background-color: #27AE60; " +
            "-fx-text-fill: white;"
        );
        btnRegistrarRefeicao.setOnMouseEntered(e -> 
            btnRegistrarRefeicao.setStyle(baseButtonStyle + "-fx-background-color: #229954; -fx-text-fill: white;")
        );
        btnRegistrarRefeicao.setOnMouseExited(e -> 
            btnRegistrarRefeicao.setStyle(baseButtonStyle + "-fx-background-color: #27AE60; -fx-text-fill: white;")
        );
        
        Button btnRegistrarAtividade = new Button("🏃 Registrar Atividade");
        btnRegistrarAtividade.setPrefWidth(380);
        btnRegistrarAtividade.setPrefHeight(65);
        btnRegistrarAtividade.setStyle(
            baseButtonStyle +
            "-fx-background-color: #E67E22; " +
            "-fx-text-fill: white;"
        );
        btnRegistrarAtividade.setOnMouseEntered(e -> 
            btnRegistrarAtividade.setStyle(baseButtonStyle + "-fx-background-color: #D35400; -fx-text-fill: white;")
        );
        btnRegistrarAtividade.setOnMouseExited(e -> 
            btnRegistrarAtividade.setStyle(baseButtonStyle + "-fx-background-color: #E67E22; -fx-text-fill: white;")
        );
        
        Button btnSugerirAlimento = new Button("💡 Sugerir Alimento");
        btnSugerirAlimento.setPrefWidth(380);
        btnSugerirAlimento.setPrefHeight(65);
        btnSugerirAlimento.setStyle(
            baseButtonStyle +
            "-fx-background-color: #9B59B6; " +
            "-fx-text-fill: white;"
        );
        btnSugerirAlimento.setOnMouseEntered(e -> 
            btnSugerirAlimento.setStyle(baseButtonStyle + "-fx-background-color: #8E44AD; -fx-text-fill: white;")
        );
        btnSugerirAlimento.setOnMouseExited(e -> 
            btnSugerirAlimento.setStyle(baseButtonStyle + "-fx-background-color: #9B59B6; -fx-text-fill: white;")
        );
        
        Button btnConquista = new Button("🏆 Conquistas");
        btnConquista.setPrefWidth(380);
        btnConquista.setPrefHeight(65);
        btnConquista.setStyle(
            baseButtonStyle +
            "-fx-background-color: #F39C12; " +
            "-fx-text-fill: white;"
        );
        btnConquista.setOnMouseEntered(e -> 
            btnConquista.setStyle(baseButtonStyle + "-fx-background-color: #E67E22; -fx-text-fill: white;")
        );
        btnConquista.setOnMouseExited(e -> 
            btnConquista.setStyle(baseButtonStyle + "-fx-background-color: #F39C12; -fx-text-fill: white;")
        );
        
        btnConsultarAlimentos.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Consultar Alimentos");
        });
        
        btnRegistrarRefeicao.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Registrar Refeição");
        });
        
        btnRegistrarAtividade.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Registrar Atividade");
        });
        
        btnSugerirAlimento.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Sugerir Alimento");
        });
        
        btnConquista.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Conquistas");
        });
        
        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(logo, bemVindo, subtitulo);
        
        VBox buttonsContainer = new VBox(16);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(20, 0, 0, 0));
        buttonsContainer.getChildren().addAll(
            btnConsultarAlimentos,
            btnRegistrarRefeicao,
            btnRegistrarAtividade,
            btnSugerirAlimento,
            btnConquista
        );
        
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40, 35, 40, 35));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(header, buttonsContainer);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Menu Principal - CalorieTrack");
        stage.setScene(scene);
    }
    
    private void abrirTelaDesenvolvimento(Stage stage, Usuario usuario, String titulo) {
        ImageView imageView = new ImageView();
        File imgFile = new File("interface/src/desenvolvimento.png");
        if (imgFile.exists()) {
            Image image = new Image(imgFile.toURI().toString());
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(420);
        }
        
        Label labelTitulo = new Label(titulo);
        labelTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 10px;");
        btnVoltar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(labelTitulo, imageView, btnVoltar);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Em desenvolvimento - " + titulo);
        stage.setScene(scene);
    }
    
    private void abrirTelaCadastro(Stage stage) {
        Label titulo = new Label("Cadastro de Usuário");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        // Campos do formulário
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome completo");
        nomeField.setPrefWidth(300);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);
        
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");
        senhaField.setPrefWidth(300);
        
        TextField idadeField = new TextField();
        idadeField.setPromptText("Idade");
        idadeField.setPrefWidth(300);
        
        TextField pesoField = new TextField();
        pesoField.setPromptText("Peso (kg)");
        pesoField.setPrefWidth(300);
        
        TextField alturaField = new TextField();
        alturaField.setPromptText("Altura (cm)");
        alturaField.setPrefWidth(300);
        
        ComboBox<String> sexoCombo = new ComboBox<>();
        sexoCombo.getItems().addAll("Masculino", "Feminino", "Outro");
        sexoCombo.setPromptText("Sexo");
        sexoCombo.setPrefWidth(300);
        
        TextField metaField = new TextField();
        metaField.setPromptText("Meta calórica diária");
        metaField.setPrefWidth(300);
        
        Label mensagem = new Label();
        mensagem.setVisible(false);
        mensagem.setWrapText(true);
        mensagem.setMaxWidth(300);
        
        Button cadastrarBtn = new Button("Cadastrar");
        cadastrarBtn.setPrefWidth(300);
        cadastrarBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        cadastrarBtn.setOnAction(e -> {
            if (nomeField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || 
                senhaField.getText().trim().isEmpty() || idadeField.getText().trim().isEmpty() ||
                pesoField.getText().trim().isEmpty() || alturaField.getText().trim().isEmpty() ||
                sexoCombo.getValue() == null || metaField.getText().trim().isEmpty()) {
                mostrarMensagem(mensagem, "Preencha todos os campos!", "erro");
                return;
            }
            
            try {
                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(nomeField.getText().trim());
                novoUsuario.setEmail(emailField.getText().trim());
                novoUsuario.setSenha(senhaField.getText().trim());
                novoUsuario.setIdade(Integer.parseInt(idadeField.getText().trim()));
                novoUsuario.setPeso(Double.parseDouble(pesoField.getText().trim()));
                novoUsuario.setAltura(Double.parseDouble(alturaField.getText().trim()));
                novoUsuario.setSexo(sexoCombo.getValue());
                novoUsuario.setMetacalorica(Double.parseDouble(metaField.getText().trim()));
                
                boolean sucesso = funcoes.cadastrarUsuario(novoUsuario);
                
                if (sucesso) {
                    mostrarMensagem(mensagem, "Cadastro realizado com sucesso! Faça login.", "sucesso");
                    
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            javafx.application.Platform.runLater(() -> start(stage));
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                } else {
                    mostrarMensagem(mensagem, "Erro ao cadastrar. Email já existe ou erro no banco de dados.", "erro");
                }
                
            } catch (NumberFormatException ex) {
                mostrarMensagem(mensagem, "Valores numéricos inválidos!", "erro");
            }
        });
        
        Button voltarBtn = new Button("Voltar");
        voltarBtn.setPrefWidth(300);
        voltarBtn.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px;");
        voltarBtn.setOnAction(e -> start(stage));
        
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: white;");
        root.getChildren().addAll(
            titulo,
            new Label("Nome:"), nomeField,
            new Label("Email:"), emailField,
            new Label("Senha:"), senhaField,
            new Label("Idade:"), idadeField,
            new Label("Peso (kg):"), pesoField,
            new Label("Altura (cm):"), alturaField,
            new Label("Sexo:"), sexoCombo,
            new Label("Meta Calórica:"), metaField,
            mensagem, cadastrarBtn, voltarBtn
        );
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        
        Scene scene = new Scene(scrollPane, 550, 800);
        stage.setScene(scene);
    }

    private void mostrarMensagem(Label label, String texto, String tipo) {
        label.setText(texto);
        label.setVisible(true);
        
        switch (tipo) {
            case "erro":
                label.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #c62828; -fx-padding: 10px; -fx-background-radius: 5px;");
                break;
            case "sucesso":
                label.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 10px; -fx-background-radius: 5px;");
                break;
            case "info":
                label.setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #1565c0; -fx-padding: 10px; -fx-background-radius: 5px;");
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}