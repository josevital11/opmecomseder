/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Usuario
 */
public class c_usuarios {
    private String idUsuario,
            Senha,
            Usuario,
            Nome,
            Email,
            Perfil,
            Tipo;
    private int usuario_id;
    
    public c_usuarios() {
    
        this.idUsuario = "";
        this.Senha = "";
        this.Usuario = "";
        this.Nome = "";
        this.Email = "";
        this.Perfil = "";
        this.Tipo = "";
        this.usuario_id = 0;

    }
    
    public c_usuarios(String idUsuario,
            String Senha,
            String Usuario,
            String Nome,
            String Email,
            String Perfil,
            String Tipo)
    {
        this.idUsuario = idUsuario;
        this.Senha = Senha;
        this.Usuario = Usuario;
        this.Nome = Nome;
        this.Email = Email;
        this.Perfil = Perfil;
        this.Tipo = Tipo;
        
        this.usuario_id = usuario_id; 
    }
    
    public boolean isNovo() {
        if (this.idUsuario == null) {
                return true;
        }else if(this.idUsuario != null && !this.idUsuario.isEmpty()) {
                return false;
        }
        return false;
    }
    
    public void setIdUsuario(String idUsuario) {this.idUsuario = idUsuario;}
    public String getIdUsuario() {return idUsuario;}
    
    public void setSenha(String Senha) {this.Senha = Senha;}
    public String getSenha() {return Senha;}
    
    public void setUsuario(String Usuario) {this.Usuario = Usuario;}
    public String getUsuario() {return Usuario;}
    
    public void setNome(String Nome) {this.Nome = Nome;}
    public String getNome() {return Nome;}
    
    public void setEmail(String Email) {this.Email = Email;}
    public String getEmail() {return Email;}
    
    public void setPerfil(String Perfil) {this.Perfil = Perfil;}
    public String getPerfil() {return Perfil;}
    
    public void setTipo(String Tipo) {this.Tipo = Tipo;}
    public String getTipo() {return Tipo;}
    
    public void setUsuario_id(int Usuario_id) {this.usuario_id = usuario_id;}
    public int getUsuario_id() {return usuario_id;}

}

