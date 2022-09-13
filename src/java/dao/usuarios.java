/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.c_usuarios;

/**
 *
 * @author Usuario
 */
public class usuarios {
    private Connection conBanco;
    private PreparedStatement psComando;
    private ResultSet rsRegistros;
    
    public void configurarConexao(Connection conBanco) {
        this.conBanco = conBanco;
    }

    public boolean inserirRegistro(c_usuarios usuario) {
        String strComandoSQL;
        
        try {
            strComandoSQL = "INSERT INTO usuarios (Senha, Usuario, Nome, Email, Perfil, Tipo, usuario_id" +
                        "VALUES('"+usuario.getSenha()+"',"+
                        "'"+usuario.getUsuario()+"',"+
                        "'"+usuario.getNome()+"',"+
                        "'"+usuario.getEmail()+"',"+
                        "'"+usuario.getPerfil()+"',"+
                        "'"+usuario.getTipo()+"',"+
                        "'"+usuario.getUsuario_id()+"')";
            psComando = conBanco.prepareStatement(strComandoSQL);
            psComando.executeUpdate();
            
            return true;
            
        } catch (Exception erro) {
            System.out.println("Erro: "+erro);
            erro.printStackTrace();
            return false;
        }
        
    }
    
    public int localizarRegistro(String strUsuario) {
        int intCodigoUsuario = 0;
        String strComandoSQL;

        try {
            strComandoSQL = "SELECT * FROM usuarios WHERE Usuario = '"+strUsuario+"'";
            psComando = conBanco.prepareStatement(strComandoSQL);
            rsRegistros = psComando.executeQuery();
            rsRegistros.next();

            //System.out.println("Usuario: "+rsRegistros.getString(strUsuario));
            
            intCodigoUsuario = rsRegistros.getInt("idUsuario");

        } catch (Exception erro) {
            erro.printStackTrace();
        }

        return intCodigoUsuario;
    }
    
    public ResultSet lerRegistro(int intCodigoUsuario) {
        String strComandoSQL;
        
        try {
            strComandoSQL = "SELECT * FROM usuarios WHERE idUsuario = '"+intCodigoUsuario+"'";
            psComando = conBanco.prepareStatement(strComandoSQL);
            rsRegistros = psComando.executeQuery();
            rsRegistros.next();

            return rsRegistros;

        } catch (Exception erro) {
            erro.printStackTrace();
            return null;
        }
    }
    
    public boolean alterarRegistro(c_usuarios usuario) {
        String strComandoSQL;
        
        try {
            strComandoSQL = "UPDATE usuarios SET idUasuario = '" +usuario.getIdUsuario()+ "'," +
                        "Senha = '"+usuario.getSenha()+"',"+
                        "Usuario = '"+usuario.getUsuario()+"',"+
                        "Nome = '"+usuario.getNome()+"',"+
                        "Email = '"+usuario.getEmail()+"',"+
                        "Pefil = '"+usuario.getPerfil()+"',"+
                        "Tipo = '"+usuario.getTipo()+"',"+
                        " WHERE idUsuario = " +usuario.getIdUsuario();
            psComando = conBanco.prepareStatement(strComandoSQL);
            psComando.executeUpdate();
            
            return true;
            
        } catch (Exception erro) {
            erro.printStackTrace();
            return false;
        }
    }
    
    public boolean excluirRegistro(int intCodigoUsuario) {
        String strComandoSQL;
        
        try {
            strComandoSQL = "DELETE FROM usuarios WHERE Registro_Usuario = " +intCodigoUsuario;
            psComando = conBanco.prepareStatement(strComandoSQL);
            psComando.executeUpdate();
            
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
            return false;
        }
    }
}
