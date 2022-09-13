<%-- 
    Document   : usuario
    Created on : 15/04/2022, 10:04:34
    Author     : Usuario
--%>

<%@page import="model.ModelProposta"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
//<%List<ModelProposta> opme = (List<ModelProposta>)request.getAttribute("listaProposta");%>

<!DOCTYPE html>
<html lang="en">

    <jsp:include page="head.jsp" />

    <body>

        <!-- Pre-loader end -->

        <%
           int codOpme=0, codCir=0, codProp=0, qtd=0, idOpme=0; 
           Double desconto=0.0, precoUnit=0.0;
           String matricula, nomeBenef, procedimento, nomeUser, fornecedor, tuss="", comercial="", unidade="", fabricante="";
         
           List<ModelProposta> listaCirurgia = (List<ModelProposta>)request.getAttribute("listaCirurgia");

           if (listaCirurgia != null){
               for (ModelProposta ml: listaCirurgia){
                   //codProp = ml.getId_prop();
                   codCir = ml.getId_cir();
                   codOpme = ml.getId_opme();
                   nomeUser = ml.getNomeUser();
                   matricula = ml.getMatricula();
                   nomeBenef = ml.getNomeBenef();
                   procedimento = ml.getProcedimento();
                   desconto = ml.getDesconto();
               }
           }
           List<ModelProposta> listaProposta = (List<ModelProposta>)request.getAttribute("listaProposta");

           if (listaProposta != null){
               for (ModelProposta mlp: listaProposta){
                   tuss = mlp.getTuss();
                   comercial = mlp.getComerc();
                   unidade = mlp.getUnidade();
                   qtd = mlp.getQtd();
                   precoUnit = mlp.getUnitario();
               }
           }
       %>

       <style>
         
            #tabela {
                margin-top: 30px;
                border-collapse: collapse;
                margin-bottom: 80px;
            }

            #tabela th {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
                background-color: #66bbff;
                color: #fff;
            }

            #tabela td {
               border: 1px solid #ddd;
               padding: 10px; 
            }
           
            
            #tabela{
              width:80%;
              margin:0 auto;
              border:0;
              box-shadow: 0 5px 30px darkgrey;
              border-spacing: 0;
            }

            #tabela thead th{
              font-weight: bold;
              background-color: black;
              color:white;

              padding:5px 10px;
            }

            #tabela tr td{
              padding:5px 10px;
              text-align: center;

              cursor: pointer; /**importante para não mostrar cursor de texto**/
            }

            #tabela tr td:last-child{
              text-align: right;
            }

            /**Cores**/
            #tabela tr:nth-child(odd){
              background-color: #eee;
            }
            
            /**Cor quando passar por cima**/
            #tabela tr:hover td{
              background-color: #feffb7;
            }
            
            /**Cor quando selecionado**/
            /*
            #minhaTabela tr.ativo td{
              background-color: #aff7ff;
            }
            button#visualizarDados{
              background-color: white;
              border: 1px solid black;
              width:50%;
              margin: 10px auto;
              padding:10px 0;
              display: block;
              color: black;
            }
            
            .ativo {
                background-color: green;
                color: white;
            }
            */
       </style>
       
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
                                                    <h4 class="sub-title">Proposta de fornecedor</h4>

                                                    <form class="form-material" 
                                                         action="<%=request.getContextPath()%>/ServletProposta" method="post" id="formUser">

                                                        <input type="hidden" name="acao" id="acao" value="">

                                                        <% if (listaCirurgia != null){ %>   

                                                            <% for (ModelProposta ml: listaCirurgia){ %>
                                                            
                                                              <div class="row">
                                                                  
                                                                <div class="col-md-3">  

                                                                    <div class="form-group form-default form-static-label form-group float-row">
                                                                        <input type="text" name="codProp" id="codProp" class="form-control"
                                                                               readonly="readonly" value="<%=ml.getId_prop()%>" /> <span
                                                                               class="form-bar"></span> <label class="float-label">ID PROPOSTA:</label>
                                                                    </div>

                                                                </div> 
                                                                
                                                                <div class="col-md-3">     

                                                                    <div class="form-group form-default form-static-label form-group float-row">
                                                                        <input type="text" name="codCir" id="codCir" class="form-control"
                                                                               readonly="readonly" value="<%=ml.getId_cir()%>" /> <span
                                                                               class="form-bar"></span> <label class="float-label">ID CIRURGIA:</label>
                                                                    </div>

                                                                </div>                   

                                                                <div class="col-md-3">                  

                                                                <div class="form-group form-default form-static-label">
                                                                    <input type="text" name="matricula" id="matricula"
                                                                           class="form-control" required="required" readonly="readonly"
                                                                                  value="<%=ml.getMatricula()%>" />
                                                                    <span class="form-bar"></span> <label class="float-label">Matrícula:</label>
                                                                </div>

                                                                </div> 

                                                                <div class="col-md-3">     

                                                                <div class="form-group form-default form-static-label form-group row">
                                                                    <input type="text" name="nomeBenef" id="nomeBenef"
                                                                           class="form-control" required="required" readonly="readonly"
                                                                           autocomplete="off" value="<%=ml.getNomeBenef()%>" /> <span
                                                                           class="form-bar"></span> <label class="float-label">Nome beneficiário:</label>
                                                                </div>

                                                                </div> 

                                                              </div> 

                                                                <div class="row">             

                                                                    <div class="col-md-12">

                                                                    <div class="form-group form-default form-static-label">
                                                                        <input type="text" name="procedimento" id="procedimento"
                                                                               class="form-control col-sm-12" required="required" autocomplete="off"
                                                                               readonly="readonly" value="<%=ml.getProcedimento()%>" /><span
                                                                               class="form-bar"></span> <label class="float-label">Procedimento principal:</label>
                                                                    </div>

                                                                    </div> 

                                                                </div>            

                                                                <div class="row"> 

                                                                    <div class="col-md-10">  

                                                                        <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="nomeUser" id="nomeUser"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="<%=ml.getNomeUser()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Fornecedor:</label>
                                                                        </div>

                                                                    </div> 
                                                                                  
                                                                    <div class="col-md-2">  

                                                                        <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="desconto" id="desconto"
                                                                                   class="form-control" required="required"
                                                                                   autocomplete="off" value="<%=ml.getDesconto()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Desconto:</label>
                                                                        </div>

                                                                    </div>           

                                                                </div>
                                                                                   
                                                                <div class="container">

                                                                <div class="row">

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="tuss" id="tuss"
                                                                                   class="form-control" required="required"
                                                                                   autocomplete="off" value="<%=ml.getTuss()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">TUSS:</label>
                                                                        </div>

                                                                    </div>    


                                                                     <div class="col-md-10">   

                                                                          <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="comercial" id="comercial"
                                                                                   class="form-control" required="required" autocomplete="off"
                                                                                   value="<%=ml.getComerc()%>" /><span
                                                                                   class="form-bar"></span> <label class="float-label">Nome comercial:</label>
                                                                        </div>

                                                                     </div>

                                                                </div>

                                                                <div class="row">  

                                                                    <div class="col-md-2">     

                                                                    <div class="form-group form-default form-static-label float-left">
                                                                        <input type="text" name="unidade" id="unidade"
                                                                               class="form-control" required="required"
                                                                               autocomplete="off" value="<%=ml.getUnidade()%>" /> <span
                                                                               class="form-bar"></span> <label class="float-label">Unidade:</label>
                                                                    </div>

                                                                    </div>    


                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="quantidade" id="quantidade"
                                                                                   class="form-control" required="required"
                                                                                          value="<%=ml.getQtd()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Quant:</label>
                                                                        </div>

                                                                    </div> 

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="preco" id="preco"
                                                                                   class="form-control" required="required"
                                                                                   autocomplete="off" value="<%=ml.getUnitario()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Preço:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-3">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="fabricante" id="fabricante"
                                                                                   class="form-control" required="required"
                                                                                          value="<%=ml.getFab()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. Fabricante:</label>
                                                                        </div>

                                                                    </div>     

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="anvisa" id="anvisa"
                                                                                   class="form-control" required="required"
                                                                                   autocomplete="off" value="<%=ml.getAnvisa()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. ANVISA:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="codOpme" id="codOpme"
                                                                                   class="form-control" required="required"
                                                                                          value="<%=ml.getId_opme()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">ID OPME:</label>
                                                                        </div>

                                                                    </div>    

                                                                </div>     

                                                            </div>
                                                            
                                                            <%}%> 
                                                            
                                                            <% if (listaProposta != null){ %> 
                                                            
                                                            <% for (ModelProposta mlp: listaProposta){ %>
                                                            
                                                                <div class="row">

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="tuss" id="tuss"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="<%=mlp.getTuss()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">TUSS:</label>
                                                                        </div>

                                                                    </div>    

                                                                     <div class="col-md-10">   

                                                                          <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="comercial" id="comercial"
                                                                                   class="form-control" required="required" autocomplete="off"
                                                                                   readonly="readonly" value="<%=mlp.getComerc()%>" /><span
                                                                                   class="form-bar"></span> <label class="float-label">Nome comercial:</label>
                                                                        </div>

                                                                     </div>

                                                                </div>

                                                                <div class="row">  

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="" id="unidade"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="<%=mlp.getUnidade()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Unidade:</label>
                                                                        </div>

                                                                    </div>    


                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="quantidade" id="quantidade"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   value="<%=mlp.getQtd()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Quant:</label>
                                                                        </div>

                                                                    </div> 

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="preco" id="preco"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="<%=mlp.getUnitario()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Preço:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-3">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="fabricante" id="fabricante"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   value="<%=mlp.getFab()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. Fabricante:</label>
                                                                        </div>

                                                                    </div>     

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="anvisa" id="anvisa"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="<%=mlp.getAnvisa()%>" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. ANVISA:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="idOpme" id="idOpme"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                          value="<%=mlp.getId_opme()%>" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">ID OPME:</label>
                                                                        </div>

                                                                    </div>    

                                                                </div>     

                                                            </div>
                                                                            
                                                            <% if (listaProposta != null){ %> 
                                                        
                                                            <table id="tabela">
                                                                <thead>
                                                                    <tr>
                                                                        <th>ID</th>
                                                                        <th>ID CIR</th>
                                                                        <th>Código TUSS</th>
                                                                        <th>Nome comercial do material</th>
                                                                        <th>Quantidade</th>
                                                                        <th>Preço unitário</th>
                                                                    </tr> 
                                                                </thead>

                                                                <tbody>
                                                                    <%for (int i = 0;i < listaProposta.size(); i++) {%>
                                                                        <tr style="  cursor: pointer;">
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getId_prop()%></td>
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getId_cir()%></td>
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getTuss()%></td>
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getComerc()%></td>
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getQtd()%></td>
                                                                            <td style="  cursor: pointer;" id="<%=listaProposta.get(i).getId_prop()%>"><%=listaProposta.get(i).getUnitario()%></td>
                                                                        </tr>
                                                                <style>
                                                                    #tabela {
                                                                        margin-bottom: 30px;
                                                                    }
                                                                </style>
                                                                        <script>
                                                                            var idProp="";
                                                                            var td = document.querySelectorAll('td');

                                                                            td.forEach((el) => {
                                                                                el.addEventListener('click', meuID);
                                                                            });

                                                                            function meuID(e) {
                                                                                idProp = e.currentTarget.id;
                                                                                visualizarDados(idProp);
                                                                                //console.log(e.currentTarget.id);
                                                                        //alert(idProp);
                                                                            }
                                                                            function visualizarDados(id, ic) {
                                                                                var urlAction = document.getElementById('formUser').action;

                                                                                window.location.href = urlAction + '?acao=visualizarDados&id=' + id;
                                                                            }
                                                                        </script>
                                                                    <%}%>
                                                                </tbody>
                                                            </table> 
                                                           <!-- <a href="<%= request.getContextPath() %>/ServletProposta"  
                                                           class="btn btn-primary waves-effect waves-light"
                                                           onclick="visualizarDados(idProp);">Visualizar dados</a>
                                                                <td><button type="button" onclick="capturaLinha();" id="visualizar">Visualizar Dados</button></td> -->
                                                           <!-- <button type="button"
                                                                class="btn btn-primary waves-effect waves-light"
                                                                onclick="visualizarDados();">Visualiza dados</button> -->

                                                        <%}%>
                                                        
                                                        <%}%>  
                                                        
                                                        <%}%>  

                                                        <%}else {%> 

                                                            <div class="container">

                                                                <div class="row">

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label form-group float-row">
                                                                            <input type="text" name="idOrc" id="idOrc" class="form-control"
                                                                                   readonly="readonly" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">ID ORÇAMENTO:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label form-group float-row">
                                                                            <input type="text" name="idCir" id="idCir" class="form-control"
                                                                                   readonly="readonly" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">ID CIRURGIA:</label>
                                                                        </div>

                                                                    </div>        

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="matricula" id="matricula"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Matrícula:</label>
                                                                        </div>

                                                                    </div> 

                                                                    <div class="col-md-3">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="nome" id="nome"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                          value="" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Nome:</label>
                                                                        </div>

                                                                    </div>

                                                                </div>

                                                                <div class="row">

                                                                    <div class="col-md-12">     
                                                                        <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="procedimento" id="procedimento"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Procedimento principal:</label>
                                                                        </div>

                                                                    </div>

                                                                </div> 

                                                                <div class="row">

                                                                    <div class="col-md-10">   

                                                                      <div class="form-group form-default form-static-label">
                                                                        <input type="text" name="fornecedor" id="fornecedor"
                                                                               class="form-control" required="required" autocomplete="off"
                                                                               readonly="readonly" value="" /><span
                                                                               class="form-bar"></span> <label class="float-label">Fornecedor:</label>
                                                                        </div>

                                                                   </div>   

                                                                    <div class="form-group form-default form-static-label">

                                                                    </div>

                                                                    <div class="col-md-2">     
                                                                        <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="desconto" id="desconto"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" />  <span
                                                                                   class="form-bar"></span> <label class="float-label">Desconto:</label>
                                                                        </div>

                                                                    </div>

                                                                </div>  

                                                                <div class="row">

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="tuss" id="tuss"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">TUSS:</label>
                                                                        </div>

                                                                    </div>    


                                                                     <div class="col-md-10">   

                                                                          <div class="form-group form-default form-static-label">
                                                                            <input type="text" name="comercial" id="comercial"
                                                                                   class="form-control" required="required" autocomplete="off"
                                                                                   readonly="readonly" value="" /><span
                                                                                   class="form-bar"></span> <label class="float-label">Nome comercial:</label>
                                                                        </div>

                                                                     </div>

                                                                </div>

                                                                <div class="row">  

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="unidade" id="unidade"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Unidade:</label>
                                                                        </div>

                                                                    </div>    


                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="quantidade" id="quantidade"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                          value="" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Quant:</label>
                                                                        </div>

                                                                    </div> 

                                                                    <div class="col-md-2">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="preco" id="preco"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Preço:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-3">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="fabricante" id="fabricante"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                          value="" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. Fabricante:</label>
                                                                        </div>

                                                                    </div>     

                                                                    <div class="col-md-3">     

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="anvisa" id="anvisa"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                   autocomplete="off" value="" /> <span
                                                                                   class="form-bar"></span> <label class="float-label">Cód. ANVISA:</label>
                                                                        </div>

                                                                    </div>    

                                                                    <div class="col-md-1">   

                                                                        <div class="form-group form-default form-static-label float-left">
                                                                            <input type="text" name="idOpme" id="idOpme"
                                                                                   class="form-control" required="required" readonly="readonly"
                                                                                          value="" />
                                                                            <span
                                                                                   class="form-bar"></span> <label class="float-label">ID OPME:</label>
                                                                        </div>

                                                                    </div>    

                                                                </div>     

                                                            </div> 
                                                        

                                                        <%}%>

                                                        <button type="button"
                                                                class="btn btn-primary waves-effect waves-light"
                                                                onclick="limpa();">Novo</button>
                                                        <button class="btn btn-success waves-effect waves-light">Salvar</button>
                                                        <button type="button"
                                                                class="btn btn-info waves-effect waves-light"
                                                                onclick="criarDelete();">Excluir</button>
                                                        <a href="<%= request.getContextPath() %>/fornecedor/pesquisarCirurgia.jsp" 
                                                           class="btn btn-secondary waves-effect waves-light">Pesquisar</a>

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
            
            function Formatar(valor)
            {
                const v = ((valor.replace(/\D/g, '') / 100).toFixed(2) + '').split('.');

                const m = v[0].split('').reverse().join('').match(/.{1,3}/g);

                for (let i = 0; i < m.length; i++)
                    m[i] = m[i].split('').reverse().join('') + '.';

                const r = m.reverse().join('');

                return r.substring(0, r.lastIndexOf('.')) + ',' + v[1];
             }
            
            function atacado(i) {
            //ADD dados para a másrcara
                var decimais = 2;
              var separador_milhar = '.';
              var separador_decimal = ',';

              var decimais_ele = Math.pow(10, decimais);
              var thousand_separator = '$1'+separador_milhar;
              var v = i.value.replace(/\D/g,'');
                v = (v/decimais_ele).toFixed(decimais) + '';
              var splits = v.split(".");
                var p_parte = splits[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, thousand_separator);
              (typeof splits[1] === "undefined") ? i.value = p_parte : i.value = p_parte+separador_decimal+splits[1];

            }
            
            $("#minhaTabela > tbody > tr").on("click", function (e) {
                $(this).siblings().removeClass("ativo");
                $(this).toggleClass("ativo");
            }    
          
            function visualizarDados(id, ic) {
                alert("Aqui");
                var urlAction = document.getElementById('formUser').action;

                window.location.href = urlAction + '?acao=visualizarDados&id=' + id;
            }

            var btnVisualizar = document.getElementById("visualizar");
            btnVisualizar.addEventListener("click", function(){
              var selecionados = tabela.getElementsByClassName("ativo");
              console.log(selecionado)
              //Verificar se eestá selecionado
              if(selecionados.length < 1){
                alert("Selecione pelo menos uma linha");
                return false;
              }
              
              

              var dados = "";

              for(var i = 0; i < selecionados.length; i++){
                var selecionado = selecionados[i];
                selecionado = selecionado.getElementsByTagName("td");
                dados += "idOrc: " + selecionado[0].innerHTML + " - tuss: " + selecionado[1].innerHTML + " - comercial " + selecionado[2].innerHTML + 
                       " - quantidade " +  selecionado[3].innerHTML + " - preco " +  selecionado[4].innerHTML +  "\n";
              }

              alert(dados);
            });
            
            $("#preco").maskMoney({showSymbol: true, symbol: "R$ ", decimal: ",", thousands: "."});

            const formatter = new Intl.NumberFormat('pt-BR', {
                currency: 'BRL',
                minimumFractionDigits: 2
            });

            $("#preco").val(formatter.format($("#preco").val()));

            $("#preco").focus();

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
                document.getElementById('codProp').value = "0";
                document.getElementById('tuss').value = "";
                document.getElementById('comercial').value = "";
                document.getElementById('unidade').value = "";
                document.getElementById('quantidade').value = "";
                document.getElementById('preco').value = "";
                document.getElementById('fabricante').value = "";
                document.getElementById('anvisa').value = "";
                document.getElementById('codOpme').value = "";
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

