<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp" />

<body>

	<!-- Pre-loader end -->

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
									<div class="col-sm-6">
										<!-- Basic Form Inputs card start -->
										<div class="card">
											<div class="card-block">
												<h4 class="sub-title">Proposta do fornecedor</h4>
												
												<form class="form-material" action="<%=request.getContextPath()%>/ServletProposta"
													method="get" id="formUser">
													
													<input type="hidden" id="acaoRelatorioImprimirTipo" name="acao" value="imprimirRelProposta">
													
													<div class="form-row align-items-center">
                                                                                                            
                                                                                                            <div class="col-mb-1">
                                                                                                                <h4>Digite o c?digo da cirurgia:</h4>
                                                                                                                <input class="form-control" name="codigo_cir" type="text" required="required">
                                                                                                                    <div class="invalid-feedback">
                                                                                                                        Obrigat?rio!
                                                                                                                    </div>
                                                                                                                    <div class="valid-feedback">
                                                                                                                        
                                                                                                                    </div>
                                                                                                            </div>
                                                                                
                                                                                                            <br><br><br>

                                                                                                            <!--
                                                                                                            
														<div class="col-sm-3 my-1">
															<label class="sr-only" for="dataInicial">Data inicial</label>
															<input value="${dataInicial}" type="text" class="form-control"
																id="dataInicial" name="dataInicial">
														</div>
														<div class="col-sm-3 my-1">
															<label class="sr-only" for="dataFinal">Data final</label>
															<input value="${dataFinal}" type="text" class="form-control" id="dataFinal" name="dataFinal">
														</div>
													-->
													
                                                                                                            <div class="col-mb-3">
														<div class="col-auto my-1">
														<!--	<button type="button" onclick="imprimirHtml();" class="btn btn-primary">Imprimir relat?rio</button>
															<button type="button" onclick="imprimirPdf();" class="btn btn-primary">Imprimir PDF</button>>
															<button type="button" class="btn btn-primary">Imprimir PDF</button>  -->
                                                                                                                        
                                                                                                                         <input type="submit" value="Visualizar relat?rio" class="btn btn-primary" target="_blank">
														</div>
                                                                                                            </div>
													</div>
												</form>	
												
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
			    dayNames: ['Domingo','Segunda','Ter?a','Quarta','Quinta','Sexta','S?bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S?b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar?o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr?ximo',
			    prevText: 'Anterior'
			});
	} );
	
	$( function() {
		  
		  $("#dataFinal").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Ter?a','Quarta','Quinta','Sexta','S?bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S?b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar?o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr?ximo',
			    prevText: 'Anterior'
			});
	} );
	
	</script>

</body>

</html>
