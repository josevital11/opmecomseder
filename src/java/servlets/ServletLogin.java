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
import model.ModelLogin;

// Controllrs sï¿½o as servlets
//@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) // Mapeamento da URL que vem da tela
public class ServletLogin extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

    private String perfil;

    public ServletLogin() {
    }

    // Recebe os dados da URL em parï¿½metros
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
            request.getSession().invalidate(); // invalida a sessï¿½o
            RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
        } else {
            doPost(request, response);
        }
    }

    // Recebe os dados enviados por um formulï¿½rio
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String url = request.getParameter("url");

        try {

            if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

                ModelLogin modellogin = new ModelLogin();
                //modellogin.setLogin(perfil);
                modellogin.setLogin(login);
                modellogin.setSenha(senha);

                if (daoLoginRepository.validarAutenticacao(modellogin)) {
                    /*Simulando login*/

                    request.getSession().setAttribute("usuario", modellogin.getLogin());
                    request.getSession().setAttribute("perfil", modellogin.getPerfil());
                    //request.getSession().setAttribute("imagemUser", modellogin.getFotouser());

                    if (url == null || url.equals(null)) {
                        if (daoLoginRepository.perfil.equals("FORNECEDOR")) {
                            url = "fornecedor/fornecedor.jsp";
                        } else {
                            url = "principal/principal.jsp";
                        }
                    }

                    RequestDispatcher redirecionar = request.getRequestDispatcher(url);
                    redirecionar.forward(request, response);

                } else {
                    RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
                    request.setAttribute("msg", "Informe o login e a senha corretamente!");
                    redirecionar.forward(request, response);
                }

            } else {
                RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
                request.setAttribute("msg", "Informe o login e a senha corretamente!");
                redirecionar.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);
        }

    }
}
