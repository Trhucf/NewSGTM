package model;

public class CEstudante extends Cartao implements ICComercial{
    private int mesesVencimento;
    private double saldo;

    public CEstudante(int numerocartao){
        super(numerocartao);
        saldo = 0;
        mesesVencimento = 6;
        modalidade = "Estudante";
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