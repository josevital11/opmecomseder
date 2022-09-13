/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.SingleConnectionBanco;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ModelLogin;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
//import util.ReportUtil;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Usuario
 */
//@WebServlet(name = "ServletUsuarioController", urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    private FilterAutenticacao filterAutenticacao = new FilterAutenticacao();

    private String usuarioLogado;

    private Long userLogadoId;
    
    private FacesContext context;
    
    private ByteArrayOutputStream baos;
    
    private InputStream stream;


    public ServletUsuarioController() throws Exception {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    //HttpServletRequest req = (HttpServletRequest) request;
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

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuario-ajax")) {

                    String nomeBusca = request.getParameter("nome");

                    List<ModelLogin> lista = daoUsuarioRepository.consultaUsuarioList(nomeBusca);
                    
                    request.setAttribute("msg", "Usuário pesquisado");
                    request.setAttribute("lista", lista);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
                    
                    /*
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(dadosJsonUser);
                    response.getWriter().write(json);
                            */
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

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
                    
                    List<ModelLogin> lista = daoUsuarioRepository.consultaUsuarioList();
                    
                    stream = this.getClass().getResourceAsStream("/report/listUsuarios.jasper");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String msg = "Operação realizada com sucesso";

            String id = request.getParameter("id");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            String perfil = request.getParameter("perfil");

            //rendamensal = rendamensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");

            ModelLogin modelLogin = new ModelLogin();

            modelLogin.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : null);
            modelLogin.setNome(nome);
            modelLogin.setEmail(email);
            modelLogin.setLogin(login);
            modelLogin.setSenha(senha);
            modelLogin.setPerfil(perfil);

            
            //Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)));
            //modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
            //modelLogin.setRendamensal(Double.valueOf(rendamensal));

            usuarioLogado = filterAutenticacao.usuarioLogado;

            userLogadoId = daoUsuarioRepository.consultaUsuarioLogadoId(usuarioLogado);

            if (userLogadoId > 0) {
		
                if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == 0) {
                    msg = "Já existe um usuário com este logim. Informe outro!";
                } else {

                    if (modelLogin.isNovo()) {
                        msg = "Gravado com sucesso!";
                    } else {
                        msg = "Atualizado com sucesso!";
                    }
                }
                
                List<ModelLogin> lista = daoUsuarioRepository.gravarUsuario(modelLogin, userLogadoId);

                request.setAttribute("msg", msg);
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }

    }

}
