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
 * @author Amanda
 */
public class TelaCartãoController implements Initializable {

    @FXML
    private Text textNumeroCartao;
    @FXML
    private Text textModalildade;
    @FXML
    private Text textDataCriacao;
    @FXML
    private Text textDataVencimento;
    @FXML
    private AnchorPane AnchorPaneCartao;
    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonConta;
    @FXML
    private Button buttonRenovarCartao;
    @FXML
    private Button buttonBloquearCartao;
    @FXML
    private Button buttonFazerRecarga;
    @FXML
    private Text textCpf;
    @FXML
    private Text textFieldSaldo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = Sessao.getInstance().getUsuarioLogado();
        
        if (usuario != null) {
            this.textCpf.setText(usuario.getCpf());
            
            //verifica se o cartão n foi bloqueado
            if(usuario.getCartao() != null){
                this.textModalildade.setText(usuario.getModalidadeCartao());
                this.textNumeroCartao.setText(usuario.getNumeroCartao());
                this.textDataCriacao.setText(usuario.getDataCartaoC());
                this.textDataVencimento.setText(usuario.getDataCartaoV());
                this.textFieldSaldo.setText(usuario.getSaldoC());

            }else{
                this.textModalildade.setText("CARTÃO BLOQUEADO");
                this.textNumeroCartao.setText("CARTÃO BLOQUEADO");
                this.textDataCriacao.setText("CARTÃO BLOQUEADO");
                this.textDataVencimento.setText("CARTÃO BLOQUEADO");
                this.textFieldSaldo.setText("CARTÃO BLOQUEADO");
            }
        }
    }    
    
    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
    AnchorPane novaTela = FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
    telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    private void handleButtonRenovarCartao(ActionEvent event) {
        try {
            trocarTela(AnchorPaneCartao, "/view/telaRenovarCartão.fxml"); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }

    @FXML
    private void handleButtonBloquearCartao(ActionEvent event) {

         try {
            trocarTela(AnchorPaneCartao, "/view/telaBloquearCartão.fxml"); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
        

    }

    @FXML
    private void handleButtonFazerRecarga(ActionEvent event) {

         try {
            trocarTela(AnchorPaneCartao, "/view/telaFazerRecarga.fxml"); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    @FXML
    private void handleButtonHome(ActionEvent event) {
        try {
            trocarTela(AnchorPaneCartao, CaminhoArquivo.TELA_INICIAL); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleButtonConta(ActionEvent event) {
        try {
            trocarTela(AnchorPaneCartao, "/view/telaConta.fxml"); 
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
   
