package controller;

import java.time.LocalDate;
import model.Usuario;
import model.Cartao;
import model.CEstudante;
import model.CIdoso;
import model.CEspecial;
import model.CCorporativo;
import model.CSimples;
import model.ICComercial ;
import data.RepositorioArquivoUsuario;

public class ControleCartao {

    private RepositorioArquivoUsuario Usuario = new RepositorioArquivoUsuario();

    public void criarCartao(Usuario us, int modalidade){
        int numero = Usuario.gerarNumeroCartao();
        Cartao cr = null; //Para reduzir o código foi criada a váriavel do tipo Cartao e a instacia de suas filhas é atribuida a ela, assim o polimorfirsmo é aproveitado.
        switch(modalidade){
            case 1:
            {
                cr = new CEstudante(numero);
                break;
            }
            case 2:
            {
                cr = new CIdoso(numero);
                break;
            }
            case 3:
            {
                cr = new CEspecial(numero);
                break;
            }
            case 4:
            {
                cr = new CCorporativo(numero);
                break;
            }
            case 5:
            {
                cr = new CSimples(numero);
                break;
            }
        }
        us.adicionarCartao(cr); //associando o cartão ao usuário
    }

    //Para consulta de saldo ou recarga é verificado se a instância do cartão implementa ICComercial e faz o cast para a mesma pa que os métodos sejam possíveis.
    public ICComercial conferirTipo(Usuario user){
        Cartao cartao = user.getCartao();
        if(cartao instanceof ICComercial){
            ICComercial cr = (ICComercial)cartao;
            return cr;
        }
        return null;
    }

    public double consultarSaldo(Usuario user){ 
        ICComercial cartao = conferirTipo(user);
        double saldo = cartao.getSaldo();
        return saldo;
    }

    //Após convertido para ICComercial, para fazer a recarga o valor que o usuário quer recarregar é adicionado ao saldo do cartão.
    public double recarga(double valor, Usuario user){
        ICComercial cartao = conferirTipo(user);
        double saldo = cartao.recarga(valor);
        return saldo;
    }

    //É pego o cartão do usuário e calculado a partir de sua data de criação e periodo de vencimento, a data de renovacao/vencimento.
    public LocalDate consultarDataRenovacao(Usuario user){
        Cartao cartao = user.getCartao();
        int mesesVencimento = cartao.getMesesVencimento();
        return cartao.calcularDtVencimento(mesesVencimento);
    }

    //É pego o cartão do usuário e caluldado a data de renovação/vencimento do cartao, em seguida verifica se é possivel fazer a renovaçao.
    public String renovacao(Usuario user){
        Cartao cartao = user.getCartao();
        LocalDate dataVencimento = consultarDataRenovacao(user);
        String estado = cartao.renovacao(dataVencimento);
        return estado;
    }
}
