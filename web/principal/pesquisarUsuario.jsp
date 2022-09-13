<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	
	<title>OPME-COMSEDER</title>
	
	<style type="text/css">
	
	form {
		position: absolute;
		top: 75%;
		left: 30%;
                width: 350px
	}
	
	h3 {
		position: absolute;
		top: 70%;
		left: 38%;
                color: forestgreen
	}
	
	.mensagem {
		position: absolute;
		top: 80%;
		left: 36%;
		color: #664d03;
    	background-color: #fff3cd;
    	border-color: #ffecb5;
	}
        
        #posicao {
            position: absolute;
		top: 90%;
		left: 5%;
                color: forestgreen
        }
	
	</style>
    </head>
    
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

<h3>Pesquisa usuário</h3>
                                                                    <div id="posicao">                                                                    
	
	<form action="<%=request.getContextPath() %>/ServletUsuarioController" method="get" class="row g-3 needs-validation" novalidate>
	<input type="hidden" value="<%=request.getParameter("url")%> name='url'">
	<input type="hidden" value="buscarUsuario-ajax" name="acao">
		<div class="col-mb-3">
                    <h4>Digite parte do nome do usuário:</h4>
                   <!-- <label class="form-label" style="color: #0053dd" font-size: 50px>Digite parte do nome do usuário:</label> -->
                    <input class="form-control" name="nome" type="text" required="required">
			<div class="invalid-feedback">
                            Obrigatório!
                        </div>
			<div class="valid-feedback">
                            Ok!
                        </div>
	    	</div>
                <br>
		
        <input type="submit" value="Pesquisar" class="btn btn-primary" style="width: 320px;">
	
	</form>
	
	<h5 class="mensagem">${msg}</h5>  
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

</body>

</html>

