/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class ModelRelatorio {
    
    private int idRel;
    private String matBenef;
    private String nomeBenef;
    private String codProc;
    private String nomeProc;
    private String nomeForn; 
    private int idProp;
    private Double desc;
    private String codTUSS;
    private String nomeComerc;
    private String unidade;
    private int qta;
    private Double precoUnit;
    private Double precoTotal;
    private Date dataCir;
    private int idCir;

    public int getIdCir() {
        return idCir;
    }

    public void setIdCir(int idCir) {
        this.idCir = idCir;
    }

    public Date getDataCir() {
        return dataCir;
    }

    public void setDataCir(Date dataCir) {
        this.dataCir = dataCir;
    }

    public int getIdRel() {
        return idRel;
    }

    public void setIdRel(int idRel) {
        this.idRel = idRel;
    }

    public String getMatBenef() {
        return matBenef;
    }

    public void setMatBenef(String matBenef) {
        this.matBenef = matBenef;
    }

    public String getNomeBenef() {
        return nomeBenef;
    }

    public void setNomeBenef(String nomeBenef) {
        this.nomeBenef = nomeBenef;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public String getNomeProc() {
        return nomeProc;
    }

    public void setNomeProc(String nomeProc) {
        this.nomeProc = nomeProc;
    }

    public String getNomeForn() {
        return nomeForn;
    }

    public void setNomeForn(String nomeForn) {
        this.nomeForn = nomeForn;
    }

    public int getIdProp() {
        return idProp;
    }

    public void setIdProp(int idProp) {
        this.idProp = idProp;
    }

    public Double getDesc() {
        return desc;
    }

    public void setDesc(Double desc) {
        this.desc = desc;
    }

    public String getCodTUSS() {
        return codTUSS;
    }

    public void setCodTUSS(String codTUSS) {
        this.codTUSS = codTUSS;
    }

    public String getNomeComerc() {
        return nomeComerc;
    }

    public void setNomeComerc(String nomeComerc) {
        this.nomeComerc = nomeComerc;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQta() {
        return qta;
    }

    public void setQta(int qta) {
        this.qta = qta;
    }

    public Double getPrecoUnit() {
        return precoUnit;
    }

    public void setPrecoUnit(Double precoUnit) {
        this.precoUnit = precoUnit;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    
}
