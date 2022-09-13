<%-- 
    Document   : usuario
    Created on : 15/04/2022, 10:04:34
    Author     : Usuario
--%>

<%@page import="java.util.List"%>
<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html lang="en">

    <jsp:include page="head.jsp" />

    <body>

        <!-- Pre-loader end -->
        
        <%
           String nome, perfil;
           List<ModelLogin> lista = (List<ModelLogin>)request.getAttribute("lista");

           if (lista != null){
               for (ModelLogin ml: lista){
                   nome = ml.getNome();
                   perfil = ml.getPerfil();
                   out.print(ml.getNome() + "<br/>");
               }
           }
       %>

        <jsp:include page="theme-loader.jsp"></jsp:include>

            <div id="pcoded" class="pcoded">
                <div class="pcoded-overlay-box"></div>
                <div class="pcoded-container navbar-wrapper">

                <jsp:include page="navbar.jsp"></jsp:include>

                <jsp:include page="navbarmainmenu.jsp"></jsp:include>

                    <div class="pcoded-content">
                        <!-- Page-header start -->

                    <jsp:include page="page-header.jsp"></jsp:include>

                        <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                    <div class="card-block">
                                                        <h4 class="sub-title">Cadastro Usuário</h4>

                                                        <form class="form-material" 
                                                             action="<%=request.getContextPath()%>/ServletUsuarioController" method="post" id="formUser">

                                                        <input type="hidden" name="acao" id="acao" value="">
                                                    
                                                    <% if (lista != null){ %>   
                                                        
                                                        <% for (ModelLogin ml: lista){ %>  

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" id="id" class="form-control"
                                                                   readonly="readonly" value="<%=ml.getId()%>" /> <span
                                                                   class="form-bar"></span> <label class="float-label">ID:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="nome" id="nome"
                                                                   class="form-control" required="required"
                                                                          value="<%=ml.getNome()%>" />
                                                            <span
                                                                   class="form-bar"></span> <label class="float-label">Nome:</label>
                                                        </div>

                                                            
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="email" name="email" id="email"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="<%=ml.getEmail()%>" /> <span
                                                                   class="form-bar"></span> <label class="float-label">Email:</label>
                                                        </div>
                                                        
                                                        
                                                                   
                                                        <div class="form-group form-default form-static-label">
                                                            <select class="form-control"
                                                                    aria-label="Default select example" name="perfil">
                                                                
                                                                <option disabled="disabled">[Selecione o Perfil]</option>
                                                                
                                                                    <% if (ml.getPerfil().equals("ADMIN")) { %>
                                                                        <option value = "ADMIN"
                                                                            <% if (ml.getPerfil().equals("ADMIN")) {   
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            }%>>Admin</option>
                                                                        <%}%>
                                                                     
                                                                    <% if (ml.getPerfil().equals("FORNECEDOR")) { %>
                                                                        <option value = ml.getPerfil()
                                                                            <% if (ml.getPerfil().equals("FORNECEDOR")) {   
                                                                            out.print(" ");
                                                                            out.print("selected=\"selected\"");
                                                                            out.print(" ");
                                                                        }%>>Fornecedor</option>
                                                                    <%}%>
                                                                    
                                                                    <% if (ml.getPerfil().equals("COMSEDER")) { %>
                                                                        <option value = ml.getPerfil()
                                                                            <% if (ml.getPerfil().equals("COMSEDER")) {   
                                                                            out.print(" ");
                                                                            out.print("selected=\"selected\"");
                                                                            out.print(" ");
                                                                        }%>>Comseder</option>
                                                                    <%}%>  
                                                                            
                                                                <option value="ADMIN"
                                                                        <%ModelLogin modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("ADMIN")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            }%>>Admin</option>
                                                                
                                                                <option value="FORNECEDOR"
                                                                        <%modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("Fornecedor")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            }%>>Fornecedor</option>

                                                                <option value="COMSEDER"
                                                                        <%modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("COMSEDER")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                        }%>>Comseder</option>
                                                                
                                                                        
                                                            </select> 
                                                            <label class="float-label">Perfil:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="login" id="login"
                                                                   class="form-control" required="required" autocomplete="off"
                                                                   value="<%=ml.getLogin()%>" /><span
                                                                   class="form-bar"></span> <label class="float-label">Login:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="senha" id="senha"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="<%=ml.getSenha()%>" /> <span
                                                                   class="form-bar"></span> <label class="float-label">Senha:</label>
                                                        </div>
                                                                   
                                                        <%}%>  

                                                    <%}else {%>    
                                                        
                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" id="id" class="form-control"
                                                                   readonly="readonly" value="" /> <span
                                                                   class="form-bar"></span> <label class="float-label">ID:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="nome" id="nome"
                                                                   class="form-control" required="required"
                                                                          value="" />
                                                            <span
                                                                   class="form-bar"></span> <label class="float-label">Nome:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="email" name="email" id="email"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="" /> <span
                                                                   class="form-bar"></span> <label class="float-label">Email:</label>
                                                        </div>
                                                        
                                                        
                                                                   
                                                            <select class="form-control"
                                                                    aria-label="Default select example" name="Fornecedor">
                                                                
                                                                <option disabled="disabled">[Selecione o Fornecedor]</option>
                                                                
                                                                        <option value = "ADMIN"
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            >Admin</option>
                                                                     
                                                                        <option value = ml.getPerfil()
                                                                            out.print(" ");
                                                                            out.print("selected=\"selected\"");
                                                                            out.print(" ");
                                                                        >Fornecedor</option>
                                                                    
                                                                        <option value = ml.getPerfil()
                                                                            out.print(" ");
                                                                            out.print("selected=\"selected\"");
                                                                            out.print(" ");
                                                                        >Comseder</option>
                                                                            
                                                                <option value="ADMIN"
                                                                        <%ModelLogin modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("ADMIN")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            }%>>Admin</option>
                                                                
                                                                <option value="FORNECEDOR"
                                                                        <%modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("Fornecedor")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                            }%>>Fornecedor</option>

                                                                <option value="COMSEDER"
                                                                        <%modellogin = (ModelLogin) request.getAttribute("modellogin");

                                                                            if (modellogin != null && modellogin.getPerfil().equals("COMSEDER")) {
                                                                                out.print(" ");
                                                                                out.print("selected=\"selected\"");
                                                                                out.print(" ");
                                                                        }%>>Comseder</option>
                                                                
                                                                        
                                                            </select> 
                                                            <label class="float-label">Perfil:</label>
                                                        

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="login" id="login"
                                                                   class="form-control" required="required" autocomplete="off"
                                                                   value="" /><span
                                                                   class="form-bar"></span> <label class="float-label">Login:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="senha" id="senha"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="" /> <span
                                                                   class="form-bar"></span> <label class="float-label">Senha:</label>
                                                        </div>
                                                    
                                                    <%}%>
                                                    
                                                    
                                                        <button type="button"
                                                                class="btn btn-primary waves-effect waves-light"
                                                                onclick="limpa();">Novo</button>
                                                        <button class="btn btn-success waves-effect waves-light">Salvar</button>
                                                        <button type="button"
                                                                class="btn btn-info waves-effect waves-light"
                                                                onclick="criarDelete();">Excluir</button>
                                                        <a href="<%= request.getContextPath() %>/principal/pesquisarUsuario.jsp" class="btn btn-secondary waves-effect waves-light">Pesquisar</a>
                                                        
                                                    </form>
                                                                   
                                                </div>
                                            </div>
                                        </div>

                                        <span>${msg }</span>
                                        <!-- <span id="msg">${msg }</span> -->
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="javascriptfile.jsp"></jsp:include>

        <div class="modal fade" id="exampleModalUsuario" tabindex="-1"
             role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Nome"
                                   aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-success" type="button"
                                        onclick="buscarUsuario();">Buscar</button>
                            </div>
                        </div>

                        <div style="height: 300px; overflow: scroll;">
                            <table class="table" id="tabelaResultados">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Ver</th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                        <span id="totalResultados"></span>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary"
                                data-dismiss="modal">Fechar</button>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">

            $("#rendamensal").maskMoney({showSymbol: true, symbol: "R$ ", decimal: ",", thousands: "."});

            const formatter = new Intl.NumberFormat('pt-BR', {
                currency: 'BRL',
                minimumFractionDigits: 2
            });

            $("#rendamensal").val(formatter.format($("#rendamensal").val()));

            $("#rendamensal").focus();

            var dataNascimento = $("#dataNascimento").val();

            if (dataNascimento != null && dataNascimento != '') {

                var dateFormat = new Date(dataNascimento);

                $("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR', {timeZone: 'UTC'}));

            }


            $("#nome").focus();

            $(function() {
                $("#dataNascimento").datepicker({
                    dateFormat: 'dd/mm/yy',
                    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
                    dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
                    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
                    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                    nextText: 'Próximo',
                    prevText: 'Anterior'
                });
            });

            $("#cep").keypress(function(event) {
                return /\d/.test(String.fromCharCode(event.keyCode));
            });

            $("#numero").keypress(function(event) {
                return /\d/.test(String.fromCharCode(event.keyCode));
            });

            function pesquisaCep() {
                var cep = $("#cep").val();
                $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function(dados) {
                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#cep").val(dados.cep);
                        $("#logradouro").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        $("#localidade").val(dados.localidade);
                        $("#uf").val(dados.uf);
                    } //end if.
                    else {
                        //CEP pesquisado não foi encontrado.
                        //limpa_formulário_cep();
                        alert("CEP não encontrado.");
                    }
                });
            }


            function verEditar(id) {

                var urlAction = document.getElementById('formUser').action;

                window.location.href = urlAction + '?acao=buscarEditar&id=' + id;

            }

           

            function limpa() {
                document.getElementById('id').value = "";
                document.getElementById('nome').value = "";
                document.getElementById('email').value = "";
                document.getElementById('login').value = "";
                document.getElementById('senha').value = "";

                document.getElementById('cep').value = "";
                document.getElementById('logradouro').value = "";
                document.getElementById('bairro').value = "";
                document.getElementById('localidade').value = "";
                document.getElementById('uf').value = "";
                document.getElementById('numero').value = "";
            }

            function criarDelete() {

                if (confirm("Deseja realmente deletar os dados?")) {
                    document.getElementById('formUser').method = "get";
                    document.getElementById('acao').value = "deletar";
                    document.getElementById('formUser').submit();
                }
            }

            function criarDeleteComAjax() {
                if (confirm('Deseja realmente excluir os dados?')) {
                    var urlAction = document.getElementById('formUser').action;
                    var id = document.getElementById("id").value;
                    $.ajax({
                        method: "get",
                        url: urlAction,
                        data: "id=" + id + "&acao=deletar-ajax",
                        success: function(response) {
                            limparForm();
                            document.getElementById('msg').textContent = response;
                        }
                    }).fail(
                        function(xhr, status, errorThrown) {
                            alert('Erro ao deletar o usuário por ID: '
                                    + xhr.responseText);
                    });
                }
            }

            function limparForm() {

                var elementos = document.getElementById('formUser').elements; // Retorna os elementos html dentro do form

                for (p = 0; p < elementos.lenght; p++) {
                    elementos[p].value = '';
                }
            }
        </script>

    </body>

</html>

