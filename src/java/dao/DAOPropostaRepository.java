/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.SingleConnectionBanco;
import connection.conexaoBancoDados;
import filter.FilterAutenticacao;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Beneficiario;
import model.ModelCirurgia;
import model.ModelLogin;
import model.ModelProposta;
import model.ModelRelatorio;
import static net.sf.jasperreports.functions.standard.TextFunctions.UPPER;
import servlets.ServletProposta;
//import servlets.ServletPropostaController;

/**
 *
 * @author Usuario
 */
public class DAOPropostaRepository {

    private Connection connection;

    conexaoBancoDados conexao = new conexaoBancoDados();

    FilterAutenticacao filter = new FilterAutenticacao();

    ///ServletPropostaController propostacontroller = new ServletPropostaController();
    ServletProposta proposta;

    private String userLogado;

    private String login;

    private Long logadoId;

    private String nomeUser = "";

    private int codBenef = 0;
    
    public Date dataCir;

    private String mat, nome, codProc, nomeProc;
    
    private String  idBenef, idProc;

    private String codTUSS, nomeComerc, unidade, codANVISA, usuario, codFab, quantStr, unitStr, descStr, idOpmeStr;

    private int userId, idProp, idOpme, idForn, quant;

    private Double desc, precoUnit, precoTotal, precoConvertido;

    private boolean enviar, escolhido;

    public DAOPropostaRepository() {
        try {
            this.proposta = new ServletProposta();
        } catch (Exception ex) {
            Logger.getLogger(DAOPropostaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        connection = SingleConnectionBanco.getConnection();

    }

    public List<ModelProposta> consultaCirurgiaId(int idCir, String userLogado, Long userLogadoId) throws Exception {
        List<ModelProposta> lista = new ArrayList<>();

        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCir);

        ResultSet resultado = statement.executeQuery();
        
        Date dataCir = resultado.getDate("data_cir");

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            ModelProposta modelProposta = new ModelProposta();

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

            //************ PROPOSTA *******************
            String sqlProp = "SELECT * FROM `proposta` WHERE id_cir =  " + idCir;

            PreparedStatement stmProp = connection.prepareStatement(sqlProp);

            ResultSet rsProp = stmProp.executeQuery();

            while (rsProp.next()) {

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
            }

            nomeUser = consultaUsuario(userLogado);

            modelProposta.setId_prop(idProp);
            modelProposta.setId_cir(idCir);
            modelProposta.setMatricula(mat);
            modelProposta.setNomeBenef(nome);
            modelProposta.setCod_proc(codProc);
            modelProposta.setProcedimento(nomeProc);
            modelProposta.setNomeUser(nomeUser);
            modelProposta.setDesconto(0.00);
            modelProposta.setId_prop(idProp);
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

            return lista;
        }

        return null;
    }

    public String consultaUsuario(String nome) throws Exception {

        String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nome + "%");

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            nomeUser = resultado.getString("nome");

