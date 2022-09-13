/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Usuario
 */
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import connection.SingleConnectionBanco;
//import connection.conexaoBancoDados;
//import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ModelLogin;

public class DAOLoginRepository {

    private Connection connection;
    
    public String perfil;

    public DAOLoginRepository() {

            //conexao = new conexaoBancoDados();
        //connection = conexaoBancoDados.getConnection();
        connection = SingleConnectionBanco.getConnection();
    }

    ModelLogin modelLogin = new ModelLogin();

    public boolean validarAutenticacao(ModelLogin modelLogin) throws SQLException {
        try {
            String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
		//String sql = "select * from model_login where login = ? and senha = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, modelLogin.getLogin());
            statement.setString(2, modelLogin.getSenha());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                perfil = (resultSet.getString("perfil"));
                return true; // autenticado
            }

            return false; // Não autenticado

        } catch (Exception e) {
            printStackTrace();
        }
        return false;
    }
}
