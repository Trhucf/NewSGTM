/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Sessao;
import model.Usuario;
import util.CaminhoArquivo;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class TelaContaAlterarSenhaController implements Initializable {

    @FXML
    private TextField fieldTextSenhaAtual;
    @FXML
    private TextField fieldTextSenhaNova;
    @FXML
    private Button buttonOk;
    @FXML
    private AnchorPane AnchorPaneSenhaAlterada;
    @FXML
    private Text textNome;
    @FXML
    private Text textCpf;
    @FXML
    private Text textNumeroCartao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
        
        if (usuario != null) {
            this.textNome.setText(usuario.getNome());
            this.textCpf.setText(usuario.getCpf());
            if (usuario.getCartao() != null) {
            this.textNumeroCartao.setText(usuario.getNumeroCartao());
            }
        }
    }    

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }
    
    @FXML
    private void handleButtonOk(ActionEvent event) {
        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
         String senhaAntiga = fieldTextSenhaAtual.getText();
         String senhaNova = fieldTextSenhaNova.getText();

         if (usuario != null && !senhaAntiga.isEmpty() && !senhaNova.isEmpty()) {
           try {
                ControleUsuario controleUsuario = new ControleUsuario();
                controleUsuario.trocarSenha(usuario, senhaAntiga, senhaNova);
                controleUsuario.atualizarUsuario(usuario);
                trocarTela(AnchorPaneSenhaAlterada, CaminhoArquivo.TELA_CONTA);
            } catch (SenhaInvalidaException ex) {

                System.getLogger(TelaContaAlterarSenhaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
}  
}
