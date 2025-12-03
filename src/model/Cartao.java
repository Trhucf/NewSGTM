package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cartao implements Serializable{
    private LocalDate dataCriacao = null;
    private int mesesVencimento; //Período específico de cada cartão pelo qual é valido
    private LocalDate dataVencimento;
    private int numero;
    protected String modalidade;
    private double saldo;

    //Ao ser criado o usuário o cartao é ativado e portanto sua data de criação passa a ser a mesma da do cadastro do usuário
    public Cartao(int numerocartao){
        dataCriacao = LocalDate.now();
        this.numero=numerocartao;
        saldo = 0.0;
    }   
    
    public int getMesesVencimento(){
        return mesesVencimento;
    }
    
    public String getModalidade(){
        return modalidade;
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public int getNumero(){
        return numero;
    }
    public LocalDate getDataCriacao(){
        return dataCriacao;
    }
    
    public LocalDate getDataVencimento(){
        return dataVencimento;
    }

    public LocalDate calcularDtVencimento(int mesesVencimento){
        dataVencimento = this.getDataCriacao().plusMonths(mesesVencimento);
        return dataVencimento;
    }
    
    //Pega a data atual e subtrai da data de vencimento, há ações para caso a diferença seja maior que 30 dias, maior e menor que 0 dias.
    public String renovacao(LocalDate dataVencimento){
        LocalDate dataAtual = LocalDate.now();
        long diasDiferenca = ChronoUnit.DAYS.between(dataAtual, dataVencimento);
        if(diasDiferenca > 30){
            return "O seu cartão está renovado, ele é válido até " + dataVencimento; 
        } else if(diasDiferenca > 0){ //Apto para renovaçao, é entao adicionada à data de renovaca mais um periodo de validade.
            dataVencimento = dataVencimento.plusMonths(getMesesVencimento());
            return "O seu cartão foi renovado, ele é válido até " + dataVencimento;   
        } //Esse caso não foi verificado se funciona, caso diasDiferenca seja < 0.
        return "O período de renovação para seu cartão está expirado, por favor dirija-se à uma agência para desbloquea-lo";
    }
}