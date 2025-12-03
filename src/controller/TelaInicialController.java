package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Estacao;
import model.Linha;
import util.CaminhoArquivo;

public class TelaInicialController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private TextField txtOrigem;
    @FXML
    private TextField txtDestino;
    @FXML
    private Button buttonCalcularTrajeto;
    @FXML
    private Button buttonConta;
    @FXML
    private Button buttonCartao;
    
    // Campos de Pesquisa
    @FXML
    private TextField pesquisarEstacao;
    @FXML
    private TextField pesquisarLinha;
    
    @FXML
    private Button verLinha;

    // Listas para o Autocomplete
    private ArrayList<String> todasEstacoes;
    private ArrayList<String> todasLinhas;
    @FXML
    private Button verEstacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Carrega os dados na memória (caso ainda não estejam)
        ControleEstacaoLinha.criarEstacoesLinhas();
        
        // 2. Prepara a lista de Estações
        todasEstacoes = ControleEstacaoLinha.getNomeEstacoes();
        
        // 3. Prepara a lista de Linhas (Extraindo manualmente da lista de objetos)
        todasLinhas = new ArrayList<>();
        if (ControleEstacaoLinha.listaLinhas != null) {
            for (Linha l : ControleEstacaoLinha.listaLinhas) {
                todasLinhas.add(l.getNome());
            }
        }
        
        // 4. Configura o Autocomplete nos campos
        configurarAutocomplete(txtOrigem, todasEstacoes);
        configurarAutocomplete(txtDestino, todasEstacoes);
        configurarAutocomplete(pesquisarEstacao, todasEstacoes);
        configurarAutocomplete(pesquisarLinha, todasLinhas);
    }

    // --- LÓGICA DE CALCULAR TRAJETO ---
    @FXML
    private void handleCalcularTrajeto(ActionEvent event) {
        String src = txtOrigem.getText();
        String trg = txtDestino.getText();

        // Validação
        if (src == null || src.trim().isEmpty() || trg == null || trg.trim().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha a origem e o destino.");
            return;
        }

        try {
            ControleTrajeto controleTrajeto = new ControleTrajeto(); 
            String caminhoCalculado = controleTrajeto.calcularTrajeto(src, trg);

            if (caminhoCalculado == null || caminhoCalculado.isEmpty()) {
                mostrarAlerta("Rota não encontrada", "Não foi possível encontrar um caminho.");
                return;
            }

            // Exibe resultado
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rota Encontrada");
            alert.setHeaderText("O trajeto calculado é:");
            alert.setContentText(caminhoCalculado);
            alert.showAndWait();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao calcular rota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- LÓGICA DE PESQUISAR ESTAÇÃO ---
    @FXML
    private void handleVerEstacao(ActionEvent event) {
        String nomeBusca = pesquisarEstacao.getText();

        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Digite o nome de uma estação para pesquisar.");
            return;
        }

        Estacao estacao = ControleEstacaoLinha.buscarEstacao(nomeBusca);

        if (estacao != null) {
            String info = "Estação: " + estacao.getNome() + "\n" +
                          "Código ID: " + estacao.getCodigo() + "\n\n" +
                          "--------------------------------\n" +
                          "Linhas que passam aqui:\n" + 
                          estacao.getNomeLinhas();
            
            mostrarAlerta("Detalhes da Estação", info);
        } else {
            mostrarAlerta("Não Encontrada", "A estação '" + nomeBusca + "' não consta no sistema.");
        }
    }

    // --- LÓGICA DE PESQUISAR LINHA ---
    @FXML
    private void handleVerLinha(ActionEvent event) {
        String nomeBusca = pesquisarLinha.getText();

        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Digite o nome ou código da linha (ex: D33).");
            return;
        }

        Linha linhaEncontrada = null;
        // Busca manual na lista
        for (Linha l : ControleEstacaoLinha.listaLinhas) {
            if (l.getNome().equalsIgnoreCase(nomeBusca)) {
                linhaEncontrada = l;
                break;
            }
        }

        if (linhaEncontrada != null) {
            StringBuilder listaEstacoes = new StringBuilder();
            for (Estacao e : linhaEncontrada.getEstacoes()) {
                listaEstacoes.append(" • ").append(e.getNome()).append("\n");
            }

            String info = "Linha: " + linhaEncontrada.getNome() + "\n\n" +
                          "--------------------------------\n" +
                          "Itinerário (Estações):\n" + 
                          listaEstacoes.toString();

            mostrarAlerta("Detalhes da Linha", info);
        } else {
            mostrarAlerta("Não Encontrada", "A linha '" + nomeBusca + "' não consta no sistema.");
        }
    }

    // --- NAVEGAÇÃO ENTRE TELAS ---
    @FXML
    private void handleButtonConta(ActionEvent event) {
        navegarParaTela(CaminhoArquivo.TELA_CONTA, event);
    }

    @FXML
    private void handleButtonCartao(ActionEvent event) {
        navegarParaTela(CaminhoArquivo.TELA_CARTAO, event);
    }

    private void navegarParaTela(String caminhoFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro de Navegação", "Não foi possível carregar a tela: " + caminhoFXML);
        }
    }

    // --- MÉTODOS AUXILIARES ---
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sistema de Transporte");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void configurarAutocomplete(TextField campo, List<String> dados){
        ContextMenu contextMenu = new ContextMenu();
        campo.textProperty().addListener((observable, oldValue, newValue)->{
            if(newValue == null || newValue.trim().isEmpty()){
                contextMenu.hide();
            } else {
                List<String> resultados = dados.stream()
                        .filter(s -> s.toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());
                if(!resultados.isEmpty()){
                    contextMenu.getItems().clear();
                    for (String resultado: resultados){
                        MenuItem item = new MenuItem(resultado);
                        item.setOnAction(e -> {
                            campo.setText(resultado);
                            campo.positionCaret(resultado.length());
                            contextMenu.hide();
                        });
                        contextMenu.getItems().add(item);
                    }
                    if(!contextMenu.isShowing()) contextMenu.show(campo, javafx.geometry.Side.BOTTOM, 0, 5);
                } else {
                    contextMenu.hide();
                }
            }
        });
        campo.focusedProperty().addListener((obs, oldVal, newVal)->{
            if(!newVal)contextMenu.hide();
        });
    }

}