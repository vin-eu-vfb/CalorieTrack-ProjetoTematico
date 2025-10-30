import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import CalorieTrack.Classes.Funcoes;
import CalorieTrack.Classes.Usuario;

public class MainApp extends Application {
    
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
            abrirTelaConsultaAlimentos(stage, usuario);
        });
        
        btnRegistrarRefeicao.setOnAction(e -> {
            abrirTelaRegistrarRefeicao(stage, usuario);
        });
        
        btnRegistrarAtividade.setOnAction(e -> {
            abrirTelaAtividades(stage, usuario);
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
    
    private void abrirTelaAdicionarAtividade(Stage stage, Usuario usuario) {
        Label titulo = new Label("Registrar Atividade");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        Label lblTipo = new Label("Tipo de Atividade:");
        lblTipo.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50;");
        
        ComboBox<String> cbTipoAtividade = new ComboBox<>();
        cbTipoAtividade.getItems().addAll(
            "Corrida", "Caminhada", "Ciclismo", "Musculação", 
            "Natação", "Futebol", "Basquete", "Tênis", "Vôlei", "Outro"
        );
        cbTipoAtividade.setPromptText("Selecione o tipo de atividade");
        cbTipoAtividade.setPrefWidth(400);
        
        Label lblData = new Label("Data da Atividade:");
        lblData.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50; -fx-padding: 10 0 0 0;");
        
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setPrefWidth(400);
        
        Label lblCalorias = new Label("Calorias Queimadas (kcal):");
        lblCalorias.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50; -fx-padding: 10 0 0 0;");
        
        TextField txtCalorias = new TextField();
        txtCalorias.setPromptText("Digite as calorias queimadas");
        txtCalorias.setPrefWidth(400);
        txtCalorias.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtCalorias.setText(oldValue);
            }
        });
        
        Button btnSalvar = new Button("Salvar Atividade");
        btnSalvar.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        btnSalvar.setOnMouseEntered(e -> 
            btnSalvar.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 45px; -fx-background-radius: 10px;")
        );
        btnSalvar.setOnMouseExited(e -> 
            btnSalvar.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 45px; -fx-background-radius: 10px;")
        );
        btnSalvar.setOnAction(e -> {
            if (cbTipoAtividade.getValue() == null || txtCalorias.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Campos obrigatórios");
                alert.setContentText("Por favor, preencha todos os campos obrigatórios.");
                alert.showAndWait();
                return;
            }
            
            String mensagem = String.format("Atividade salva com sucesso!\n\n" +
                "Tipo: %s\n" +
                "Data: %s\n" +
                "Calorias: %s kcal",
                cbTipoAtividade.getValue(),
                datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                txtCalorias.getText()
            );
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Atividade Registrada");
            alert.setContentText(mensagem);
            alert.showAndWait();
            
            abrirTelaAtividades(stage, usuario);
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(
            "-fx-background-color: #E74C3C; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        btnCancelar.setOnMouseEntered(e -> 
            btnCancelar.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 45px; -fx-background-radius: 10px;")
        );
        btnCancelar.setOnMouseExited(e -> 
            btnCancelar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 45px; -fx-background-radius: 10px;")
        );
        btnCancelar.setOnAction(e -> abrirTelaAtividades(stage, usuario));
        
        HBox botoesContainer = new HBox(20);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.getChildren().addAll(btnSalvar, btnCancelar);
        
        VBox formContainer = new VBox(10);
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setPadding(new Insets(10));
        formContainer.getChildren().addAll(
            lblTipo, cbTipoAtividade,
            lblData, datePicker,
            lblCalorias, txtCalorias
        );
        
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(titulo, formContainer, botoesContainer);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Registrar Atividade - CalorieTrack");
        stage.setScene(scene);
    }
    
    private void abrirTelaRegistrarRefeicao(Stage stage, Usuario usuario) {
        Label titulo = new Label("Registrar Refeição");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        VBox itensContainer = new VBox(20);
        itensContainer.setPadding(new Insets(20));
        
        Label lblTotalCalorias = new Label("Total: 0 kcal");
        lblTotalCalorias.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        Runnable atualizarTotalCalorias = () -> {
            double total = 0;
            for (var node : itensContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox linha = (HBox) node;
                    if (linha.getUserData() instanceof Double) {
                        total += (Double) linha.getUserData();
                    }
                }
            }
            lblTotalCalorias.setText(String.format("Total: %.1f kcal", total));
        };
    
        Function<HBox, HBox> adicionarLinha = (linhaExistente) -> {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER_LEFT);
            
            ComboBox<String> cbAlimento = new ComboBox<>();
            cbAlimento.setPromptText("Buscar alimento...");
            cbAlimento.setEditable(true);
            cbAlimento.setPrefWidth(250);
            
            Label lblCalorias = new Label("0 kcal");
            lblCalorias.setPrefWidth(100);
            lblCalorias.setStyle("-fx-text-fill: #2C3E50; -fx-font-weight: bold;");
            
            TextField txtQuantidade = new TextField();
            txtQuantidade.setPromptText("Quantidade");
            txtQuantidade.setPrefWidth(100);
            txtQuantidade.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal.matches("\\d*\\.?\\d*")) {
                    txtQuantidade.setText(oldVal);
                }
            });
            
            Button btnRemover = new Button("-");
            btnRemover.setStyle(
                "-fx-background-color: #E74C3C; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-min-width: 30px; " +
                "-fx-pref-width: 30px; " +
                "-fx-max-width: 30px;"
            );
            btnRemover.setOnAction(e -> {
                itensContainer.getChildren().remove(linha);
                atualizarTotalCalorias.run();
            });
            
            cbAlimento.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.length() >= 3) {
                    cbAlimento.getItems().setAll(
                        "Maçã (52 kcal/100g)",
                        "Arroz cozido (130 kcal/100g)",
                        "Frango grelhado (165 kcal/100g)",
                        "Ovo cozido (155 kcal/100g)",
                        "Pão francês (300 kcal/100g)"
                    );
                    cbAlimento.show();
                }
            });
            
            cbAlimento.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    try {
                        String[] partes = newVal.split("\\s*\\(|\\)\\s*");
                        if (partes.length > 1) {
                            double calorias = Double.parseDouble(partes[1].split(" ")[0]);
                            linha.setUserData(calorias);
                            lblCalorias.setText(String.format("%.1f kcal", calorias));
                            atualizarTotalCalorias.run();
                        }
                    } catch (Exception e) {
                        linha.setUserData(0.0);
                        lblCalorias.setText("0 kcal");
                    }
                }
            });
            
            txtQuantidade.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal.isEmpty() && cbAlimento.getValue() != null) {
                    try {
                        double quantidade = Double.parseDouble(newVal);
                        double caloriasPor100g = (Double) linha.getUserData();
                        double calorias = (caloriasPor100g * quantidade) / 100.0;
                        lblCalorias.setText(String.format("%.1f kcal", calorias));
                        linha.setUserData(calorias);
                        atualizarCalorias(linha, calorias);
                        atualizarTotalCalorias.run();
                    } catch (NumberFormatException e) {
                    }
                }
            });
            
            linha.getChildren().addAll(cbAlimento, txtQuantidade, lblCalorias, btnRemover);
            
            if (linhaExistente != null) {
                int index = itensContainer.getChildren().indexOf(linhaExistente);
                itensContainer.getChildren().set(index, linha);
            } else {
                itensContainer.getChildren().add(linha);
            }
            
            return linha;
        };
        
        adicionarLinha.apply(null);
        
        Button btnAdicionarAlimento = new Button("+ Adicionar Alimento");
        btnAdicionarAlimento.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 5 15;"
        );
        btnAdicionarAlimento.setOnAction(e -> adicionarLinha.apply(null));
        
        Button btnSalvar = new Button("Salvar Refeição");
        btnSalvar.setStyle(
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        btnSalvar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Refeição Registrada");
            alert.setContentText("Sua refeição foi registrada com sucesso!");
            alert.showAndWait();
            abrirTelaMenu(stage, usuario);
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(
            "-fx-background-color: #E74C3C; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        btnCancelar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        HBox botoesContainer = new HBox(20, btnSalvar, btnCancelar);
        botoesContainer.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        
        ScrollPane scrollPane = new ScrollPane(itensContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        
        root.getChildren().addAll(titulo, scrollPane, btnAdicionarAlimento, lblTotalCalorias, botoesContainer);
        
        Scene scene = new Scene(root, 600, 700);
        stage.setTitle("Registrar Refeição - CalorieTrack");
        stage.setScene(scene);
    }
    
    private void atualizarCalorias(HBox linha, double calorias) {
        linha.setUserData(calorias);
    }
    
    private void abrirTelaAtividades(Stage stage, Usuario usuario) {
        Label titulo = new Label("Atividades");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        Button btnListarAtividades = new Button("Listar Atividades");
        btnListarAtividades.setPrefWidth(300);
        btnListarAtividades.setPrefHeight(60);
        btnListarAtividades.setStyle(
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-background-radius: 10px;"
        );
        btnListarAtividades.setOnMouseEntered(e -> 
            btnListarAtividades.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnListarAtividades.setOnMouseExited(e -> 
            btnListarAtividades.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnListarAtividades.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Lista de Atividades");
        });
        
        Button btnAdicionarAtividade = new Button("Adicionar Atividade");
        btnAdicionarAtividade.setPrefWidth(300);
        btnAdicionarAtividade.setPrefHeight(60);
        btnAdicionarAtividade.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-background-radius: 10px;"
        );
        btnAdicionarAtividade.setOnMouseEntered(e -> 
            btnAdicionarAtividade.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnAdicionarAtividade.setOnMouseExited(e -> 
            btnAdicionarAtividade.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnAdicionarAtividade.setOnAction(e -> {
            abrirTelaAdicionarAtividade(stage, usuario);
        });
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 10px;");
        btnVoltar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        VBox botoesContainer = new VBox(20);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.getChildren().addAll(btnListarAtividades, btnAdicionarAtividade);
        
        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(titulo, botoesContainer, btnVoltar);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Atividades - CalorieTrack");
        stage.setScene(scene);
    }
    
    private void abrirTelaConsultaAlimentos(Stage stage, Usuario usuario) {
        Label titulo = new Label("Consultar Alimentos");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        TextField campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o nome do alimento...");
        campoPesquisa.setPrefWidth(400);
        campoPesquisa.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");
        
        ListView<String> listaResultados = new ListView<>();
        listaResultados.setPrefHeight(500);
        listaResultados.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 5px;");
        
        campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                listaResultados.getItems().clear();
                listaResultados.getItems().add("Resultado para: " + newValue);
            } else {
                listaResultados.getItems().clear();
            }
        });
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 10px;");
        btnVoltar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(titulo, campoPesquisa, listaResultados, btnVoltar);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Consultar Alimentos - CalorieTrack");
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