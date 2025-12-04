/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea; // Importante: TextArea, não TextField
import javafx.stage.Stage;

/**
 * Controller da janela de exibição da rota
 */
public class TelaInicialRotaController implements Initializable {

    @FXML
    private TextArea textAreaTrajeto; // Mudado de TextField para TextArea
    @FXML
    private Button buttonOk;

    private Stage dialogStage; // Referência à janela (palco)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Deixar o TextArea não editável para o usuário apenas ler
        if (textAreaTrajeto != null) {
            textAreaTrajeto.setEditable(false);
            // Garante que o texto quebre a linha automaticamente se for muito longo
            textAreaTrajeto.setWrapText(true); 
        }
    }    
    
    /**
     * Define o palco (Stage) deste controller.
     * Necessário para fechar a janela depois.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Define o texto da rota dentro do TextArea.
     * É aqui que recebemos a String da tela principal.
     */
    public void setRota(String rota) {
        this.textAreaTrajeto.setText(rota);
        // Opcional: Posiciona a barra de rolagem no topo caso o texto seja grande
        this.textAreaTrajeto.positionCaret(0); 
    }

    /**
     * Ação do botão OK para fechar a janela.
     * Lembre-se de vincular este método no SceneBuilder (On Action).
     */
    @FXML
    private void handleBtnOk(ActionEvent event) {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}