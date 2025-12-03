package model;

import controller.ControleEstacaoLinha;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Trajeto {
    // Breadth First Search (Adaptada para retornar um caminho)
    //O método inicia com argumentos source para ponto inicial e target para ponto final
    public static ArrayList<Estacao> busca(int source, int target){
        //Encontrando a matriz de adjacencia
        ArrayList<ArrayList<Integer>> adj = ControleEstacaoLinha.getMatrizAdjacencia();
        
        //Iniciando o caminho que será o atributo de retorno
        ArrayList<Integer> path = new ArrayList<>();

        //Atributo v é o número de vértices do grafo
        int v = adj.size();

        //Atributo visited é a array de vértices visitados
        boolean visited[] = new boolean[v];

        //Aqui é iniciado um array parent, ele serve para
        //guardar todos os vértices "pai", e encontrar
        //o caminho. 
        int parent[] = new int[v];
        //Inicio parent com todos os valores -1, pois não
        //é um valor válido de vértice.
        for(int i = 0; i<v; i++){
            parent[i]=-1;
        }

        //Queue q é a fila, para controlar o algoritmo BFS
        Queue<Integer> q = new LinkedList<>();

        //Inicio source (Ponto inicial) como true e o adiciono
        //à fila
        visited[source]=true;
        q.add(source);

        //Loop do algoritmo (enquanto a fila não estiver vazia)
        while(!q.isEmpty()){
            //curr(vértice atual) é retirado do inicio da fila
            int curr = q.poll();
            //condição pra parar quando o ponto final é achado
            if(curr == target){
                    break;
                }
            
            //Laço for para passar em todos os vizinhos de curr
            for(int x : adj.get(curr)){
                //Se ainda não foi visitado
                if (!visited[x]) {
                    visited[x] = true;
                    q.add(x);
                    //Essa linha é a que guarda o pai do
                    //vértice encontrado
                    parent[x]=curr;
                }
                
            }
        }
        //Caso não encontre um caminho
        if (parent[target] == -1) {
            return null;
        }
        //Construindo o caminho, primeiro iniciamos
        //Um atributo currentNode
        int currentNode = target;
        //Este loop serve pra, a partir do último
        //vértice(no caso, o target), encontrar seus
        //vértices pais até achar o primeiro com pai -1
        //(vértice inicial com valor inválido pois não tem pai).
        while(currentNode!=-1){
            //Guardando os vértices em path para criar o caminho
            path.add(currentNode);
            currentNode=parent[currentNode];
        }

        //Utilizando Collections para reverter o caminho
        //que foi encontrado pois está ao contrário.
        java.util.Collections.reverse(path);

        //A função retorna uma ArrayList com os objetos das
        //Estações do caminho
        ArrayList<Estacao> caminho = new ArrayList<>();
        for(int x: path){
            caminho.add(ControleEstacaoLinha.estacoes.get(x));
        }
        return caminho;
    }

    //O seguinte método trajetoString() é utlizado para, a partir de um caminho ArrayList<Estacoes>,
    //montar uma série de instruções em texto.
    public static String trajetoString(ArrayList<Estacao> caminho){

        //Utilizando o método linhaComum, pego a primeira linha em comum entre as duas primeiras
        //estações e inicio linhaAtual.
        Linha linhaAtual = ControleEstacaoLinha.linhaComum(caminho.get(0), caminho.get(1)).get(0);

        //Inicio um StringBuilder para a String de instruções.
        StringBuilder trajeto = new StringBuilder();

        //Adiciono à string a primeira estação + linha a ser pega.
        trajeto.append("A partir da estação ");
        trajeto.append(caminho.get(0).getNome());
        trajeto.append(" pegue a linha ");

        //Defino uma condicional: caso exista uma linha em comum entre a primeira e última estação, o usuário
        //deve simplesmente seguir o caminho da linha
        ArrayList<Linha> conexaoDireta = ControleEstacaoLinha.linhaComum(caminho.get(0), caminho.get(caminho.size()-1));
        if(!conexaoDireta.isEmpty()){
            linhaAtual = conexaoDireta.get(0);
            trajeto.append(linhaAtual.getNome());
            trajeto.append(" até o seu destino, estação ");
            trajeto.append(caminho.get(caminho.size()-1).getNome());
            return trajeto.toString();    
        }
        
        //Este bloco de código serve para tratar os casos em que existem linhas x e y em um trajeto z
        //(no qual existe estação central entre o inicio e o fim), tais que x é comum da estção
        //inicial e da estação central, e y é comum da estação central a estação final.
        Linha ateCentral = null;
        int trajetoAntes = trajeto.length();
        for(Estacao e: caminho){
            if(e.getNome().equalsIgnoreCase("Estação Herzem Gusmão")){
                ateCentral=ControleEstacaoLinha.linhaComum(e, caminho.get(0)).get(0);
                if(ateCentral!=null){
                    trajeto.append(ateCentral.getNome());
                    break;
                }
            }
            break;
        }
        if(ateCentral!=null){
            Linha depoisCentral = null;
            depoisCentral=ControleEstacaoLinha.linhaComum(ControleEstacaoLinha.buscarEstacao("Estação Herzem Gusmão"), caminho.get(caminho.size()-1)).get(0);
            if(depoisCentral!=null){
                trajeto.append(" e faça baldeação para a linha ").append(depoisCentral.getNome()).append(" até seu destino, ").append(caminho.get(caminho.size()-1).getNome());
                return trajeto.toString();
            }
        }else{
            trajeto.setLength(trajetoAntes);
        }

        trajeto.append(linhaAtual.getNome());

        //Agora inicio um laço que passa por todas as estações do caminho.
        for (int i = 1; i < caminho.size()-1; i++) {
            //A cada iteração, construo uma lista de linhas em comum entre duas estações conectadas (i e i+1);
            ArrayList<Linha> proximasLinhas = ControleEstacaoLinha.linhaComum(caminho.get(i), caminho.get(i+1));

            //Condicional: se a próxima estação contem a linha atual, pula e continua o loop, caso contrário
            //paramos o trajeto e fazemos baldeação para a próxima linha em comum.
            if(!proximasLinhas.contains(linhaAtual)){
                trajeto.append(" ate a estacao ");
                trajeto.append(caminho.get(i).getNome());
                trajeto.append("\n");
                Linha novaLinha = proximasLinhas.get(0);
                linhaAtual=novaLinha;
                trajeto.append("E faça baldeação para a linha ");
                trajeto.append(linhaAtual.getNome());
                trajeto.append(" ");
            }
        }

        //Ao final do loop, apenas fechamos trajeto.
        trajeto.append("até o seu destino, estação ");
        trajeto.append(caminho.get(caminho.size()-1).getNome());

        return trajeto.toString();
    }
}