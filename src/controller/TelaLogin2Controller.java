package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Usuario;
import model.Sessao;
import util.CaminhoArquivo;
// Importe suas exceções se necessário
// import util.SenhaInvalidaException;
// import util.CpfInvalidoException;

public class TelaLogin2Controller implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelaLogin; // Certifique-se que o fx:id do painel principal é este
    @FXML
    private TextField textFildLogin;
    @FXML
    private PasswordField textFildSenha;
    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonCadastro;
    @FXML
    private Label labelAcessoInvalido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }
    
    @FXML
    private void handleButtonLogin(ActionEvent event) {
        if (!textFildLogin.getText().isEmpty() && !textFildSenha.getText().isEmpty()) {
             labelAcessoInvalido.setText("");

            String cpf = textFildLogin.getText();
            String senha = textFildSenha.getText();

            try{
                ControleUsuario controle = new ControleUsuario();
                controle.confirmarCpf(cpf);
                controle.confirmarSenha(cpf, senha);
                Usuario login = controle.login(cpf);
                Sessao.getInstance().setUsuarioLogado(login);
                trocarTela(anchorPaneTelaLogin, CaminhoArquivo.TELA_CONTA); 
            } catch(IOException ex) {
              System.err.println("Erro ao carregar tela da conta: " + ex.getMessage());
                ex.printStackTrace();
            } catch (Exception e) { 
                System.err.println("Erro: " + e.getMessage());
                labelAcessoInvalido.setText("Login ou Senha incorretos."); 
            }
        } else {
            labelAcessoInvalido.setText("Preencha todos os campos.");
            textFildLogin.requestFocus();
        }
    }
        
    @FXML
    private void handleButtonCadastro(ActionEvent event) {
        try{
            trocarTela(anchorPaneTelaLogin, CaminhoArquivo.TELA_CADASTRO); 
        } catch(IOException ex) {
            System.err.println("Erro ao carregar tela de cadastro: " + ex.getMessage());
            ex.printStackTrace();
        }
    } 
}