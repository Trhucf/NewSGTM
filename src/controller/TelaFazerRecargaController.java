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
import model.Sessao;
import model.Usuario;
import util.CaminhoArquivo;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class TelaFazerRecargaController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneRecarga;
    @FXML
    private Button buttonOk;
    @FXML
    private TextField textFieldValor;

    public double valor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
        if(usuario != null){
             valor = Double.parseDouble(textFieldValor.getText());

            ControleCartao controlecartao = new ControleCartao();
            ControleUsuario controleUsuario = new ControleUsuario();
            controlecartao.recarga(valor, usuario);
            
            controleUsuario.atualizarUsuario(usuario);
            
        }
    }    
    
    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }


    @FXML
    private void handleButtonOk(ActionEvent event) {
        try {
            trocarTela(AnchorPaneRecarga, CaminhoArquivo.TELA_CARTAO); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
