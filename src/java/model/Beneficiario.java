

package model;

import model.*;
import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class Beneficiario {
    
    private int codigo;
    private String matricula;
    private String nome;
    private String dataNasc;
    private String cpf;
    private String pesquisa;
    private String sqlPesquisa;
    
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public Beneficiario(){
        
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String getSqlPesquisa() {
        return sqlPesquisa;
    }

    public void setSqlPesquisa(String sqlPesquisa) {
        this.sqlPesquisa = sqlPesquisa;
    }
    
}
