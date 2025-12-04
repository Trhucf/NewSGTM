package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl; 
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import util.CaminhoArquivo;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.Usuario;
import data.RepositorioArquivoUsuario;
import controller.CpfInvalidoException; 
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class TelaCadastroController implements Initializable {

    private static final String SUCESSO_COR = "green";
    private static final String ERRO_COR = "#EC5800";
    private static final String ESTILO_ERRO_BORDA = "-fx-border-color: " + ERRO_COR + "; -fx-border-width: 2px;";
    
    List<String> modalidades = List.of("Corporativo", "Estudante", "Idoso", "Simples", "Especial");
    
    @FXML
    private TextField textFieldModalidade;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldNome;
    @FXML
    private Button buttonCadastro;
    @FXML
    private PasswordField passwordFieldSenha;
    @FXML
    private Label labelStatusCadastro;
    @FXML
    private AnchorPane anchorPaneTelaCadastro; 
    private List<TextInputControl> camposDeEntrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        camposDeEntrada = Arrays.asList(textFieldNome, textFieldCPF, textFieldModalidade, passwordFieldSenha);
        
        resetarEstilosCampos();
        labelStatusCadastro.setText("");
        configurarAutocomplete(textFieldModalidade, modalidades);
    }
    
    private void configurarAutocomplete(TextField campo, List<String> dados) {
        ContextMenu contextMenu = new ContextMenu();
        java.util.function.Consumer<String> atualizarLista = (textoAtual) -> {
            List<String> resultados;
            if (textoAtual == null || textoAtual.trim().isEmpty()) {
                resultados = new ArrayList<>(dados);
            } else {
                resultados = dados.stream()
                        .filter(s -> s.toLowerCase().contains(textoAtual.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (!resultados.isEmpty()) {
                contextMenu.getItems().clear();
                for (String resultado : resultados) {
                    MenuItem item = new MenuItem(resultado);
                    item.setOnAction(e -> {
                        campo.setText(resultado);
                        campo.positionCaret(resultado.length());
                        contextMenu.hide();
                    });
                    contextMenu.getItems().add(item);
                }
                if (!contextMenu.isShowing()) { 
                    contextMenu.show(campo, javafx.geometry.Side.BOTTOM, 0, 5);
                }
            } else {
                contextMenu.hide();
            }
        };

        campo.textProperty().addListener((observable, oldValue, newValue) -> atualizarLista.accept(newValue));
        campo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) atualizarLista.accept(campo.getText());
            else contextMenu.hide();
        });
        campo.setOnMouseClicked(event -> atualizarLista.accept(campo.getText()));
    }

    private void limparCamposDaTela() {
        camposDeEntrada.forEach(TextInputControl::clear);
        resetarEstilosCampos();
        labelStatusCadastro.setText("");
    }

    private void resetarEstilosCampos() {
        camposDeEntrada.forEach(campo -> campo.setStyle(""));
    }
    
    private void setStatus(String mensagem, String cor) {
        labelStatusCadastro.setText(mensagem);
        labelStatusCadastro.setStyle("-fx-text-fill: " + cor + ";");
    }

    private void trocarParaTelaLogin(String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));

        AnchorPane painelAtual = anchorPaneTelaCadastro;

        if (painelAtual == null) {
            if (buttonCadastro.getScene() != null) {
                painelAtual = (AnchorPane) buttonCadastro.getScene().getRoot();
            } else {
                throw new IOException("Erro ao trocar de tela!");
            }
        }

        painelAtual.getChildren().setAll(novaTela);
    }

    private boolean validarCamposObrigatorios() {
      
        boolean camposVazios = false;
        for (TextInputControl campo : camposDeEntrada) {
            if (campo.getText().trim().isEmpty()) {
                campo.setStyle(ESTILO_ERRO_BORDA);
                camposVazios = true;
            } else {
                campo.setStyle(""); 
            }
        }
        
        if (camposVazios) {
            setStatus("Preencha todos os campos!", ERRO_COR);
            return false;
        }

        String senha = passwordFieldSenha.getText();
        if (senha.length() < 4 || senha.length() > 8) {
            setStatus("Crie uma senha de 4 a 8 dígitos!", ERRO_COR);
            passwordFieldSenha.setStyle(ESTILO_ERRO_BORDA);
            return false;
        }
        String cpf = textFieldCPF.getText();
        if(cpf.length() != 11 ){
            setStatus("CPF invalido!", ERRO_COR );
             textFieldCPF.setStyle(ESTILO_ERRO_BORDA);
            return false;
        }
        return true;
    }


    @FXML
    private void handleButtonCadastro(ActionEvent event) {
        
        resetarEstilosCampos(); 
        setStatus("", "black"); 
        
        if (!validarCamposObrigatorios()) {
            return; 
        }

        final String nome = textFieldNome.getText().trim().toUpperCase();
        final String cpf = textFieldCPF.getText().trim();
        
        final String nomeModalidade = textFieldModalidade.getText(); 
        
        final String senha = passwordFieldSenha.getText();

        try {
        
            ControleUsuario controleUsuario = new ControleUsuario();
            
            try {
                controleUsuario.confirmarCpf(cpf);
                setStatus("CPF já cadastrado no sistema!", ERRO_COR);
                textFieldCPF.setStyle(ESTILO_ERRO_BORDA);
                return;
            } catch (CpfInvalidoException e) {
            }
            
            Usuario novoUsuario = controleUsuario.criarUsuario(nome, cpf, senha);
            
            int codigoModalidade;
            
            switch (nomeModalidade) {
                case "Estudante":   codigoModalidade = 1; break;
                case "Idoso":       codigoModalidade = 2; break;
                case "Especial":    codigoModalidade = 3; break;
                case "Corporativo": codigoModalidade = 4; break;
                case "Simples":     codigoModalidade = 5; break;
                default:            codigoModalidade = 5; 
            }

            ControleCartao controleCartao = new ControleCartao();
            
            controleCartao.criarCartao(novoUsuario, codigoModalidade);
            
            controleUsuario.arquivarUsuario(novoUsuario);

            setStatus("Cadastro realizado com sucesso!", SUCESSO_COR);
            
            new Thread(() -> {
                try {
                    Thread.sleep(2000); 
                    javafx.application.Platform.runLater(() -> {
                        try {
                            trocarParaTelaLogin(CaminhoArquivo.TELA_LOGIN);
                        } catch (IOException e) {
                            System.err.println("Erro ao carregar tela de login: " + e.getMessage());
                            setStatus("Erro ao carregar tela de login!", ERRO_COR);
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
            
        } catch (Exception e) {
            System.err.println("Erro inesperado durante o cadastro: " + e.getMessage());
            setStatus("Erro inesperado: " + e.getMessage(), ERRO_COR);
            e.printStackTrace();
        }
    }
}