package data;    

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Usuario;

    public class RepositorioArquivoUsuario{
        private final String ARQUIVO = "usuario.ser";

        private static int NCartao;

        public RepositorioArquivoUsuario(){
            calcularNCartao();
        }
        
        private void calcularNCartao(){
         ArrayList<Usuario> usuarios = getAllUsuarios();
         int maxNumero= 0;

         if (usuarios != null && !usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                if (usuario.getCartao() != null) {
                    int nAtual = usuario.getCartao().getNumero();
                    if (nAtual > maxNumero) {
                        maxNumero = nAtual;
                    }
                }
            }
        }
          this.NCartao = maxNumero + 1;
        }

        public int gerarNumeroCartao() {
            int numero = this.NCartao;
            this.NCartao++;
            return numero;
        }

        public ArrayList<Usuario> getAllUsuarios() {
            ArrayList<Usuario> usuarios = new ArrayList<>();
            File arquivoUsuarios = new File(ARQUIVO);

            //Se o arquivo ainda nao existir, cria um novo e retorna uma lista vazia
            if (!arquivoUsuarios.exists()) {
                try {

                    arquivoUsuarios.createNewFile();

                } catch (IOException e) {
                    System.err.println("Erro ao criar arquivo de dados das disciplinas!" + e.getMessage());
                    e.printStackTrace();
                }
                return usuarios;
            }

            //Se o arquivo existir, recurpera e retorna as disciplinas cadastradas
            try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(ARQUIVO))) {

                usuarios = (ArrayList<Usuario>)leitorDeObjetos.readObject();

            } catch (Exception e) {
                System.err.println("Erro no metodo getAllDisciplinas: " + e.getMessage());
                e.printStackTrace();
            }
            return usuarios;
        }

        //Remove o usuario do Array e passa esse Array para ser regravado peço método atualizarArquivoUsuario.
        public void deletarUsuario(Usuario user){ //possíveis erros: caso não encontre usuário
            ArrayList<Usuario> usuarios = getAllUsuarios();
            for (int i = 0; i < usuarios.size(); i++) {
                if(user.getCpf().equals(usuarios.get(i).getCpf())){
                    usuarios.remove(usuarios.get(i));
                }
            }
            atualizarArquivoUsuario(usuarios);
        }


        //Grava o Array passado com alteracoes feitas.
        public void atualizarArquivoUsuario(ArrayList<Usuario> usuarios){
            try (ObjectOutputStream gravarObj = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {

                gravarObj.writeObject(usuarios);

            } catch (Exception e) {
                System.err.println("Erro no método atualizarArquivoDisciplina: " + e.getMessage());
                e.printStackTrace();
            }
        }

        //Adiciona o usuário ao array e passa para outro método para que o array seja gravado.
        public void criarUsuario(Usuario user){
            ArrayList<Usuario> disciplinasCadastradas = getAllUsuarios();
            disciplinasCadastradas.add(user);

            atualizarArquivoUsuario(disciplinasCadastradas);
        }

        public void atualizarUsuario(Usuario user){
            deletarUsuario(user);
            criarUsuario(user);
        }

        public Usuario pesquisarUsuario(String cpf){
            ArrayList <Usuario> usuarios = getAllUsuarios();
            Usuario user = null;
            for (Usuario usuario : usuarios){
                if (cpf.equals(usuario.getCpf())){
                    user = usuario;
                    break;
                }            
            }
            return user;
        }

        //Ainda não foi testado.
        public void bloquearCartao(Usuario user, String senha){ 
            if(user.getSenha().equals(senha)){
            ArrayList <Usuario> usuarios;
            boolean achou = false;
            usuarios = (ArrayList<Usuario>)getAllUsuarios();
            for (int i=0; i < usuarios.size(); i++){
                if (user.getCpf().equals(usuarios.get(i).getCpf())){
                    user.bloquearCartao();
                    atualizarUsuario(user);
                    achou = true;
                    break;
                }
            }
            if (!achou){
                throw new RuntimeException("Usuário não encontrado");
            }
            }
        }
    }
