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
import connection.SingleConnectionBanco;
import connection.conexaoBancoDados;
import filter.FilterAutenticacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ModelLogin;
import model.ModelProposta;

public class DAOUsuarioRepository {

    private Connection connection;

    conexaoBancoDados conexao = new conexaoBancoDados();

    FilterAutenticacao filter = new FilterAutenticacao();

    private String usuarioLogado;

    private Long logadoId;
    
    public DAOUsuarioRepository() {

        connection = SingleConnectionBanco.getConnection();

    }

    public List<ModelLogin> consultaUsuarioList() throws Exception {  // AGUADANDO NOVA VERSï¿½O DO TOMCAT

        List<ModelLogin> retorno = new ArrayList<ModelLogin>();

        String sql = "select * from model_login  where useradmin is false";
        
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
            modelLogin.setLogin(resultado.getString("login"));

            retorno.add(modelLogin);
        }

        return retorno;
    }

    public List<ModelLogin> consultaUsuarioListRel(Long userlogadoid) throws Exception {

        List<ModelLogin> retorno = new ArrayList<ModelLogin>();

        String sql = "select * from model_login  where useradmin is false and usuario_id = " + userlogadoid;
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));

            retorno.add(modelLogin);
        }

        return retorno;
    }

    public List<ModelLogin> consultaUsuarioList(String nome, Long userLogadoId) throws Exception {
        List<ModelLogin> retorno = new ArrayList<ModelLogin>();

        String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
        //String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nome + "%");
        statement.setLong(2, userLogadoId);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));

            retorno.add(modelLogin);
        }

        return retorno;
    }
    
    public List<ModelLogin> consultaUsuarioLista(String nome, Long userLogadoId) throws Exception {
        List<ModelLogin> lista = new ArrayList<ModelLogin>();

        String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nome + "%");
        statement.setLong(2, userLogadoId);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));

            lista.add(modelLogin);
        }

        return lista;
    }

    public List<ModelLogin> gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
        List<ModelProposta> lista = new ArrayList<>();
        
        if (objeto.isNovo()) {/* Grava um novo */

            String sql = "INSERT INTO proposta(id_cir, id_opme, id_forn, cod_tiss, unidade, qtd, preco_unit, cod_fab, cod_anvisa, desc_forn, "
                    + "nomeUserLogado, matricula, nome_benef, cod_procedimento, nome_procedimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedSql = connection.prepareStatement(sql);

            preparedSql.setString(1, objeto.getLogin());
            preparedSql.setString(2, objeto.getSenha());
            preparedSql.setString(3, objeto.getNome());
            preparedSql.setString(4, objeto.getEmail());
            preparedSql.setLong(5, userLogado);
            preparedSql.setString(6, objeto.getPerfil());

            preparedSql.execute();

            connection.commit();

        } else {
            String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=? WHERE id =  " + objeto.getId() + "";

            PreparedStatement prepareSql = connection.prepareStatement(sql);

            prepareSql.setString(1, objeto.getLogin());
            prepareSql.setString(2, objeto.getSenha());
            prepareSql.setString(3, objeto.getNome());
            prepareSql.setString(4, objeto.getEmail());
            prepareSql.setString(5, objeto.getPerfil());

            prepareSql.executeUpdate();

            connection.commit();

        }

        return (List<ModelLogin>) this.consultaUsuarioList(objeto.getNome());
    }
    
    public List<ModelLogin> consultaUsuarioList(String nome) throws Exception {
        List<ModelLogin> lista = new ArrayList<>();

        String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false";
        //String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nome + "%");

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { /* percorrer as linhas de resultado do SQL */

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setPerfil(resultado.getString("perfil"));

            lista.add(modelLogin);
        }

        return lista;
    }

    public ModelLogin consultaUsuario(String login) throws Exception {

        ModelLogin modelLogin = new ModelLogin();

        String sql = "select * from model_login where upper(login) = upper('" + login
                + "') and useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) /* Se tem resultado */ {

            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
        }

        return modelLogin;

    }

    public List<ModelLogin> consultaUsuarioLogado(String login) throws Exception {

        List<ModelLogin> lista = new ArrayList();

        String sql = "select * from model_login where upper(login) = upper('" + login + "')";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();
        
        ModelLogin modelLogin = new ModelLogin();

        while (resultado.next()) /* Se tem resultado */ {

            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
        }

        return (List<ModelLogin>) this.consultaUsuarioList(modelLogin.getNome());

    }
    
    public List<ModelLogin> consultaUsuarioLogado(ModelLogin objeto, String login) throws Exception {

        List<ModelLogin> lista = new ArrayList();

        String sql = "select * from model_login where upper(login) = upper('" + login + "')";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();
        
        //ModelLogin modelLogin = new ModelLogin();

        while (resultado.next()) /* Se tem resultado */ {

            objeto.setId(resultado.getInt("id"));
            objeto.setEmail(resultado.getString("email"));
            objeto.setLogin(resultado.getString("login"));
            objeto.setSenha(resultado.getString("senha"));
            objeto.setNome(resultado.getString("nome"));
            objeto.setPerfil(resultado.getString("perfil"));
        }

        return (List<ModelLogin>) this.consultaUsuarioList(objeto.getLogin());

    }


    public Long consultaUsuarioLogadoId(String login) throws Exception {
        ModelLogin modelLogin = new ModelLogin();

        String sql = "select * from model_login where upper(login) = upper('" + login + "')";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

		//logadoId = 0;
        while (resultado.next()) /* Se tem resultado */ {

            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));

            logadoId = resultado.getLong("id");
        }

        return logadoId;
    }

    public ModelLogin consultarUsuarioId(String id, Long userLogado) throws SQLException {

        ModelLogin modelLogin = new ModelLogin();

        String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, Long.parseLong(id));
        statement.setLong(2, userLogado);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setEmail(resultado.getString("Eemail"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
        }

        return modelLogin;
    }

    public ModelLogin consultarUsuarioId(Long id) throws SQLException {

        ModelLogin modelLogin = new ModelLogin();

        String sql = "select * from model_login where id = ? and useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            modelLogin.setId(resultado.getInt("id"));
            modelLogin.setEmail(resultado.getString("email"));
            modelLogin.setLogin(resultado.getString("login"));
            modelLogin.setSenha(resultado.getString("senha"));
            modelLogin.setNome(resultado.getString("nome"));
            modelLogin.setPerfil(resultado.getString("perfil"));
        }

        return modelLogin;
    }

    public boolean validarLogin(String login) throws Exception {

        String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "');";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        resultado.next();/* Pra ele entrar nos resultados do sql */

        return resultado.getBoolean("existe");

    }

    public void deletarUser(String idUser) throws Exception {
        String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false ";

        PreparedStatement prepareSql = connection.prepareStatement(sql);

        prepareSql.setLong(1, Long.parseLong(idUser));

        prepareSql.executeUpdate();

        connection.commit();

    }
    
    public List<ModelLogin> gravarSenha(ModelLogin objeto, Long userLogadoId) throws Exception {
        //List<ModelSenha> lista = new ArrayList<>();

        String sql = "UPDATE model_login SET senha=? WHERE id =  " + userLogadoId + "";

        PreparedStatement prepareSql = connection.prepareStatement(sql);

        prepareSql.setString(1, objeto.getSenha());

        prepareSql.executeUpdate();

        connection.commit();

        return null;
    }

}
