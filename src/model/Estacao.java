package model;
import java.util.ArrayList;

public class Estacao {
    //Atributos: Cada estação tem um nome, código, contador para controlar
    //os códigos, uma lista de estações adjacentes e uma lista de
    //linhas que passam nela.
    private final String nome;
    private final int codigo;
    private static int cont = 0;
    private final ArrayList<Estacao> adj;
    private final ArrayList<Linha> linhas;

    //Setters e getters
    public String getNome(){
        return nome;
    }

    public int getCodigo(){
        return codigo;
    }

    public ArrayList<Linha> getLinhas(){
        return linhas;
    }

    public String getNomeLinhas(){
        String nomeL ="";
        nomeL+=linhas.get(0).getNome();
        for(int i=1; i<linhas.size(); i++){
            nomeL += ", "+linhas.get(i).getNome();
        }
        return nomeL;
    }

    public void setAdj(Estacao a){
        if(!this.adj.contains(a)){
            this.adj.add(a);
            a.setAdj(this);
        }
    }

    public ArrayList<Integer> getAdj(){
        ArrayList<Integer> codAdj = new ArrayList<>();
        for(int i = 0; i<adj.size(); i++){
            codAdj.add(adj.get(i).getCodigo());
        }
        return codAdj;
    }

    //Método para adcionar linhas. Note que, no ato de adicionar
    //linha à estação, a mesma estação é adicionada à linha por
    //addEstação(), caso já não a possua;
    public void addLinha(Linha l){
        linhas.add(l);
        if(!l.getEstacoes().contains(this)){
        l.addEstacao(this);
        }
    }

    //Construtor
    public Estacao(String nome){
        this.nome=nome;
        codigo = cont;
        cont++;
        adj = new ArrayList<>();
        linhas = new ArrayList<>();
    }
}