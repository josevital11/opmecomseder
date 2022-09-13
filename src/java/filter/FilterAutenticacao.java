/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

/**
 *
 * @author Usuario
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.net.httpserver.HttpServer;

import connection.SingleConnectionBanco;
//import dao.DaoVersionadorBanco;

//@WebFilter(urlPatterns = {"/principal/*"}) // Intercepta todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {

    private static Connection connection;

    public static String usuarioLogado;

    public FilterAutenticacao() {
    }

	// Encerra os processos quando o servidor é parado
    // Mataria os processos de conexão com banco
    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	// Intercepta toas as requisições e respostas do sistema
    // Tudo que se fizer no sistema vai ser por aqui
    // Validação de autenticação
    // Dar comit e rolback de transsações do banco
    // Validar e fazer redirecionamento de páginas
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();

            usuarioLogado = (String) session.getAttribute("nome");

            String urlParaAutenticar = req.getServletPath(); // url que está sendo acessada

            /* Validar se está logado, se não, redireciona para a tela de login */
            if (usuarioLogado == null
                    && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { /* Não está logado */

                RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
                request.setAttribute("msg", "Por favor, realize o login.");
                redireciona.forward(request, response);
                return; // Para a execução e redireciona para o login

            } else {
                chain.doFilter(request, response);
            }

            connection.commit(); // Der tudo certo, então comita as operaçoes no banco de dados

        } catch (Exception e) {
            e.printStackTrace();

            RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
            request.setAttribute("msg", e.getMessage());
            redirecionar.forward(request, response);

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        connection = SingleConnectionBanco.getConnection();
    }

}
