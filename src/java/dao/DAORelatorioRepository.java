/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.SingleConnectionBanco;
import connection.conexaoBancoDados;
import filter.FilterAutenticacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Beneficiario;
import model.ModelLogin;
import model.ModelProposta;
import model.ModelRelatorio;
import servlets.ServletProposta;

/**
 *
 * @author Usuario
 */
public class DAORelatorioRepository {

    private Connection connection;

    conexaoBancoDados conexao = new conexaoBancoDados();

    FilterAutenticacao filter = new FilterAutenticacao();

    ///ServletPropostaController propostacontroller = new ServletPropostaController();
    ServletProposta proposta;

    private String userLogado;

    private String login;

    private Long logadoId;

    private String nomeUser = "";

    private int idCir, codBenef = 0;

    public Date dataCir;

    private String mat, nome, codProc, nomeProc;

    private String idBenef, idProc;

    private String codTUSS, nomeComerc, unidade, codANVISA, usuario, codFab, quantStr, unitStr, descStr, idOpmeStr;

    private int userId, idProp, idOpme, idForn, quant;

    private Double desc, precoUnit, precoTotal, precoConvertido;

    public Statement st = null;

    public String Mensagem_erro = null;

    ;
    
    public DAORelatorioRepository() {
        try {
            this.proposta = new ServletProposta();
        } catch (Exception ex) {
            Logger.getLogger(DAORelatorioRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        connection = SingleConnectionBanco.getConnection();

    }

    /*
    public List<ModelProposta> consultaPropostaRel(int idCir, String userLogado, Long userLogadoId) throws Exception {
        List<ModelProposta> lista = new ArrayList<>();

        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir= " + idCir;
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            // percorrer as linhas de resultado do SQL 

            String idBenef = resultado.getString("id_benef");

            String idProc = resultado.getString("id_proc");

            String sqlBene = "SELECT * FROM `beneficiario` WHERE id_benef =  " + Integer.parseInt(idBenef);

            Beneficiario bene = new Beneficiario();

            PreparedStatement stmBene = connection.prepareStatement(sqlBene);

            ResultSet rsbene = stmBene.executeQuery();

            while (rsbene.next()) {

                codBenef = rsbene.getInt("id_benef");
                mat = rsbene.getString("mat_benef");
                nome = rsbene.getString("nome_benef");
            }

            String sqlProc = "SELECT * FROM `procedimento` WHERE id_proc =  " + Integer.parseInt(idProc);

            PreparedStatement stmProc = connection.prepareStatement(sqlProc);

            ResultSet rsProc = stmProc.executeQuery();

            while (rsProc.next()) {

                codProc = rsProc.getString("cod_proc");
                nomeProc = rsProc.getString("nome_proc");
            }

        }

        //************ PROPOSTA *******************
        String sqlProp = "SELECT * FROM `proposta` WHERE id_forn =  " + userLogadoId + " and id_cir = " + idCir;

        PreparedStatement stmProp = connection.prepareStatement(sqlProp);

        ResultSet rsProp = stmProp.executeQuery();

        while (rsProp.next()) {

            ModelProposta modelProposta = new ModelProposta();

            idProp = rsProp.getInt("id_prop");
            desc = rsProp.getDouble("desc_forn");
            codTUSS = rsProp.getString("cod_tiss");
            nomeComerc = rsProp.getString("nome_comerc");
            unidade = rsProp.getString("unidade");
            quant = rsProp.getInt("qtd");
            precoUnit = rsProp.getDouble("preco_unit");
            codFab = rsProp.getString("cod_fab");
            codANVISA = rsProp.getString("cod_anvisa");
            idOpme = rsProp.getInt("id_opme");

            nomeUser = consultaUsuario(userLogado);

            modelProposta.setId_prop(idProp);
            // modelProposta.setUserLogadoId(userLogadoId);
            modelProposta.setMatricula(mat);
            modelProposta.setNomeBenef(nome);
            modelProposta.setCod_proc(codProc);
            modelProposta.setProcedimento(nomeProc);
            modelProposta.setNomeUser(nomeUser);
            modelProposta.setUserLogadoId(userLogadoId);
            //modelProposta.setId_prop(idProp);
            modelProposta.setDesconto(desc);
            modelProposta.setTuss(codTUSS);
            modelProposta.setComerc(nomeComerc);
            modelProposta.setUnidade(unidade);
            modelProposta.setQtd(quant);
            modelProposta.setUnitario(precoUnit);
            modelProposta.setFab(codFab);
            modelProposta.setAnvisa(codANVISA);
            modelProposta.setId_opme(idOpme);

            lista.add(modelProposta);

        }

        return lista;
    }
     */
    public List<ModelLogin> consultaUsuarioList() throws Exception {  // AGUADANDO NOVA VERSï¿½O DO TOMCAT

        List<ModelLogin> retorno = new ArrayList<ModelLogin>();

        String sql = "select * from model_login  where useradmin is false";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

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

    public List<ModelRelatorio> consultaRelatorio() throws Exception {

        List<ModelRelatorio> retorno = new ArrayList<ModelRelatorio>();

        String sql = "select * from rel_proposta";

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            ModelRelatorio modelRelatorio = new ModelRelatorio();

            modelRelatorio.setMatBenef(resultado.getString("mat_benef"));
            modelRelatorio.setNomeBenef(resultado.getString("nome_benef"));
            modelRelatorio.setCodProc(resultado.getString("cod_proc"));
            modelRelatorio.setNomeProc(resultado.getString("nome_proc"));
            modelRelatorio.setDataCir(resultado.getDate("data_cir"));
            modelRelatorio.setNomeForn(resultado.getString("nome_forn"));
            modelRelatorio.setIdProp(resultado.getInt("id_prop"));
            modelRelatorio.setCodTUSS(resultado.getString("cod_tuss"));
            modelRelatorio.setNomeComerc(resultado.getString("nome_comerc"));
            modelRelatorio.setQta(resultado.getInt("qtd"));
            modelRelatorio.setPrecoUnit(resultado.getDouble("preco_unit"));
            modelRelatorio.setPrecoTotal(resultado.getDouble("preco_total"));

            retorno.add(modelRelatorio);
        }

        return retorno;
    }

    public void copiarProposta(int idCir, String userLogado, Long userLogadoId) throws Exception {

        Deletar();

        int idRel = 0;
        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCir);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            dataCir = resultado.getDate("data_cir");

            idBenef = resultado.getString("id_benef");

            idProc = resultado.getString("id_proc");
            /* percorrer as linhas de resultado do SQL */

        }

        //ModelProposta modelProposta = new ModelProposta();
        String sqlBene = "SELECT * FROM `beneficiario` WHERE id_benef =  " + Integer.parseInt(idBenef);

        Beneficiario bene = new Beneficiario();

        PreparedStatement stmBene = connection.prepareStatement(sqlBene);

        ResultSet rsbene = stmBene.executeQuery();

        while (rsbene.next()) {

            codBenef = rsbene.getInt("id_benef");
            mat = rsbene.getString("mat_benef");
            nome = rsbene.getString("nome_benef");
        }

        String sqlProc = "SELECT * FROM `procedimento` WHERE id_proc =  " + Integer.parseInt(idProc);

        PreparedStatement stmProc = connection.prepareStatement(sqlProc);

        ResultSet rsProc = stmProc.executeQuery();

        while (rsProc.next()) {

            codProc = rsProc.getString("cod_proc");
            nomeProc = rsProc.getString("nome_proc");
        }

        String sqlForn = "SELECT * FROM `model_login` WHERE id =  " + userLogadoId;

        PreparedStatement stmForn = connection.prepareStatement(sqlForn);

        ResultSet rsForn = stmForn.executeQuery();

        while (rsForn.next()) {

            nomeUser = rsForn.getString("nome");
        }

        //************ PROPOSTA *******************
        String sqlProp = "SELECT * FROM `proposta` WHERE id_cir =  " + idCir + " and id_forn = " + userLogadoId;

        PreparedStatement stmProp = connection.prepareStatement(sqlProp);

        ResultSet rsProp = stmProp.executeQuery();

        while (rsProp.next()) {

            ModelRelatorio modelRelatorio = new ModelRelatorio();

            idRel = idRel + 1;
            idProp = rsProp.getInt("id_prop");
            desc = rsProp.getDouble("desc_forn");
            codTUSS = rsProp.getString("cod_tiss");
            nomeComerc = rsProp.getString("nome_comerc");
            unidade = rsProp.getString("unidade");
            quant = rsProp.getInt("qtd");
            precoUnit = rsProp.getDouble("preco_unit");
            precoTotal = rsProp.getDouble("preco_total");

            adicionaDadosRelatorio(idRel, mat, nome, idCir, dataCir, codProc, nomeProc, nomeUser,
                    desc, idProp, codTUSS, nomeComerc, unidade, quant, precoUnit, precoTotal);

        }

        connection.commit();
        
    }

