package model;

public class CEspecial extends Cartao {
    private int mesesVencimento;

    public CEspecial(int numerocartao){
        super(numerocartao);
        mesesVencimento = 12;
        modalidade = "Especial";

    }
    
    @Override
    public String getModalidade(){
        return modalidade;
    }

    @Override
    public int getMesesVencimento(){
        return mesesVencimento;
    }   
}