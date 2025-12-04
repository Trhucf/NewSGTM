package controller;

import java.util.ArrayList;
import model.Estacao;
import model.Trajeto;

public class ControleTrajeto {
    public static String calcularTrajeto(String src, String trg){
        Estacao source = ControleEstacaoLinha.buscarEstacao(src);
        Estacao target = ControleEstacaoLinha.buscarEstacao(trg);
        
        try{
            ArrayList<Estacao> caminho = Trajeto.busca(source.getCodigo(), target.getCodigo());

            String instrucao = Trajeto.trajetoString(caminho);
            return instrucao;
        }catch (NullPointerException n){
            String erro = n.getMessage();
            return erro;
        }
    };
}
