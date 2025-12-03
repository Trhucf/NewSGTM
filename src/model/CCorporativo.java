package model;
public class CCorporativo extends Cartao implements ICComercial{
    private double saldo;
    private int mesesVencimento;

    public CCorporativo(){
        super();
        saldo = 0;
        mesesVencimento = 12;
        modalidade = "Corporativo";
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