/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Sessao;
import util.CaminhoArquivo;

/**
 * FXML Controller class
 *
 * @author Amanda
 */
public class TelaContaController implements Initializable {

    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonConta;
    @FXML
    private Button buttonCartao;
    @FXML
    private AnchorPane AnchorPaneConta;
    @FXML
    private Button buttonAlterarSenha;
    @FXML
    private Text textNome;
    @FXML
    private Text textCpf;
    @FXML
    private Text textNumeroCartao;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
        
        if (usuario != null) {
            this.textNome.setText(usuario.getNome());
            this.textCpf.setText(usuario.getCpf());
            // verifica se o cartão n foi block
            if(usuario.getCartao() != null){
                this.textNumeroCartao.setText(usuario.getNumeroCartao());
            }else{
                this.textNumeroCartao.setText("CARTÃO BLOQUEADO");
            }
        }
    }    
        

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }
    
   
    
    @FXML
    private void handleButtonHome(ActionEvent event) {
        try {
            trocarTela(AnchorPaneConta, CaminhoArquivo.TELA_INICIAL); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAlterarSenha(ActionEvent event) {
        try {
            trocarTela(AnchorPaneConta, CaminhoArquivo.TELA_CONTA_SENHA); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    

    @FXML
    private void handleButtonCartao(ActionEvent event) {
        try {
            trocarTela(AnchorPaneConta, CaminhoArquivo.TELA_CARTAO);
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
}
