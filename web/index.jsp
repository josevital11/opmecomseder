<%-- 
    Document   : index
    Created on : 12/04/2022, 17:23:53
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	
	<title>OPME-COMSEDER</title>
	
	<style type="text/css">
	
	form {
		position: absolute;
		top: 30%;
		left: 36%;
                width: 350px
	}
	
	h3 {
		position: absolute;
		top: 20%;
		left: 36%;
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
	
	</style>
    </head>
    <body>
        <h3>Sistema OPME-COMSEDER</h3>
	
	<form action="<%=request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	<input type="hidden" value="<%=request.getParameter("url")%> name='url'">
		<div class="col-mb-3">
			<label class="form-label">Login:</label>
                        <input class="form-control" name="login" type="text" required="required">
			<div class="invalid-feedback">
                            Obrigatório!
                        </div>
			<div class="valid-feedback">
                            Ok!
                        </div>
	    	</div>
                <br>
		
		<div class="col-mb-3">
			<label class="form-label">Senha:</label>
                        <input class="form-control" name="senha" type="password" required="required">
			<div class="invalid-feedback">
                            Obrigatorio!
                        </div>
			<div class="valid-feedback">
                            Ok!
                        </div>
	    	</div>
                <br>	
		
                <input type="submit" value="Acessar" class="btn btn-primary">
	
	</form>
	
	<h5 class="mensagem">${msg}</h5>
	
	<!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <script type="text/javascript">

        //Example starter JavaScript for disabling form submissions if there are invalid fields
        (function () {
          'use strict'

          // Fetch all the forms we want to apply custom Bootstrap validation styles to
          var forms = document.querySelectorAll('.needs-validation')

          // Loop over them and prevent submission
          Array.prototype.slice.call(forms)
            .forEach(function (form) {
              form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                  event.preventDefault()
                  event.stopPropagation()
                }

                form.classList.add('was-validated')
              }, false)
            })
        })()

    </script>	
    </body>
</html>
