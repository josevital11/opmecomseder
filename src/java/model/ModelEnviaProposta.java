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
public class ModelEnviaProposta {
    
    private static final long serialVersionUID = 1L;

	private int id_prop;
	private int id_cir;
	private int id_bene;
	private int id_opme;
	private Long id_forn;
	private String nomeUser;

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public Long getUserLogadoId() {
        return userLogadoId;
    }

    public void setUserLogadoId(Long userLogadoId) {
        this.userLogadoId = userLogadoId;
    }
	private String matricula;
        
        private String nomeBenef;
        private String cod_proc;

    public String getNomeBenef() {
        return nomeBenef;
    }

    public void setNomeBenef(String nomeBenef) {
        this.nomeBenef = nomeBenef;
    }

    public String getCod_proc() {
        return cod_proc;
    }

    public void setCod_proc(String cod_proc) {
        this.cod_proc = cod_proc;
    }
        
        
	private String procedimento;
	private String tuss;
	private String comerc;
	private String unidade;
	private int qtd;
	private Double desconto;
	private Double unitario;
	private Double total;
	private String fab;
	private String anvisa;
        private Long userLogadoId;

    public String getTuss() {
        return tuss;
    }

    public void setTuss(String tuss) {
        this.tuss = tuss;
    }

    public String getComerc() {
        return comerc;
    }

    public void setComerc(String comerc) {
        this.comerc = comerc;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getUnitario() {
        return unitario;
    }

    public void setUnitario(Double unitario) {
        this.unitario = unitario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getAnvisa() {
        return anvisa;
    }

    public void setAnvisa(String anvisa) {
        this.anvisa = anvisa;
    }

    public int getId_prop() {
        return id_prop;
    }

    public void setId_prop(int id_prop) {
        this.id_prop = id_prop;
    }

    public int getId_cir() {
        return id_cir;
    }

    public void setId_cir(int id_cir) {
        this.id_cir = id_cir;
    }

    public int getId_bene() {
        return id_bene;
    }

    public void setId_bene(int id_bene) {
        this.id_bene = id_bene;
    }

    public int getId_opme() {
        return id_opme;
    }

    public void setId_opme(int id_opme) {
        this.id_opme = id_opme;
    }

    public Long getId_forn() {
        return id_forn;
    }

    public void setId_forn(Long id_forn) {
        this.id_forn = id_forn;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }
        
    public boolean isNovo() {
        if (this.id_prop == 0) {
                return true;
        }else if(this.id_prop != 0 && this.id_prop > 0) {
                return false;
        }
        return false;
    } 
    
}
