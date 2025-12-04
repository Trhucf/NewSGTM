/**
 * 
 * author (Ruth, Luís, Amanda e Nicolle)
 * última versão 19/11/2025
 * 
 */
package model;
 
import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L; //Para que quando a classe seja altera o UID permaneça o mesmo e os objetos possam ser desserializados
    private String cpf;
    private String nome;
    private String senha; 
    private ArrayList<Linha> linhasFav;
    private ArrayList<Estacao> estacoesFav;
    private Cartao cartao;

    public Usuario(String nome, String cpf, String senha){
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome(){ //Para exibir mensagens personalizadas
        return nome; 
    }

    public String getSenha(){
        return senha;
    }

    public Cartao getCartao(){
        return cartao;
    }

    public String getNumeroCartao(){
        return "" + cartao.getNumero();
    }
    
    public String getCpf(){
        return cpf;
    }

    public String getDataCartaoC(){
        return "" + cartao.getDataCriacao();
    }
    
    public String getDataCartaoV(){
        return "" + cartao.getDataVencimento();
    }
    
    public String getSaldoC(){
        return "" + cartao.getSaldo();
    }
   
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    //O usuário passa a possuir um cartão
    public void adicionarCartao(Cartao cartao){
        this.cartao = cartao;
    }

    //caso perca o cartao, ou seja usado indevidamente. Adicionar pedido de confirmação duplo
    public void bloquearCartao(){
        this.cartao = null;
    }

    public String getModalidadeCartao() {
        return cartao.getModalidade();
    }

}