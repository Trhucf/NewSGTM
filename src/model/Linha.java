package model;
import java.util.ArrayList;

public class Linha {
    //Atributos: Cada linha tem um nome, e uma lista de estações associadas
    private final String nome;
    private final ArrayList<Estacao> estacoes;

    //Setters e getters
    public String getNome(){
        return nome;
    }

    public ArrayList<Estacao> getEstacoes(){
        return estacoes;
    }

    //Método para adcionar estações. Note que, no ato de adicionar
    //estação à linha, a mesma linha é adicionada à estação por
    //addLinha(), caso já não a possua;
    public void addEstacao(Estacao e){
        estacoes.add(e);
        if(!e.getLinhas().contains(this)){
        e.addLinha(this);
        }
    }

    //Construtor
    public Linha(String nome){
        this.nome=nome;
        this.estacoes = new ArrayList<>();
    }
}
