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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Sessao;
import model.Usuario;
import util.CaminhoArquivo;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class TelaRenovarCartaoController implements Initializable {

    @FXML
    private Button buttonOk;
    @FXML
    private AnchorPane AnchorPaneCartaoRenovado;
    @FXML
    private Text fieldNovaDataV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
        if(usuario != null){
            ControleCartao controlecartao = new ControleCartao();
            controlecartao.renovacao(usuario);
            ControleUsuario controleUsuario = new ControleUsuario();
            controleUsuario.atualizarUsuario(usuario);
            this.fieldNovaDataV.setText(usuario.getDataCartaoV());
        }
    }    

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }
    
    @FXML
    private void handleButtonOk(ActionEvent event) {
        try {
            trocarTela(AnchorPaneCartaoRenovado, CaminhoArquivo.TELA_CARTAO); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
