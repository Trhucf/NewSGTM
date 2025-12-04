package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaEntradaController implements Initializable {
    
    private PauseTransition pause;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("=== INICIALIZANDO TELA ENTRADA ===");
        
        pause = new PauseTransition(Duration.seconds(10));
        
        pause.setOnFinished(event -> {
            System.out.println("Timer completado! Mudando para tela de login...");
            
            // Executa na thread da interface gráfica
            Platform.runLater(() -> {
                try {
                    // 1. Carrega o FXML da próxima tela
                    Parent root = FXMLLoader.load(getClass().getResource("/view/telaConta.fxml"));
                    
                    // 2. Obtém o stage atual de forma segura
                    Stage stageAtual = obterStageAtual();
                    
                    if (stageAtual != null) {
                        // 3. Cria nova cena e define no stage
                        Scene scene = new Scene(root);
                        stageAtual.setScene(scene);
                        stageAtual.show();
                        
                        System.out.println("Tela alterada com sucesso!");
                    } else {
                        System.err.println("Não foi possível obter o stage atual!");
                    }
                    
                } catch (IOException e) {
                    System.err.println("Erro ao carregar FXML: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        });
        
        System.out.println("Iniciando timer de 10 segundos...");
        pause.play();
    }
    
    /**
     * Método seguro para obter o stage atual
     */
    private Stage obterStageAtual() {
        try {
            // Pega a primeira janela visível
            return (Stage) javafx.stage.Window.getWindows()
                    .stream()
                    .filter(javafx.stage.Window::isShowing)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Método para interromper o timer (se necessário)
     */
    public void pararTimer() {
        if (pause != null) {
            pause.stop();
        }
    }
}