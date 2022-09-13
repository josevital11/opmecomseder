/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import filter.FilterAutenticacao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ModelLogin;

// Controllrs sï¿½o as servlets
//@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) // Mapeamento da URL que vem da tela
public class ServletUsuarioControle extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    private FilterAutenticacao filterAutenticacao = new FilterAutenticacao();

    private String usuarioLogado;

    private Long userLogadoId;

    public ServletUsuarioControle() throws Exception {
        super();
    }

    // Recebe os dados da URL em parï¿½metros
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        usuarioLogado = filterAutenticacao.usuarioLogado;
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

					// List<ModelLogin> modelLogins = (List<ModelLogin>)
                    // daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
                    // List<ModelLogin> modelLogins = (List<ModelLogin>)
                    // daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
                    request.setAttribute("msg", "Exluído com sucesso!");

                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

                    String idUser = request.getParameter("id");

                    daoUsuarioRepository.deletarUser(idUser);

                    response.getWriter().write("Exluído com sucesso!");

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuario-ajax")) {

                    String nomeBusca = request.getParameter("nomeBusca");

                    // List<ModelLogin> dadosJsonUser =
                    // daoUsuarioRepository.consultaUsuarioList(nomeBusca);
                    List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, userLogadoId);

					////////ObjectMapper mapper = new ObjectMapper();
                    ////////String json = mapper.writeValueAsString(dadosJsonUser);
                    //////response.getWriter().write(json);
                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

                    String id = request.getParameter("id");

                    ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, userLogadoId);

                    request.setAttribute("msg", "Usuário em edição");
                    request.setAttribute("modellogin", modelLogin);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) { // AGUADANDO NOVA VERSï¿½O DO TOMCAT

                    List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList();

                    request.setAttribute("msg", "Usuário em edição");
                    request.setAttribute("modellogins", modelLogins);
                    request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

                } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

                    //request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
                    request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(userLogadoId));
                }
					///HashedMap<String, Object> params = new HashedMap<String, Object>();
                ///params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorios") + File.separator);

                ///byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", params, request.getServletContext());
                /////byte[] reletorio = new ReportUtil().geraRelatorioPDF(modelLogin, "rel-user-jsp", request.getServletContext());
                response.setHeader("Content-Disposition", "attachment; filename=arquivo.pdf");
                ////response.getOutputStream().write(reletorio);
            } catch (SQLException ex) {
                Logger.getLogger(ServletUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
                RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
                request.setAttribute("msg", e.getMessage());
                redirecionar.forward(request, response);
            }

            request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

        }

    }

    // Recebe os dados enviados por um formulï¿½rio
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
            ModelLogin modellogin = new ModelLogin();

            modellogin.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : null);
            modellogin.setNome(nome);
            modellogin.setEmail(email);
            modellogin.setLogin(login);
            modellogin.setSenha(senha);
            modellogin.setPerfil(perfil);

            //Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)));
            //modellogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
            //modellogin.setRendamensal(Double.valueOf(rendamensal));
            usuarioLogado = filterAutenticacao.usuarioLogado;

            userLogadoId = daoUsuarioRepository.consultaUsuarioLogadoId(usuarioLogado);

            if (userLogadoId > 0) {
			//}

                // try {
                if (daoUsuarioRepository.validarLogin(modellogin.getLogin()) && modellogin.getId() == 0) {
                    msg = "Já existe um usuário com este logim. Informe outro!";
                } else {

                    if (modellogin.isNovo()) {
                        msg = "Gravado com sucesso!";
                    } else {
                        msg = "Atualizado com sucesso!";
                    }

                }
                
                List<ModelLogin> lista = daoUsuarioRepository.gravarUsuario(modellogin, userLogadoId);

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
