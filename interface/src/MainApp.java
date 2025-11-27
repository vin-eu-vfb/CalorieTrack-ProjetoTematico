package CalorieTrack;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.util.StringConverter;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;

import CalorieTrack.Classes.Funcoes;
import CalorieTrack.Classes.Usuario;
import CalorieTrack.Classes.ConexaoBD;
import CalorieTrack.Classes.TabelaNutricional;
import CalorieTrack.Classes.TabelaNutricionalDAO;
import CalorieTrack.Classes.Refeicao;
import CalorieTrack.Classes.RefeicaoDAO;
import CalorieTrack.Classes.Exercicio;
import CalorieTrack.Classes.ExercicioDAO;

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
        
        Button btnRegistrarRefeicao = new Button("🍽️ Refeição");
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
        btnRegistrarRefeicao.setOnAction(e -> {
            abrirTelaRefeicoes(stage, usuario);
        });
        
        Button btnRegistrarAtividade = new Button("🏃 Atividade");
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
        
        Button btnReceitas = new Button("📖 Receitas");
        btnReceitas.setPrefWidth(380);
        btnReceitas.setPrefHeight(65);
        btnReceitas.setStyle(
            baseButtonStyle +
            "-fx-background-color: #16A085; " +
            "-fx-text-fill: white;"
        );
        btnReceitas.setOnMouseEntered(e -> 
            btnReceitas.setStyle(baseButtonStyle + "-fx-background-color: #138D75; -fx-text-fill: white;")
        );
        btnReceitas.setOnMouseExited(e -> 
            btnReceitas.setStyle(baseButtonStyle + "-fx-background-color: #16A085; -fx-text-fill: white;")
        );
        btnReceitas.setOnAction(e -> {
            abrirTelaDesenvolvimento(stage, usuario, "Receitas");
        });
        
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
        
        // Botão para listar refeições
        Button btnListarRefeicoes = new Button("Listar Refeições");
        btnListarRefeicoes.setStyle(
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 40px;"
        );
        btnListarRefeicoes.setOnAction(e -> {
            abrirTelaListarRefeicoes(stage, usuario);
        });
        
        // Botão para adicionar refeição
        Button btnAdicionarRefeicao = new Button("Adicionar Refeição");
        btnAdicionarRefeicao.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 40px;"
        );
        btnAdicionarRefeicao.setOnAction(e -> {
            abrirTelaAdicionarRefeicao(stage, usuario);
        });
        
        btnRegistrarAtividade.setOnAction(e -> {
            abrirTelaAtividades(stage, usuario);
        });
        
        btnSugerirAlimento.setOnAction(e -> {
            abrirTelaSugerirAlimento(stage, usuario);
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
            btnReceitas,
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

        Label lblDuracao = new Label("Duração (minutos):");
        lblDuracao.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50; -fx-padding: 10 0 0 0;");
        TextField txtDuracao = new TextField();
        txtDuracao.setPromptText("Ex.: 45");
        txtDuracao.setPrefWidth(400);
        txtDuracao.textProperty().addListener((obs, ov, nv) -> {
            if (!nv.matches("\\d*\\.?\\d*")) txtDuracao.setText(ov);
        });

        Label lblIntensidade = new Label("Intensidade:");
        lblIntensidade.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50; -fx-padding: 10 0 0 0;");
        ComboBox<String> cbIntensidade = new ComboBox<>();
        cbIntensidade.getItems().addAll("Leve", "Moderada", "Intensa");
        cbIntensidade.setPromptText("Selecione a intensidade");
        cbIntensidade.setPrefWidth(400);
        
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
            if (cbTipoAtividade.getValue() == null || txtCalorias.getText().trim().isEmpty() ||
                txtDuracao.getText().trim().isEmpty() || cbIntensidade.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Campos obrigatórios");
                alert.setContentText("Por favor, preencha tipo, data, calorias, duração e intensidade.");
                alert.showAndWait();
                return;
            }
            try (Connection conn = ConexaoBD.conectar()) {
                if (conn == null) throw new SQLException("Sem conexão com o banco.");
                ExercicioDAO exercicioDAO = new ExercicioDAO(conn);
                Exercicio exercicio = new Exercicio();
                exercicio.setNome(cbTipoAtividade.getValue());
                exercicio.setDuracao(Double.parseDouble(txtDuracao.getText().trim()));
                exercicio.setIntensidade(cbIntensidade.getValue());
                exercicio.setCaloriasGastas(Double.parseDouble(txtCalorias.getText().trim()));
                exercicioDAO.adicionarBD(exercicio, usuario.getIdUsuario());

                String mensagemSucesso = String.format(
                    "Atividade salva com sucesso!\n\nTipo: %s\nData: %s\nCalorias: %s kcal\nDuração: %s min\nIntensidade: %s",
                    cbTipoAtividade.getValue(),
                    datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    txtCalorias.getText().trim(),
                    txtDuracao.getText().trim(),
                    cbIntensidade.getValue()
                );
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Atividade Registrada");
                alert.setContentText(mensagemSucesso);
                alert.showAndWait();
                abrirTelaAtividades(stage, usuario);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao salvar atividade: " + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
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
            lblCalorias, txtCalorias,
            lblDuracao, txtDuracao,
            lblIntensidade, cbIntensidade
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
    
    private void abrirTelaListarRefeicoes(Stage stage, Usuario usuario) {
        Label titulo = new Label("Minhas Refeições");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        // Tabela para exibir as refeições
        TableView<Refeicao> tabela = new TableView<>();
        
        // Colunas da tabela
        TableColumn<Refeicao, String> colNome = new TableColumn<>("Refeição");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeRefeicao"));
        
        TableColumn<Refeicao, String> colData = new TableColumn<>("Data/Hora");
        colData.setCellValueFactory(cellData -> {
            String texto = cellData.getValue().getHorario();
            DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            try {
                // Tenta formatos comuns do banco
                LocalDateTime dt;
                try {
                    dt = LocalDateTime.parse(texto, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } catch (Exception ex1) {
                    try {
                        dt = LocalDateTime.parse(texto, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    } catch (Exception ex2) {
                        dt = LocalDateTime.parse(texto); // fallback ISO
                    }
                }
                return new SimpleStringProperty(dt.format(br));
            } catch (Exception e) {
                return new SimpleStringProperty("Data inválida");
            }
        });
        
        TableColumn<Refeicao, String> colCalorias = new TableColumn<>("Calorias");
        colCalorias.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("%.1f kcal", cellData.getValue().getCaloriasTotais()))
        );
        
        tabela.getColumns().addAll(java.util.Arrays.asList(colNome, colData, colCalorias));
        
        // Carregar as refeições do banco de dados
        try (Connection conn = ConexaoBD.conectar()) {
            if (conn != null) {
                RefeicaoDAO refeicaoDAO = new RefeicaoDAO(conn);
                List<Refeicao> refeicoes = refeicaoDAO.procurarDB(usuario.getIdUsuario());
                tabela.getItems().addAll(refeicoes);
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, 
                "Erro ao carregar refeições: " + ex.getMessage(), 
                ButtonType.OK);
            alert.showAndWait();
        }
        
        // Botão para adicionar nova refeição
        Button btnNovaRefeicao = new Button("Nova Refeição");
        btnNovaRefeicao.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-pref-width: 150px;"
        );
        btnNovaRefeicao.setOnAction(e -> {
            abrirTelaAdicionarRefeicao(stage, usuario);
        });
        
        // Botão para voltar
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(
            "-fx-background-color: #E74C3C; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-pref-width: 150px;"
        );
        btnVoltar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        // Layout
        HBox botoes = new HBox(20, btnNovaRefeicao, btnVoltar);
        botoes.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(20, titulo, tabela, botoes);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        
        // Configurar a cena
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Minhas Refeições - CalorieTrack");
    }
    
    private void abrirTelaAdicionarRefeicao(Stage stage, Usuario usuario) {
        Label titulo = new Label("Registrar Refeição");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        VBox itensContainer = new VBox(20);
        itensContainer.setPadding(new Insets(20));
        
        Label lblTotalCalorias = new Label("Total: 0 kcal");
        lblTotalCalorias.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        // Campo para nome da refeição
        Label lblNomeRefeicao = new Label("Nome da Refeição:");
        TextField txtNomeRefeicao = new TextField();
        txtNomeRefeicao.setPromptText("Ex: Café da Manhã, Almoço, Jantar...");
        txtNomeRefeicao.setPrefWidth(400);
        
        // Cabeçalho acima dos campos
        HBox headerLinha = new HBox(10);
        headerLinha.setAlignment(Pos.CENTER_LEFT);
        Label lblHeaderAlimento = new Label("Alimento");
        lblHeaderAlimento.setPrefWidth(250);
        lblHeaderAlimento.setStyle("-fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        Label lblHeaderGramas = new Label("Gramas");
        lblHeaderGramas.setPrefWidth(100);
        lblHeaderGramas.setStyle("-fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        headerLinha.getChildren().addAll(lblHeaderAlimento, lblHeaderGramas);

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
    
        // Adiciona uma linha com dados vindos do banco
        Function<HBox, HBox> adicionarLinha = (linhaExistente) -> {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER_LEFT);
            linha.setUserData(0.0);
            
            ComboBox<TabelaNutricional> cbAlimento = new ComboBox<>();
            cbAlimento.setPromptText("Buscar alimento...");
            cbAlimento.setEditable(true);
            cbAlimento.setPrefWidth(250);
            // Exibir nome no dropdown e no botão
            cbAlimento.setCellFactory(lv -> new ListCell<TabelaNutricional>() {
                @Override
                protected void updateItem(TabelaNutricional item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getNome());
                }
            });
            cbAlimento.setButtonCell(new ListCell<TabelaNutricional>() {
                @Override
                protected void updateItem(TabelaNutricional item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getNome());
                }
            });
            
            // Carregar alimentos do BD
            try (Connection conn = ConexaoBD.conectar()) {
                if (conn != null) {
                    TabelaNutricionalDAO tabelaDAO = new TabelaNutricionalDAO(conn);
                    List<TabelaNutricional> alimentos = tabelaDAO.buscarBD();
                    cbAlimento.getItems().setAll(alimentos);

                    // Converter texto digitado em item válido
                    cbAlimento.setConverter(new StringConverter<TabelaNutricional>() {
                        @Override
                        public String toString(TabelaNutricional object) {
                            return object == null ? "" : object.getNome();
                        }
                        @Override
                        public TabelaNutricional fromString(String string) {
                            if (string == null) return null;
                            String s = string.trim().toLowerCase();
                            for (TabelaNutricional a : alimentos) {
                                if (a.getNome() != null && a.getNome().toLowerCase().equals(s)) {
                                    return a;
                                }
                            }
                            return null;
                        }
                    });

                    // Habilitar busca por nome (filtro dinâmico)
                    cbAlimento.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
                        String termo = newVal == null ? "" : newVal.trim().toLowerCase();
                        if (termo.length() >= 1) {
                            List<TabelaNutricional> filtrados = alimentos.stream()
                                .filter(a -> a.getNome() != null && a.getNome().toLowerCase().contains(termo))
                                .collect(Collectors.toList());
                            cbAlimento.getItems().setAll(filtrados);
                            cbAlimento.show();
                        } else {
                            cbAlimento.getItems().setAll(alimentos);
                        }
                    });
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar alimentos: " + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
            
            Label lblCalorias = new Label("0 kcal");
            lblCalorias.setPrefWidth(100);
            lblCalorias.setStyle("-fx-text-fill: #2C3E50; -fx-font-weight: bold;");
            
            TextField txtQuantidade = new TextField();
            txtQuantidade.setPromptText("Quantidade (g)");
            txtQuantidade.setPrefWidth(100);
            txtQuantidade.setText("100");
            txtQuantidade.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal.matches("\\d*(\\.\\d*)?")) {
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
            
            // Atualiza calorias ao selecionar alimento ou alterar quantidade
            cbAlimento.setOnAction(event -> {
                calcularLinhaCalorias(cbAlimento, txtQuantidade, lblCalorias, linha);
                atualizarTotalCalorias.run();
            });
            txtQuantidade.textProperty().addListener((obs, o, n) -> {
                calcularLinhaCalorias(cbAlimento, txtQuantidade, lblCalorias, linha);
                atualizarTotalCalorias.run();
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
        
        // Initialize buttons
        Button btnSalvar = new Button("Salvar Refeição");
        btnSalvar.setStyle(
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        
        Button btnAdicionarAlimento = new Button("+ Adicionar Alimento");
        btnAdicionarAlimento.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(
            "-fx-background-color: #E74C3C; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 200px; " +
            "-fx-pref-height: 45px; " +
            "-fx-background-radius: 10px;"
        );
        
        // Set button actions
        btnAdicionarAlimento.setOnAction(e -> adicionarLinha.apply(null));
        btnCancelar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        // Save button action
        btnSalvar.setOnAction(e -> {
            if (txtNomeRefeicao.getText().trim().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Informe o nome da refeição.", ButtonType.OK);
                a.showAndWait();
                return;
            }
            try (Connection conn = ConexaoBD.conectar()) {
                if (conn == null) {
                    throw new SQLException("Sem conexão com o banco.");
                }
                // Monta objeto Refeicao
                Refeicao r = new Refeicao();
                r.setNomeRefeicao(txtNomeRefeicao.getText().trim());
                r.setHorario(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00")));
                
                // Soma calorias e coleta itens selecionados
                double total = 0.0;
                List<TabelaNutricional> itens = new ArrayList<>();
                for (var n : itensContainer.getChildren()) {
                    if (n instanceof HBox) {
                        HBox l = (HBox) n;
                        Object ud = l.getUserData();
                        if (ud instanceof Double) total += (Double) ud;
                        // recuperar ComboBox dentro da linha
                        for (var child : l.getChildren()) {
                            if (child instanceof ComboBox<?>) {
                                @SuppressWarnings("unchecked")
                                ComboBox<TabelaNutricional> cb = (ComboBox<TabelaNutricional>) child;
                                if (cb.getValue() != null) {
                                    // Get the quantity from the text field in the same row
                                    for (var qtyField : l.getChildren()) {
                                        if (qtyField instanceof TextField) {
                                            try {
                                                double quantidade = Double.parseDouble(((TextField)qtyField).getText());
                                                TabelaNutricional item = cb.getValue();
                                                item.setQuantidade(quantidade); // Set the quantity on the food item
                                                itens.add(item);
                                                break;
                                            } catch (NumberFormatException ex) {
                                                // If quantity is not a valid number, add the item with quantity 0
                                                itens.add(cb.getValue());
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                r.setCaloriasTotais(total);
                r.setTabelaNutricional(new ArrayList<>(itens));
                
                RefeicaoDAO refeicaoDAO = new RefeicaoDAO(conn);
                refeicaoDAO.adicionarDB(r, usuario.getIdUsuario());
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Refeição registrada com sucesso!");
                alert.setContentText(String.format("Refeição '%s' com %d itens e total de %.1f kcal.",
                    r.getNomeRefeicao(), itens.size(), total));
                alert.showAndWait();
                abrirTelaListarRefeicoes(stage, usuario);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao salvar refeição: " + ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        
        // Layout
        HBox botoesContainer = new HBox(20, btnSalvar, btnCancelar);
        botoesContainer.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        
        VBox formContainer = new VBox(10);
        formContainer.setAlignment(Pos.TOP_CENTER);
        formContainer.getChildren().addAll(new VBox(5, lblNomeRefeicao, txtNomeRefeicao), headerLinha, itensContainer);

        ScrollPane scrollPane = new ScrollPane(formContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        
        VBox content = new VBox(15, titulo, scrollPane, btnAdicionarAlimento, lblTotalCalorias, botoesContainer);
        content.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(content, 600, 700);
        stage.setTitle("Registrar Refeição - CalorieTrack");
        stage.setScene(scene);
        stage.show();
    }
    
    private void atualizarCalorias(HBox linha, double calorias) {
        linha.setUserData(calorias);
    }
    
    private void calcularLinhaCalorias(ComboBox<TabelaNutricional> cbAlimento, TextField txtQuantidade, Label lblCalorias, HBox linha) {
        TabelaNutricional alimento = null;
        Object val = cbAlimento.getValue();
        if (val instanceof TabelaNutricional) {
            alimento = (TabelaNutricional) val;
        } else {
            String nome = cbAlimento.getEditor() != null ? cbAlimento.getEditor().getText() : null;
            if (nome != null) {
                String s = nome.trim().toLowerCase();
                for (TabelaNutricional a : cbAlimento.getItems()) {
                    if (a.getNome() != null && a.getNome().toLowerCase().equals(s)) {
                        alimento = a;
                        cbAlimento.setValue(a);
                        break;
                    }
                }
            }
        }
        double quantidade = 0.0;
        try {
            if (!txtQuantidade.getText().isEmpty()) {
                quantidade = Double.parseDouble(txtQuantidade.getText());
            }
        } catch (NumberFormatException ex) {
            // mantém a quantidade padrão de 100g
        }

        double calorias = 0.0;
        if (alimento != null) {
            calorias = (alimento.getCalorias() * quantidade) / 100.0;
        }
        lblCalorias.setText(String.format("%.1f kcal", calorias));
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
        btnListarAtividades.setOnAction(e -> abrirTelaListarAtividades(stage, usuario));
        
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
    
    private void abrirTelaRefeicoes(Stage stage, Usuario usuario) {
        Label titulo = new Label("Refeições");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        Button btnListarRefeicoes = new Button("Listar Refeições");
        btnListarRefeicoes.setPrefWidth(300);
        btnListarRefeicoes.setPrefHeight(60);
        btnListarRefeicoes.setStyle(
            "-fx-background-color: #3498DB; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-background-radius: 10px;"
        );
        btnListarRefeicoes.setOnMouseEntered(e -> 
            btnListarRefeicoes.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnListarRefeicoes.setOnMouseExited(e -> 
            btnListarRefeicoes.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnListarRefeicoes.setOnAction(e -> abrirTelaListarRefeicoes(stage, usuario));
        
        Button btnAdicionarRefeicao = new Button("Adicionar Refeição");
        btnAdicionarRefeicao.setPrefWidth(300);
        btnAdicionarRefeicao.setPrefHeight(60);
        btnAdicionarRefeicao.setStyle(
            "-fx-background-color: #2ECC71; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-background-radius: 10px;"
        );
        btnAdicionarRefeicao.setOnMouseEntered(e -> 
            btnAdicionarRefeicao.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnAdicionarRefeicao.setOnMouseExited(e -> 
            btnAdicionarRefeicao.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10px;")
        );
        btnAdicionarRefeicao.setOnAction(e -> abrirTelaAdicionarRefeicao(stage, usuario));
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 10px;");
        btnVoltar.setOnAction(e -> abrirTelaMenu(stage, usuario));
        
        VBox botoesContainer = new VBox(20);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.getChildren().addAll(btnListarRefeicoes, btnAdicionarRefeicao);
        
        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(titulo, botoesContainer, btnVoltar);
        
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Refeições - CalorieTrack");
        stage.setScene(scene);
    }
    
    private void abrirTelaConsultaAlimentos(Stage stage, Usuario usuario) {
        Label titulo = new Label("Consultar Alimentos");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        TextField campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o nome do alimento...");
        campoPesquisa.setPrefWidth(400);
        campoPesquisa.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");
        
        ListView<TabelaNutricional> listaResultados = new ListView<>();
        listaResultados.setPrefHeight(500);
        listaResultados.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 5px;");
        listaResultados.setCellFactory(lv -> new ListCell<TabelaNutricional>() {
            @Override
            protected void updateItem(TabelaNutricional item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%s — %.0f kcal/100g", item.getNome(), item.getCalorias()));
                }
            }
        });
        
        Runnable carregarLista = () -> {
            String termo = campoPesquisa.getText() == null ? "" : campoPesquisa.getText().trim().toLowerCase();
            try (Connection conn = ConexaoBD.conectar()) {
                if (conn == null) throw new SQLException("Sem conexão com o banco.");
                TabelaNutricionalDAO dao = new TabelaNutricionalDAO(conn);
                List<TabelaNutricional> todos = dao.buscarBD();
                List<TabelaNutricional> filtrados = todos.stream()
                    .filter(t -> termo.isEmpty() || (t.getNome() != null && t.getNome().toLowerCase().contains(termo)))
                    .collect(Collectors.toList());
                listaResultados.getItems().setAll(filtrados);
            } catch (SQLException ex) {
                listaResultados.getItems().setAll();
            }
        };
        
        campoPesquisa.textProperty().addListener((obs, o, n) -> carregarLista.run());
        carregarLista.run();
        
        listaResultados.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                TabelaNutricional sel = listaResultados.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    String detalhes = String.format(
                        "Nome: %s\nCalorias: %.0f kcal/100g\nProteínas: %.1f g\nCarboidratos: %.1f g\nGorduras: %.1f g",
                        sel.getNome(), sel.getCalorias(), sel.getProteinas(), sel.getCarboidratos(), sel.getGorduras()
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalhes do Alimento");
                    alert.setHeaderText(sel.getNome());
                    alert.setContentText(detalhes);
                    alert.showAndWait();
                }
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
    
    private void abrirTelaListarAtividades(Stage stage, Usuario usuario) {
        Label titulo = new Label("Atividades do Usuário");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        ListView<Exercicio> lista = new ListView<>();
        lista.setPrefHeight(550);
        lista.setCellFactory(lv -> new ListCell<Exercicio>() {
            @Override
            protected void updateItem(Exercicio item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%s — %.0f kcal", item.getNome(), item.getCaloriasGastas()));
                }
            }
        });

        try (Connection conn = ConexaoBD.conectar()) {
            if (conn == null) throw new SQLException("Sem conexão com o banco.");
            ExercicioDAO dao = new ExercicioDAO(conn);
            List<Exercicio> exercicios = dao.buscarBD(usuario.getIdUsuario());
            lista.getItems().setAll(exercicios);
        } catch (SQLException ex) {
            // em caso de erro, mostra item textual simples
            ListView<String> erro = new ListView<>();
            erro.getItems().setAll("Erro ao carregar atividades: " + ex.getMessage());
            VBox rootErro = new VBox(20, titulo, erro);
            rootErro.setAlignment(Pos.TOP_CENTER);
            rootErro.setPadding(new Insets(30));
            Scene scene = new Scene(rootErro, 550, 800);
            stage.setTitle("Atividades - CalorieTrack");
            stage.setScene(scene);
            return;
        }

        lista.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Exercicio ex = lista.getSelectionModel().getSelectedItem();
                if (ex != null) {
                    String detalhes = String.format(
                        "Nome: %s\nDuração: %.0f min\nIntensidade: %s\nCalorias Gastas: %.0f kcal",
                        ex.getNome(), ex.getDuracao(), ex.getIntensidade(), ex.getCaloriasGastas()
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalhes da Atividade");
                    alert.setHeaderText(ex.getNome());
                    alert.setContentText(detalhes);
                    alert.showAndWait();
                }
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(200);
        btnVoltar.setStyle("-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 10px;");
        btnVoltar.setOnAction(e -> abrirTelaAtividades(stage, usuario));

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");
        root.getChildren().addAll(titulo, lista, btnVoltar);

        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Atividades - CalorieTrack");
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
    
    private void abrirTelaSugerirAlimento(Stage stage, Usuario usuario) {
        Label titulo = new Label("Sugerir Alimento");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do alimento");
        txtNome.setPrefWidth(400);

        TextField txtQuantidade = new TextField();
        txtQuantidade.setPromptText("Quantidade (g)");
        txtQuantidade.setPrefWidth(400);
        txtQuantidade.setText("100");
        txtQuantidade.setEditable(false);

        TextField txtCalorias = new TextField();
        txtCalorias.setPromptText("Calorias por 100g");
        txtCalorias.setPrefWidth(400);

        TextField txtProteinas = new TextField();
        txtProteinas.setPromptText("Proteínas por 100g (g)");
        txtProteinas.setPrefWidth(400);

        TextField txtCarboidratos = new TextField();
        txtCarboidratos.setPromptText("Carboidratos por 100g (g)");
        txtCarboidratos.setPrefWidth(400);

        Label msg = new Label();
        msg.setVisible(false);

        Button btnSalvar = new Button("Salvar Sugestão");
        btnSalvar.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-background-radius: 10px;");
        btnSalvar.setOnAction(e -> {
            try {
                String nome = txtNome.getText().trim();
                String qStr = txtQuantidade.getText().trim();
                String calsStr = txtCalorias.getText().trim();
                String protStr = txtProteinas.getText().trim();
                String carbStr = txtCarboidratos.getText().trim();

                if (nome.isEmpty() || qStr.isEmpty() || calsStr.isEmpty() || protStr.isEmpty() || carbStr.isEmpty()) {
                    mostrarMensagem(msg, "Preencha todos os campos.", "erro");
                    return;
                }

                double quantidade = Double.parseDouble(qStr);
                double calorias = Double.parseDouble(calsStr);
                double proteinas = Double.parseDouble(protStr);
                double carboidratos = Double.parseDouble(carbStr);

                try (Connection conn = ConexaoBD.conectar()) {
                    if (conn == null) throw new SQLException("Sem conexão com o banco.");
                    TabelaNutricionalDAO dao = new TabelaNutricionalDAO(conn);
                    TabelaNutricional tn = new TabelaNutricional();
                    tn.setNome(nome);
                    tn.setQuantidade(quantidade);
                    tn.setCalorias(calorias);
                    tn.setProteinas(proteinas);
                    tn.setCarboidratos(carboidratos);
                    tn.setGorduras(0.0);
                    dao.adicionarBD(tn);
                }

                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Alimento sugerido com sucesso!", ButtonType.OK);
                ok.showAndWait();
                abrirTelaMenu(stage, usuario);
            } catch (NumberFormatException nfe) {
                mostrarMensagem(msg, "Valores numéricos inválidos.", "erro");
            } catch (SQLException ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Erro ao salvar sugestão: " + ex.getMessage(), ButtonType.OK);
                err.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-background-radius: 10px;");
        btnCancelar.setOnAction(e -> abrirTelaMenu(stage, usuario));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.addRow(0, new Label("Nome:"), txtNome);
        form.addRow(1, new Label("Quantidade (g):"), txtQuantidade);
        form.addRow(2, new Label("Calorias/100g:"), txtCalorias);
        form.addRow(3, new Label("Proteínas/100g (g):"), txtProteinas);
        form.addRow(4, new Label("Carboidratos/100g (g):"), txtCarboidratos);

        HBox botoes = new HBox(16, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, titulo, form, msg, botoes);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F9FA 0%, #FFFFFF 100%);");

        Scene scene = new Scene(root, 550, 800);
        stage.setTitle("Sugerir Alimento - CalorieTrack");
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