package model;

public class CIdoso extends Cartao {
    private int mesesVencimento;

    public CIdoso(int numerocartao){
        super(numerocartao);
        mesesVencimento = 12;
        modalidade = "Idoso";
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