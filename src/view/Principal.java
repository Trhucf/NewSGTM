/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package view;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.CaminhoArquivo;

/**
 *
 * @author Usuario
 */
public class Principal extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
             URL url = getClass().getResource(CaminhoArquivo.TELA_LOGIN);
             System.out.println("URL carregada = " + url);
             Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);

           
            stage.setTitle("SGTM");
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }

    
}
