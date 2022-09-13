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
                                                        <h4 class="sub-title">Mudança de senha</h4>

                                                        <form class="form-material" 
                                                             action="<%=request.getContextPath()%>/ServletSenha" method="post" id="formUser">

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="senha" id="login"
                                                                   class="form-control" required="required" autocomplete="off"
                                                                   value="" /><span
                                                                   class="form-bar"></span> <label class="float-label">NOVA SENHA:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="confirma" id="senha"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="" /> <span
                                                                   class="form-bar"></span> <label class="float-label">CONFIRMA SENHA:</label>
                                                        </div>
                                                    
                                                    <button class="btn btn-success waves-effect waves-light">Salvar</button>
                                                        
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