    public void adicionaDadosRelatorio(int idRel, String matBenef, String nomeBenef, int idCir, Date dataCir, String codProc, String nomeProc, String nomeForn,
            Double desc, int idProp, String codTUSS, String nomeComerc, String unidade, int qta, Double precoUnit, Double precoTotal) {

        String sqlCopia = "INSERT INTO rel_proposta(id_rel, mat_benef, nome_benef, id_cir, data_cir, cod_proc, nome_proc, nome_forn, desc_forn, id_prop, cod_tuss, "
                + "nome_comerc, unidade, qtd, preco_unit, preco_total)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedSql;
            preparedSql = connection.prepareStatement(sqlCopia);

            preparedSql.setInt(1, idRel);
            preparedSql.setString(2, matBenef);
            preparedSql.setString(3, nomeBenef);
            preparedSql.setInt(4, idCir);
            preparedSql.setDate(5, dataCir);
            preparedSql.setString(6, codProc);
            preparedSql.setString(7, nomeProc);
            preparedSql.setString(8, nomeForn);
            preparedSql.setDouble(9, desc);
            preparedSql.setInt(10, idProp);
            preparedSql.setString(11, codTUSS);
            preparedSql.setString(12, nomeComerc);
            preparedSql.setString(13, unidade);
            preparedSql.setInt(14, quant);
            preparedSql.setDouble(15, precoUnit);
            preparedSql.setDouble(16, quant * precoUnit);

            preparedSql.execute();
            
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DAORelatorioRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpaDadosReltorio() throws SQLException {
        //String sqlDelete = "delete from rel_proposta";
        Statement st = connection.createStatement();
        int sqlDelete = st.executeUpdate("delete from rel_proposta");
        //st.executeUpdate(sqlDelete);
        connection.commit();
    }

    public boolean Deletar() throws SQLException {

        boolean resposta;

        try {
            //sql pra deletar os dados da tabela
            String sqlDelete = "DELETE FROM rel_proposta ";
            Statement st = connection.createStatement();
            st.executeUpdate(sqlDelete);
            connection.commit();
            resposta = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            resposta = false;
            String Mensagem_erro = "BANCO MYSQL CONECTADO...\n\r " + " \n\r ERRO 4... AO DELETAR OS DADOS..!" + ex.toString();
        }
        return resposta;
    }

    public String getMensagem_erro() {

        return Mensagem_erro;
    }

    public void copiarPropostas(Long userLogadoId) throws Exception {

        Deletar();

        int idRel = 0;

        //************ PROPOSTA *******************
        String sqlProp = "SELECT * FROM `proposta` WHERE id_forn = " + userLogadoId;

        PreparedStatement stmProp = connection.prepareStatement(sqlProp);

        ResultSet rsProp = stmProp.executeQuery();

        while (rsProp.next()) {
            
            int idCir = rsProp.getInt("id_cir");

            //************ CIRURGIA *******************
            String sql = "select * from cirurgia  where id_cir = " + idCir;
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setInt(1, idCir);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {

                //idCir = resultado.getInt("id_cir");

                dataCir = resultado.getDate("data_cir");

                idBenef = resultado.getString("id_benef");

                idProc = resultado.getString("id_proc");

                String sqlBene = "SELECT * FROM `beneficiario` WHERE id_benef =  " + Integer.parseInt(idBenef);

                Beneficiario bene = new Beneficiario();

                PreparedStatement stmBene = connection.prepareStatement(sqlBene);

                ResultSet rsbene = stmBene.executeQuery();

                while (rsbene.next()) {

                    codBenef = rsbene.getInt("id_benef");
                    mat = rsbene.getString("mat_benef");
                    nome = rsbene.getString("nome_benef");
                }

                String sqlProc = "SELECT * FROM `procedimento` WHERE id_proc =  " + Integer.parseInt(idProc);

                PreparedStatement stmProc = connection.prepareStatement(sqlProc);

                ResultSet rsProc = stmProc.executeQuery();

                while (rsProc.next()) {

                    codProc = rsProc.getString("cod_proc");
                    nomeProc = rsProc.getString("nome_proc");
                }

                String sqlForn = "SELECT * FROM `model_login` WHERE id =  " + userLogadoId;

                PreparedStatement stmForn = connection.prepareStatement(sqlForn);

                ResultSet rsForn = stmForn.executeQuery();

                while (rsForn.next()) {

                    nomeUser = rsForn.getString("nome");
                }

                idRel = idRel + 1;
                idCir = rsProp.getInt("id_cir");
                idProp = rsProp.getInt("id_prop");
                desc = rsProp.getDouble("desc_forn");
                codTUSS = rsProp.getString("cod_tiss");
                nomeComerc = rsProp.getString("nome_comerc");
                unidade = rsProp.getString("unidade");
                quant = rsProp.getInt("qtd");
                precoUnit = rsProp.getDouble("preco_unit");
                precoTotal = rsProp.getDouble("preco_total");

                adicionaDadosRelatorio(idRel, mat, nome, idCir, dataCir, codProc, nomeProc, nomeUser,
                        desc, idProp, codTUSS, nomeComerc, unidade, quant, precoUnit, precoTotal);

            }
        }

        connection.commit();
    }
}