            logadoId = resultado.getLong("id");
        }

        return nomeUser;
    }

    /*
    public String consultaUsuarioId(String id) throws Exception {

        String sql = "select * from model_login  where upper(login) like upper(?) and useradmin is false";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nome + "%");

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { 

            nomeUser = resultado.getString("nome");
            
            logadoId = resultado.getLong("id");
        }

        return nomeUser;
    }
     */
    public List<ModelProposta> gravarProposta(ModelProposta objeto, Long userLogado) throws Exception {
        List<ModelProposta> lista = new ArrayList<>();

        if (objeto.isNovo()) {/* Grava um novo */

            String sql = "INSERT INTO proposta(id_cir, id_opme, id_forn, cod_tiss, nome_comerc, unidade, qtd, preco_unit, preco_total, "
                    + "cod_fab, cod_anvisa, desc_forn, nomeUserLogado, enviado, escolhido) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedSql = connection.prepareStatement(sql);

            preparedSql.setInt(1, objeto.getId_cir());
            preparedSql.setInt(2, objeto.getId_opme());
            preparedSql.setLong(3, objeto.getId_forn());
            preparedSql.setString(4, objeto.getTuss());
            preparedSql.setString(5, objeto.getComerc());
            preparedSql.setString(6, objeto.getUnidade());
            preparedSql.setInt(7, objeto.getQtd());
            preparedSql.setDouble(8, objeto.getUnitario());
            preparedSql.setDouble(9, objeto.getQtd() * objeto.getUnitario());
            preparedSql.setString(10, objeto.getFab());
            preparedSql.setString(11, objeto.getAnvisa());
            preparedSql.setDouble(12, objeto.getDesconto());
            preparedSql.setString(13, objeto.getNomeUser());
            preparedSql.setBoolean(14, false);
            preparedSql.setBoolean(15, false);

            preparedSql.execute();

            connection.commit();

        } else {
            String sql = "UPDATE proposta SET id_cir=?, id_opme=?, id_forn=?, cod_tiss=?, nome_comerc=?, unidade=?, qtd=?, preco_unit=?, preco_total=?, "
                    + "cod_fab=?, cod_anvisa=?, desc_forn=?, nomeUserLogado=?, enviado=?, escolhido=? WHERE id_prop= ?";
                    //+ "WHERE id_prop =  ?";

            PreparedStatement prepareSql = connection.prepareStatement(sql);

            prepareSql.setInt(1, objeto.getId_cir());
            prepareSql.setInt(2, objeto.getId_opme());
            prepareSql.setLong(3, objeto.getId_forn());
            prepareSql.setString(4, objeto.getTuss());
            prepareSql.setString(5, objeto.getComerc());
            prepareSql.setString(6, objeto.getUnidade());
            prepareSql.setInt(7, objeto.getQtd());
            prepareSql.setDouble(8, objeto.getUnitario());
            prepareSql.setDouble(9, objeto.getQtd() * objeto.getUnitario());
            prepareSql.setString(10, objeto.getFab());
            prepareSql.setString(11, objeto.getAnvisa());
            prepareSql.setDouble(12, objeto.getDesconto());
            prepareSql.setString(13, objeto.getNomeUser());
            if (enviar) {
                prepareSql.setBoolean(14, true);
            }else{
                prepareSql.setBoolean(14, false);
            }
            prepareSql.setBoolean(15, false);
            prepareSql.setInt(16, objeto.getId_prop());

            prepareSql.executeUpdate();

            connection.commit();

        }

        //return null;
        return (List<ModelProposta>) this.consultaItemProposta(String.valueOf(objeto.getId_prop()), objeto.getId_cir());
    }

    public List<ModelProposta> consultaOpme(ModelProposta objeto, String tuss) throws Exception {
        List<ModelProposta> lista = new ArrayList<>();

        ModelProposta modelProposta = new ModelProposta();

        if (!tuss.isEmpty()) {
            String sqlAplic = "SELECT * FROM aplicados WHERE cod_tiss = '" + tuss + "'";
            PreparedStatement statementAplic = connection.prepareStatement(sqlAplic);
            statementAplic.setString(1, "%" + tuss + "%");
            ResultSet resultadoAplic = statementAplic.executeQuery();

            if (resultadoAplic.next()) {
                /* percorrer as linhas de resultado do SQL */

                nomeComerc = resultadoAplic.getString("nome_comerc");
                unidade = resultadoAplic.getString("unidade");
                codANVISA = resultadoAplic.getString("cod_anvisa");
                idOpme = resultadoAplic.getInt("id_opme");

            } else {
                String sqlOpme = "SELECT * FROM opme WHERE cod_tiss = '" + tuss + "'";
                PreparedStatement statementOpme = connection.prepareStatement(sqlOpme);
                statementOpme.setString(1, "%" + tuss + "%");
                ResultSet resultadoOpme = statementOpme.executeQuery();

                nomeComerc = resultadoOpme.getString("nome_comerc");
                unidade = resultadoOpme.getString("unidade");
                codANVISA = resultadoOpme.getString("cod_anvisa");
                idOpme = resultadoOpme.getInt("id_opme");
            }

            modelProposta.setComerc(nomeComerc);
            modelProposta.setUnidade(unidade);
            modelProposta.setAnvisa(codANVISA);
            modelProposta.setId_opme(idOpme);

            lista.add(modelProposta);

            return lista;

        }

        return null;
    }

    public int consultaCodcir(int idProp) throws SQLException {
        //int idProp = Integer.parseInt(idPr);
        String sqlCir = "SELECT * FROM proposta WHERE id_prop = ? ";

        PreparedStatement prepareSql = connection.prepareStatement(sqlCir);
        prepareSql.setInt(1, idProp);
        ResultSet resultado = prepareSql.executeQuery();

        if (resultado.next()) {
            int codCir = resultado.getInt("id_cir");
            return codCir;
        }

        return 0;
    }

    public void deletarItemProposta(Long idProp) throws Exception {
        String sql = "DELETE FROM proposta WHERE id_prop = ? ";

        PreparedStatement prepareSql = connection.prepareStatement(sql);

        prepareSql.setLong(1, idProp);

        prepareSql.executeUpdate();

        connection.commit();

    }

    public List<ModelProposta> consultaItemProposta(String idProp, int idCir) throws Exception {
        List<ModelProposta> listaItemProposta = new ArrayList<>();
        ModelProposta modelProposta = new ModelProposta();

        String sql = "select * from proposta where id_prop = " + Integer.parseInt(idProp);

        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            modelProposta.setTuss(resultado.getString("cod_tiss"));
            modelProposta.setComerc(resultado.getString("nome_comerc"));
            modelProposta.setUnidade(resultado.getString("unidade"));
            modelProposta.setQtd(resultado.getInt("qtd"));
            //Locale ptBr = new Locale("pt", "BR");
            //Double d = resultado.getDouble("preco_unit");
            //String valorString = NumberFormat.getCurrencyInstance(ptBr).format(d);
            //modelProposta.setUnitario(Double.parseDouble(valorString));
            //String valorString = NumberFormat.getCurrencyInstance(ptBr).format(d);
            modelProposta.setUnitario(resultado.getDouble("preco_unit"));
            modelProposta.setFab(resultado.getString("cod_fab"));
            modelProposta.setAnvisa(resultado.getString("cod_anvisa"));
            modelProposta.setDesconto(resultado.getDouble("desc_forn"));
            modelProposta.setId_opme(resultado.getInt("id_opme"));
            modelProposta.setId_prop(Integer.parseInt(idProp));

            listaItemProposta.add(modelProposta);
        }
        return listaItemProposta;
    }

    public List<ModelProposta> consultaCirurgia(int idCir, String userLogado, Long userLogadoId) throws Exception {
        List<ModelProposta> listaCirurgia = new ArrayList<>();

        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCir);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            ModelProposta modelProposta = new ModelProposta();

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

            nomeUser = consultaUsuario(userLogado);

            modelProposta.setId_cir(idCir);
            modelProposta.setMatricula(mat);
            modelProposta.setNomeBenef(nome);
            modelProposta.setCod_proc(codProc);
            modelProposta.setProcedimento(nomeProc);
            modelProposta.setNomeUser(nomeUser);
            modelProposta.setDesconto(0.00);

            listaCirurgia.add(modelProposta);

            return listaCirurgia;
        }

        return null;
    }

    public List<ModelProposta> consultaProposta(int idCir, String userLogado, Long userLogadoId) throws Exception {
        List<ModelProposta> listaProposta = new ArrayList<>();

        //************ PROPOSTA *******************
        String sqlProp = "SELECT * FROM `proposta` WHERE id_cir = " + idCir + " and id_forn = " + userLogadoId + " and enviado = " + false;

        PreparedStatement stmProp = connection.prepareStatement(sqlProp);

        ResultSet rsProp = stmProp.executeQuery();

        while (rsProp.next()) {

            ModelProposta modelProposta = new ModelProposta();

            idProp = rsProp.getInt("id_prop");
            idCir = rsProp.getInt("id_cir");
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
            modelProposta.setId_cir(idCir);
            modelProposta.setDesconto(desc);
            modelProposta.setTuss(codTUSS);
            modelProposta.setComerc(nomeComerc);
            modelProposta.setUnidade(unidade);
            modelProposta.setQtd(quant);
            modelProposta.setUnitario(precoUnit);
            modelProposta.setFab(codFab);
            modelProposta.setAnvisa(codANVISA);
            modelProposta.setId_opme(idOpme);

            listaProposta.add(modelProposta);

        }

        return listaProposta;
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

    public void enviarProposta(int idCir, String userLogado, Long userLogadoId) throws Exception {

        String sqlProp = "SELECT * FROM `proposta` WHERE id_forn =  " + userLogadoId + " and id_cir = " + idCir + " and enviado is false";

        PreparedStatement stmProp = connection.prepareStatement(sqlProp);

        ResultSet rsProp = stmProp.executeQuery();

        while (rsProp.next()) {

            DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();

            idProp = rsProp.getInt("id_prop");
            idForn = rsProp.getInt("id_forn");
            idOpme = rsProp.getInt("id_opme");
            desc = rsProp.getDouble("desc_forn");
            codTUSS = rsProp.getString("cod_tiss");
            nomeComerc = rsProp.getString("nome_comerc");
            desc = rsProp.getDouble("desc_forn");
            unidade = rsProp.getString("unidade");
            quant = rsProp.getInt("qtd");
            precoUnit = rsProp.getDouble("preco_unit");
            codFab = rsProp.getString("cod_fab");
            codANVISA = rsProp.getString("cod_anvisa");
            enviar = rsProp.getBoolean("enviado");
            nomeUser = consultaUsuario(userLogado);
            escolhido = false;

            //if (objeto.envia()) {/* Grava um novo */
            String sqlEnvia = "INSERT INTO envia_proposta(id_cir, id_opme, id_forn, cod_tiss, nome_comerc, unidade, qtd, preco_unit, preco_total, "
                    + "cod_fab, cod_anvisa, desc_forn, nomeUserLogado, usuario_id, escolhido) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedSql = connection.prepareStatement(sqlEnvia);

            preparedSql.setInt(1, idCir);
            preparedSql.setInt(2, idOpme);
            preparedSql.setLong(3, idForn);
            preparedSql.setString(4, codTUSS);
            preparedSql.setString(5, nomeComerc);
            preparedSql.setString(6, unidade);
            preparedSql.setInt(7, quant);
            preparedSql.setDouble(8, precoUnit);
            preparedSql.setDouble(9, quant * precoUnit);
            preparedSql.setString(10, codFab);
            preparedSql.setString(11, codANVISA);
            preparedSql.setDouble(12, desc);
            preparedSql.setString(13, nomeUser);
            preparedSql.setLong(14, userLogadoId);
            preparedSql.setBoolean(15, escolhido);

            preparedSql.execute();

            ModelProposta modelProposta = new ModelProposta();

            daoPropostaRepository.alterarProposta(modelProposta, userLogadoId, idProp);

        }

        connection.commit();

    }

    public List<ModelProposta> alterarProposta(ModelProposta objeto, Long userLogado, int idProposta) throws Exception {

        String sql = "UPDATE proposta SET enviado=? "
                + "WHERE id_prop =  " + idProposta + "";

        PreparedStatement prepareSql = connection.prepareStatement(sql);

        prepareSql.setBoolean(1, true);

        prepareSql.executeUpdate();

        connection.commit();

        return null;
        //return (List<ModelProposta>) this.consultaItemProposta(String.valueOf(objeto.getId_prop()), objeto.getId_cir());
    }

    public List<ModelProposta> consultaCodigoCirurgia(int idCir, String userLogado, Long userLogadoId) throws Exception {
        List<ModelProposta> listaCodigoCirurgia = new ArrayList<>();

        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCir);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            ModelProposta modelProposta = new ModelProposta();

            nomeUser = consultaUsuario(userLogado);

            modelProposta.setId_cir(idCir);

            listaCodigoCirurgia.add(modelProposta);

            return listaCodigoCirurgia;
        }

        return null;
    }

    public List<ModelProposta> consultaPropostaCirurgiaId(int idCir, String userLogado, Long userLogadoId) throws Exception {
        /*
        String sqlUser = "select * from model_login where login = ?";
        PreparedStatement statementUser = connection.prepareStatement(sqlUser);
        statementUser.setString(1, userLogado.toUpperCase());
        ResultSet rsu = statementUser.executeQuery();
        while (rsu.next()){
            nomeUser = rsu.getString("nome");
        }
         */
        List<ModelProposta> lista = new ArrayList<>();

        //************ CIRURGIA *******************
        String sql = "select * from cirurgia  where id_cir=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idCir);

        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            /* percorrer as linhas de resultado do SQL */

            ModelProposta modelProposta = new ModelProposta();

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

            //************ PROPOSTA *******************
            String sqlProp = "SELECT * FROM `proposta` WHERE id_cir =  " + idCir + " and id_forn = " + userLogadoId;

            PreparedStatement stmProp = connection.prepareStatement(sqlProp);

            ResultSet rsProp = stmProp.executeQuery();

            while (rsProp.next()) {

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
                modelProposta.setId_cir(idCir);
                modelProposta.setMatricula(mat);
                modelProposta.setNomeBenef(nome);
                modelProposta.setCod_proc(codProc);
                modelProposta.setProcedimento(nomeProc);
                modelProposta.setNomeUser(nomeUser);
                modelProposta.setDesconto(0.00);
                modelProposta.setId_prop(idProp);
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

        return null;
    }

    /*
    public void copiarProposta(int idCir, String userLogado, Long userLogadoId) throws Exception {
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
            // percorrer as linhas de resultado do SQL 
            
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

            adicionaDadosRelatorio(idRel, mat, nome, dataCir, codProc, nomeProc, nomeUser, 
                desc, idProp, codTUSS, nomeComerc, unidade, quant, precoUnit, precoTotal);

        }

        connection.commit();
    }

    public void adicionaDadosRelatorio(int idRel, String matBenef, String nomeBenef, Date dataCir, String codProc, String nomeProc, String nomeForn, 
            Double desc, int idProp, String codTUSS, String nomeComerc, String unidade, int qta, Double precoUnit, Double precoTotal){
        
        String sqlCopia = "INSERT INTO rel_proposta(id_rel, mat_benef, nome_benef, data_cir, cod_proc, nome_proc, nome_forn, desc_forn, id_prop, cod_tuss, "
                    + "nome_comerc, unidade, qtd, preco_unit, preco_total"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedSql;
            preparedSql = connection.prepareStatement(sqlCopia);

            preparedSql.setInt(1, idRel);
            preparedSql.setString(2, matBenef);
            preparedSql.setString(3, nomeBenef);
            preparedSql.setDate(4, dataCir);
            preparedSql.setString(5, codProc);
            preparedSql.setString(6, nomeProc);
            preparedSql.setString(7, nomeForn);
            preparedSql.setDouble(8, desc);
            preparedSql.setInt(9, idProp);
            preparedSql.setString(10, codTUSS);
            preparedSql.setString(11, nomeComerc);
            preparedSql.setString(12, unidade);
            preparedSql.setInt(13, quant);
            preparedSql.setDouble(14, precoUnit);
            preparedSql.setDouble(15, quant * precoUnit);

            preparedSql.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPropostaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
*/
}
