package controller;

public class CpfInvalidoException extends Exception {

    public CpfInvalidoException() {
        super();
    }
    public CpfInvalidoException(String mensagem) {
        super(mensagem);
    }
    public CpfInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}