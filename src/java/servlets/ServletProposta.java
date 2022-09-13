/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import connection.SingleConnectionBanco;
//import connection.conexaoBancoDados;
import dao.DAOPropostaRepository;
import dao.DAORelatorioRepository;
import dao.DAOUsuarioRepository;
import filter.FilterAutenticacao;
import java.awt.HeadlessException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import model.ModelLogin;
import model.ModelProposta;
import model.ModelRelatorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static org.hibernate.criterion.Expression.sql;

/**
 *
 * @author Usuario
 */
//@WebServlet(name = "ServletProposta", urlPatterns = {"/ServletProposta"})
public class ServletProposta extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
    
    private FilterAutenticacao filterAutenticacao = new FilterAutenticacao();

    private String usuarioLogado;

    private Long userLogadoId;
    
    private int idCir;
    
    private FacesContext context;
    
    private ByteArrayOutputStream baos;
    
    private InputStream stream;
    
    private Connection conecta;

    //conexaoBancoDados conexao = new conexaoBancoDados();

    public ServletProposta() throws Exception {
        super();
        conecta = SingleConnectionBanco.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletProposta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletProposta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession();
    usuarioLogado = (String) session.getAttribute("usuario");

        
        //usuarioLogado = filterAutenticacao.usuarioLogado;
        userLogadoId = 0L;
        try {
            userLogadoId = daoUsuarioRepository.consultaUsuarioLogadoId(usuarioLogado);
        } catch (Exception e1) {
            e1.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e1.getMessage());
            redirecionar.forward(request, response);
        }

        String acao = request.getParameter("acao");

        //response.getWriter().write(acao);
        if (userLogadoId > 0) {

            try {
                if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

                    DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();
                    
                    String idProp = request.getParameter("codProp");
                    
                    String idCir = request.getParameter("codCir");

                    daoPropostaRepository.deletarItemProposta(Long.parseLong(idProp));

                    idCir = request.getParameter("codCir");
                    
                    List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCirurgia(Integer.parseInt(idCir), usuarioLogado, userLogadoId);
                    
                    List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(Integer.parseInt(idCir), usuarioLogado, userLogadoId);
                    
                    request.setAttribute("msg", "Cirurgia pesquisada");
                    request.setAttribute("listaCirurgia", listaCirurgia);
                    request.setAttribute("listaProposta", listaProposta);
                    request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

                    String idUser = request.getParameter("id");

                    daoUsuarioRepository.deletarUser(idUser);

                    response.getWriter().write("Exluído com sucesso!");

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarCirurgia-ajax")) {

                    int idCir = Integer.parseInt(request.getParameter("codigo_cir"));

                    DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();
                    
                    List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCirurgia(idCir, usuarioLogado, userLogadoId);
                    
                    List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(idCir, usuarioLogado, userLogadoId);
                    
                    request.setAttribute("msg", "Cirurgia pesquisada");
                    //request.setAttribute("cirurgia", cirurgia);
                    request.setAttribute("listaCirurgia", listaCirurgia);
                    request.setAttribute("listaProposta", listaProposta);
                    request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);
                            
                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

                    String id = request.getParameter("id");

                    ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, userLogadoId);

                    request.setAttribute("msg", "Usuário em edição");
                    request.setAttribute("modelLogin", modelLogin);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) { // AGUADANDO NOVA VERSï¿½O DO TOMCAT

                    List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList();

                    request.setAttribute("msg", "Usuário em edição");
                    request.setAttribute("modelLogins", modelLogins);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelProposta")) {
                    
                    String idCodCir = request.getParameter("codigo_cir");
                    
                    int idCir = Integer.parseInt(idCodCir);
                    
                    DAORelatorioRepository daoRelatorioRepository = new DAORelatorioRepository();
                    
                    daoRelatorioRepository.copiarProposta(idCir, usuarioLogado, userLogadoId);
                    
                    stream = this.getClass().getResourceAsStream("/report/relatorio-proposta.jasper");
                    Map<String, Object> params = new HashMap<String, Object>();
                    baos = new ByteArrayOutputStream();
                    
                    net.sf.jasperreports.engine.JasperReport report = (net.sf.jasperreports.engine.JasperReport) JRLoader.loadObject(stream);
                    JasperPrint print = JasperFillManager.fillReport(report, params, SingleConnectionBanco.getConnection());
                    JasperExportManager.exportReportToPdfStream(print, baos);
                    
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(baos.size());
                    response.setHeader("Content-disposition", "inline: filename=relatorio.pdf");
                    response.getOutputStream().write(baos.toByteArray());
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    
                    context.responseComplete();
                
                }else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelPropostas")) {
                    
                    //String idCodCir = request.getParameter("codigo_cir");
                    
                    //int idCir = Integer.parseInt(idCodCir);
                    
                    DAORelatorioRepository daoRelatorioRepository = new DAORelatorioRepository();
                    
                    daoRelatorioRepository.copiarPropostas(userLogadoId);
                    
                    stream = this.getClass().getResourceAsStream("/report/relatorio-minhas-propostas.jasper");
                    Map<String, Object> params = new HashMap<String, Object>();
                    baos = new ByteArrayOutputStream();
                    
                    net.sf.jasperreports.engine.JasperReport report = (net.sf.jasperreports.engine.JasperReport) JRLoader.loadObject(stream);
                    JasperPrint print = JasperFillManager.fillReport(report, params, SingleConnectionBanco.getConnection());
                    JasperExportManager.exportReportToPdfStream(print, baos);
                    
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(baos.size());
                    response.setHeader("Content-disposition", "inline: filename=relatorio.pdf");
                    response.getOutputStream().write(baos.toByteArray());
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    
                    context.responseComplete();
                    
                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("visualizarDados")) {

                    DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();
                    
                    String strIdProp = request.getParameter("id");
                    
                    int idProp = Integer.valueOf(strIdProp);
                    
                    int idCir = daoPropostaRepository.consultaCodcir(idProp);
                    
                    if (idCir > 0){
                    
                        List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCirurgia(idCir, usuarioLogado, userLogadoId);

                        List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(idCir, usuarioLogado, userLogadoId);
                        
                        List<ModelProposta> listaItemProposta = daoPropostaRepository.consultaItemProposta(strIdProp, idCir);

                        request.setAttribute("msg", "Cirurgia pesquisada");
                        //request.setAttribute("cirurgia", cirurgia);
                        request.setAttribute("listaCirurgia", listaCirurgia);
                        request.setAttribute("listaProposta", listaProposta);
                        request.setAttribute("listaItemProposta", listaItemProposta);
                        request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);
                        
                    }

                }else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("enviaProposta")) {
                    
                    DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();
                    
                    String codCir = request.getParameter("codigo_cir");

                    idCir = Integer.parseInt(codCir);

                    daoPropostaRepository = new DAOPropostaRepository();

                    daoPropostaRepository.enviarProposta(idCir, usuarioLogado, userLogadoId);

                    //List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCirurgia(idCir, usuarioLogado, userLogadoId);

                    //List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(idCir, usuarioLogado, userLogadoId);

                    request.setAttribute("msg", "Proposta enviada");
                    //request.setAttribute("cirurgia", cirurgia);
                    //request.setAttribute("listaCirurgia", listaCirurgia);
                    //request.setAttribute("listaProposta", listaProposta);
                    request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);
                    
                }if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("enviar")) {

                    DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();
                    
                    //String idProp = request.getParameter("codProp");
                    
                    String idCir = request.getParameter("codCir");

                    //daoPropostaRepository.deletarItemProposta(Long.parseLong(idProp));

                    //idCir = request.getParameter("codCir");
                    
                    daoPropostaRepository.enviarProposta(Integer.parseInt(idCir), usuarioLogado, userLogadoId);
                    //List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCodigoCirurgia(Integer.parseInt(idCir), usuarioLogado, userLogadoId);
                    
                    //List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(Integer.parseInt(idCir), usuarioLogado, userLogadoId);
                    
                    request.setAttribute("msg", "Cirurgia pesquisada");
                    //request.setAttribute("listaCirurgia", listaCirurgia);
                    //request.setAttribute("listaProposta", listaProposta);
                    request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);

                }
					///HashedMap<String, Object> params = new HashedMap<String, Object>();
                ///params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorios") + File.separator);

					///byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", params, request.getServletContext());
                /////byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", request.getServletContext());
                //response.setHeader("Content-Disposition", "attachment; filename=arquivo.pdf");
                ////response.getOutputStream().write(reletorio);
            } catch (SQLException ex) {
                Logger.getLogger(ServletUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                System.out.println("Erro: "+e);
                e.printStackTrace();
                RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
                request.setAttribute("msg", e.getMessage());
                redirecionar.forward(request, response);
            }

            ///request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        try {

            String msg = "Operação realizada com sucesso";

            String id = request.getParameter("codProp");
            String idCir = request.getParameter("codCir");
            String idOpme = request.getParameter("idOpme");
            Long idForn = userLogadoId;
            
            //Long userLogado = userLogadoId;
            String codTiss = request.getParameter("tuss");
            String comercial = request.getParameter("comercial");
            String unidade = request.getParameter("unidade");
            String quantidade = request.getParameter("quantidade");
            String preco = request.getParameter("preco");
            String fabricante = request.getParameter("fabricante");
            String anvisa = request.getParameter("anvisa");
            String desconto = request.getParameter("desconto");
            String nomeUserLogado = request.getParameter("nomeUser");
            //String idUserLogado = request.getParameter("usuario_id");

            desconto = desconto.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
            preco = preco.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");

            ModelProposta modelProposta = new ModelProposta();

            modelProposta.setId_prop(id != null && !id.isEmpty() ? Integer.parseInt(id) : 0);
            modelProposta.setId_cir(Integer.parseInt(idCir));
            modelProposta.setId_opme(Integer.parseInt(idOpme));
            modelProposta.setId_forn((idForn));
            modelProposta.setTuss(codTiss);
            modelProposta.setComerc(comercial);
            modelProposta.setUnidade(unidade);
            modelProposta.setQtd((int) Long.parseLong(quantidade));
            modelProposta.setUnitario(Double.parseDouble(preco));
            modelProposta.setTotal(Double.parseDouble(preco)*Long.parseLong(quantidade));
            modelProposta.setFab(fabricante);
            modelProposta.setAnvisa(anvisa);
            modelProposta.setDesconto(Double.parseDouble(desconto));
            modelProposta.setNomeUser(nomeUserLogado);
            //modelProposta.setUserLogadoId(Integer.parseInt(idUserLogado));

            
            //Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)));
            //modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
            //modelLogin.setRendamensal(Double.valueOf(rendamensal));

            ///usuarioLogado = filterAutenticacao.usuarioLogado;
            
            DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();

            ///userLogadoId = daoUsuarioRepository.consultaUsuarioLogadoId(usuarioLogado);

            if (userLogadoId > 0) {
		

                if (modelProposta.isNovo()) {
                    msg = "Gravado com sucesso!";
                } else {
                    msg = "Atualizado com sucesso!";
                }
                
                List<ModelProposta> lista = daoPropostaRepository.gravarProposta(modelProposta, userLogadoId);
                
                List<ModelProposta> listaCirurgia = daoPropostaRepository.consultaCirurgia(Integer.parseInt(idCir), usuarioLogado, userLogadoId);

                List<ModelProposta> listaProposta = daoPropostaRepository.consultaProposta(Integer.parseInt(idCir), usuarioLogado, userLogadoId);

                request.setAttribute("msg", "Cirurgia pesquisada");
                request.setAttribute("lista", lista);
                request.setAttribute("listaCirurgia", listaCirurgia);
                request.setAttribute("listaProposta", listaProposta);
                request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);

            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }

    }
    
}


