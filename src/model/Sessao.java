/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

// Sessao.java
public class Sessao {
    private static Sessao instance;
    private Usuario usuarioLogado;
    
    private Sessao() {
        // Construtor privado
    }
    
    public static Sessao getInstance() {
        if (instance == null) {
            instance = new Sessao();
        }
        return instance;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }
    
    public void logout() {
        this.usuarioLogado = null;
    }
    
    public boolean isLogado() {
        return usuarioLogado != null;
    }
}