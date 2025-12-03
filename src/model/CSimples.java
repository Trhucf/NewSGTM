package model;

public class CSimples extends Cartao implements ICComercial{
    private int mesesVencimento;
    private double saldo;

    public CSimples(){
        super();
        saldo = 0;
        mesesVencimento = 12;
        modalidade = "Simples";
    }

    @Override
    public String getModalidade(){
        return modalidade;
    }
    
    @Override
    public int getMesesVencimento(){
        return mesesVencimento;
    }

    public double getSaldo(){
        return saldo;
    }

    public double recarga(double valor){
        return saldo += valor;
    }
}