package controller;
import model.Usuario;
import data.RepositorioArquivoUsuario;

public class ControleUsuario {
    private RepositorioArquivoUsuario repositorioUsuario = new RepositorioArquivoUsuario();


    //retorna usuário para que o cartao possa ser criado vinculado au usuário
    public Usuario criarUsuario(String nome, String cpf, String senha){
        Usuario us = new Usuario(nome, cpf, senha);
        return us;
    }

    /**
     *
     
@param cpf
@param senha
@throws SenhaInvalidaException*/
public boolean confirmarSenha(String cpf, String senha) throws SenhaInvalidaException{
    Usuario user = repositorioUsuario.pesquisarUsuario(cpf);
    if(!(user.getSenha().equals(senha))){
        throw new SenhaInvalidaException();}
    return true;}

    public boolean confirmarCpf(String cpf) throws CpfInvalidoException{ 
        Usuario user = repositorioUsuario.pesquisarUsuario(cpf);
        if(user == null){
            throw new CpfInvalidoException();
        }
        return true;
    }
    
     
    public void trocarSenha(Usuario user, String senhaAntiga, String senhaNova) throws SenhaInvalidaException{
        if(user.getSenha().equals(senhaAntiga)){
            user.setSenha(senhaNova);
        } else{
                throw new SenhaInvalidaException();
        }
    }
    

    //Verifica se o usuário foi cadastrado e se sua senha está correta.
    public Usuario login(String cpf){
        Usuario user = repositorioUsuario.pesquisarUsuario(cpf);
        return user;
    }

    //grava o usuário em um arquivo depois de ter o cartao adicionado
    public void arquivarUsuario(Usuario us){
        repositorioUsuario.criarUsuario(us);
    }

    //Após alguma modificação em seus dados o usuário é atualizado
    public void atualizarUsuario(Usuario user){
        repositorioUsuario.atualizarUsuario(user);
    }
}
