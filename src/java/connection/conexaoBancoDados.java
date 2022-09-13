/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Usuario
 */
public class conexaoBancoDados {

    static Connection conBanco;

    public static boolean abrirConexao() {
        
        String driver = "com.mysql.cj.jdbc.Driver";
        //Informações referentes ao banco de dados
        String url = "jdbc:mysql://localhost:3306/opme?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "";
        
        try {
            Class.forName(driver);
            conBanco = DriverManager.getConnection(url, user, password);
            return true;
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            //JOptionPane.showConfirmDialog(null, "Erro na conexão!\n Error" + e.getMessage());
            return false;
        }
    }
    
    public void fecharConexao() {
        try {
            conBanco.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conBanco;
    }
}
