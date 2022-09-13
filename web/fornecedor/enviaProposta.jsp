<%@page import="model.ModelProposta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%><%@page import="java.util.List"%>
//<%List<ModelProposta> opme = (List<ModelProposta>)request.getAttribute("listaProposta");%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp" />

<body>

	<!-- Pre-loader end -->
        
        <%
           int codCir=0; 
            
            List<ModelProposta> listaCirurgia = (List<ModelProposta>)request.getAttribute("listaCirurgia");

           if (listaCirurgia != null){
               for (ModelProposta ml: listaCirurgia){
                   codCir = ml.getId_cir();
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
                                                    <h4 class="sub-title">Envia Proposta</h4>

                                                    <form class="form-material" action="<%=request.getContextPath()%>/ServletProposta"
                                                        method="get" id="formUser">

                                                        <input type="hidden" id="acaoenviaProposta" name="acao" value="enviar">

                                                        <div class="row">

                                                            <div class="col-md-3">     

                                                                <div class="form-group form-default form-static-label form-group float-row">
                                                                    <input type="text" name="codCir" id="codCir" class="form-control"
                                                                           value="" /> <span
                                                                           class="form-bar"></span> <label class="float-label">DIGITE O CÓDGO DA CIRURGIA:</label>
                                                                </div>

                                                            </div>                   

                                                         </div>                   

                                                        <div class="form-row align-items-center">

                                                                <div class="col-auto my-1">
                                                                <!--	<button type="button" onclick="imprimirHtml();" class="btn btn-primary">Imprimir relatório</button>
                                                                        <button type="button" onclick="imprimirPdf();" class="btn btn-primary">Imprimir PDF</button>>
                                                                        <button type="button" class="btn btn-primary">Imprimir PDF</button>  -->

                                                                         <input type="submit" value="Enviar proposta" class="btn btn-primary" target="_blank">
                                                                </div>
                                                        </div>

                                                        <br><br><br>

                                                        <h4>Observação: Depois de enviada, você não poderá mais visualizá-la.</h4>

                                                    </form>	
                                                </div> 
                                            </div>                   

                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <!-- Page-body end -->
                    </div>
                    <div id="styleSelector"></div>
                </div>
            </div>
        </div>
                                                                                                        
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	<script type="text/javascript">
	
	function imprimirHtml() {
		document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';
		$("#formUser").submit();
	}
	
	function imprimirPdf() {
		document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';
		$("#formUser").submit();
		return false;
	}
	
	$( function() {
		  
		  $("#dataInicial").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Próximo',
			    prevText: 'Anterior'
			});
	} );
	
	$( function() {
		  
		  $("#dataFinal").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Próximo',
			    prevText: 'Anterior'
			});
	} );
	
	</script>

</body>

</html>
