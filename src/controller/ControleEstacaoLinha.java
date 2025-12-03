package controller;
import model.Estacao;
import data.RepositorioEstacaoLinha;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import model.Linha;

public class ControleEstacaoLinha {
    //Atributos: controleEstação possui apenas atributos e métodos
    //estáticos, uma lista de todas as estações, e uma lista de
    //lista de inteiros contendo a matriz de adjacencia das estações.
    public static ArrayList<Estacao> estacoes = new ArrayList<>();

    public static ArrayList<ArrayList<Integer>> matrizAdj = new ArrayList<>();

    //Criando um Mapa Temporário para busca rápida (Nome -> Objeto Estação)
    private static Map<String, Estacao> mapaRapido = new HashMap<>();

    public static ArrayList<Linha> listaLinhas = new ArrayList<>();

    //Setters e getters
    public static ArrayList<Estacao> getEstacoes(){
        return estacoes;
    }
    public static ArrayList<String> getNomesLinhas(){
        ArrayList<String> nomeLinhas = null;
        for(Linha l: listaLinhas){
            nomeLinhas.add(l.getNome());
        }
        return nomeLinhas;
    }
    public static ArrayList<String> getNomeEstacoes(){
        ArrayList<String> nomesEstacoes = new ArrayList<>();
        for(Estacao e: estacoes){
            nomesEstacoes.add(e.getNome());
        }
        return nomesEstacoes;
    }

    public static void addEstacao(Estacao e){
        estacoes.add(e);
    }

    //Método linhaComum encontra linhas em comum entre duas estações arbitrárias.
    public static ArrayList<Linha> linhaComum(Estacao a, Estacao b){
        ArrayList<Linha> linhasIguais = new ArrayList<>();
        for (int i = 0; i < a.getLinhas().size(); i++) {
            for(int j=0; j<b.getLinhas().size(); j++){
            if(a.getLinhas().get(i)==b.getLinhas().get(j)){
                linhasIguais.add(a.getLinhas().get(i));
                }
            }
        }
        return linhasIguais;
    }

    //Método buscarEstacao encontra uma linha a partir do nome dentro da lista
    //com todas as linhas.
    public static Estacao buscarEstacao(String nome){
        Estacao buscada = mapaRapido.get(nome);
        if(buscada!=null){
            return buscada;
        }else{
            return null;
        }
    }

    //Método buscarEstacao encontra uma linha a partir do código dentro da lista
    //com todas as linhas.
    public static Estacao buscarEstacao(int cod){
        for(int i=0; i<estacoes.size(); i++){
            if(estacoes.get(i).getCodigo()==cod)
                return estacoes.get(i);
        }
        return null;
    }
    
    public static Map<String, Estacao> getMapaRapido(){
        return mapaRapido;
    }
    public static void addMapaRapido(String s, Estacao e){
        mapaRapido.put(s,e);
    }

    //Método criar estações utiliza o construtor de estação
    //para criar as estações em data.RepositorioEstacoes e gerar a matriz de adjacencia.
    public static void criarEstacoesLinhas(){
        if (!estacoes.isEmpty()) { 
            return; 
        }
        //Pega a lista de conexões do Repositório de linhas
        String[][] linhas = RepositorioEstacaoLinha.getLinhas();

        //Este laço cria as estações/linhas e popula o mapa e as listas
        for(String[] linha: linhas){
            Linha novaLinha = new Linha(linha[0]);
            listaLinhas.add(novaLinha);
            for(int i=1; i<linha.length; i++){
                String nomeEstacao= linha[i];
                Estacao estacao = mapaRapido.get(nomeEstacao);
                
                if(estacao==null){
                    estacao = new Estacao(nomeEstacao);
                    addEstacao(estacao);
                    addMapaRapido(nomeEstacao, estacao);
                }
                novaLinha.addEstacao(estacao);
            }
        }

        //Este laço constroi as adjacencias
        for(String[] linha: linhas){
            for (int i = 1; i < linha.length-1; i++){
                String nomeOrigem = linha[i];
                String nomeDestino = linha[i+1];

                Estacao origem = mapaRapido.get(nomeOrigem);
                Estacao destino = mapaRapido.get(nomeDestino);

                if(origem!=null && destino!=null){
                    origem.setAdj(destino);
                }
            }
        }

        gerarMatrizAdjacencia();
    }

    //Método gerarMatrizAdjacencia gera a matriz de 
    //adjacencia a partir da lista atual de estações;
    private static void gerarMatrizAdjacencia(){
        //Construindo a matriz de adjacencia
        for(int i = 0; i<estacoes.size(); i++){
            matrizAdj.add(buscarEstacao(i).getAdj());
        }
    }

    //Método getMatrizAdjacencia retorna a matriz de adjacencia para
    //ser utilizada em Trajeto.
    public static ArrayList<ArrayList<Integer>> getMatrizAdjacencia(){
        return matrizAdj;
    }
}