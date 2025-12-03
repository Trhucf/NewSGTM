package controller;

public class SenhaInvalidaException extends Exception{

    @Override
    public String getMessage(){
        return "Cpf inválido!";
    }
}