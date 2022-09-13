/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import dao.DAOLoginRepository;
import dao.DAOPropostaRepository;
import dao.DAOUsuarioRepository;
import filter.FilterAutenticacao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ModelLogin;
import model.ModelProposta;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Usuario
 */
public class ServletPropostaControllerNovo extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
    
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    private DAOPropostaRepository daoPropostaRepository = new DAOPropostaRepository();

    private FilterAutenticacao filterAutenticacao = new FilterAutenticacao();

    private String usuarioLogado;

    private Long userLogadoId;
    
    private FacesContext context;
    
    private ByteArrayOutputStream baos;
    
    private InputStream stream;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        if (userLogadoId > 0) {

            try {
                if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

                    String idUser = request.getParameter("id");

                    daoUsuarioRepository.deletarUser(idUser);

                    ModelLogin modelLogin = new ModelLogin();
                    
                    List<ModelLogin> lista = daoUsuarioRepository.consultaUsuarioLogado(modelLogin, usuarioLogado);
					// List<ModelLogin> modelLogins = (List<ModelLogin>)
                    // daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
                    request.setAttribute("msg", "Exluído com sucesso!");

                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

                    String idUser = request.getParameter("id");

                    daoUsuarioRepository.deletarUser(idUser);

                    response.getWriter().write("Exluído com sucesso!");

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarCirurgia-ajax")) {

                    int idCir = Integer.parseInt(request.getParameter("codigo_cir"));

                    //List<String> cirurgia = daoPropostaRepository.consultaCirurgiaId(idCir, usuarioLogado);
                    List<ModelProposta> lista = daoPropostaRepository.consultaCirurgiaId(idCir, usuarioLogado, userLogadoId);
                    
                    request.setAttribute("msg", "Cirurgia pesquisada");
                    //request.setAttribute("cirurgia", cirurgia);
                    request.setAttribute("lista", lista);
                    request.getRequestDispatcher("fornecedor/proposta.jsp").forward(request, response);
                    
                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

                    String id = request.getParameter("id");

                    ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, userLogadoId);

                    request.setAttribute("msg", "Usuário em edição");
                    request.setAttribute("modelLogin", modelLogin);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarCir")) { // AGUADANDO NOVA VERSï¿½O DO TOMCAT

                   // List<ModelProposta> modelProposta = DAOPropostaRepository.consultaCirurgiaId(int idCir);

                    request.setAttribute("msg", "Usuário em edição");
                    //request.setAttribute("modelLogins", modelLogins);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
                    
                    List<ModelLogin> lista = daoUsuarioRepository.consultaUsuarioList();
                    
                    stream = this.getClass().getResourceAsStream("/report/listagemUsuarios.jasper");
                    Map<String, Object> params = new HashMap<String, Object>();
                    baos = new ByteArrayOutputStream();
                    
                    net.sf.jasperreports.engine.JasperReport report = (net.sf.jasperreports.engine.JasperReport) JRLoader.loadObject(stream);
                    JasperPrint print = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(lista));
                    JasperExportManager.exportReportToPdfStream(print, baos);
                    
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(baos.size());
                    response.setHeader("Content-disposition", "inline: filename=relatorio.pdf");
                    response.getOutputStream().write(baos.toByteArray());
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    
                    context.responseComplete();

                    //request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
                    //request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(userLogadoId));
                }
					///HashedMap<String, Object> params = new HashedMap<String, Object>();
                ///params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorios") + File.separator);

					///byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", params, request.getServletContext());
                /////byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", request.getServletContext());
                //response.setHeader("Content-Disposition", "attachment; filename=arquivo.pdf");
                ////response.getOutputStream().write(reletorio);
            } catch (SQLException ex) {
                Logger.getLogger(ServletPropostaControllerNovo.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    
  


}
