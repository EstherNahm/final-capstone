<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<div class="register-img">
<div class="container" style="margin-top:10%;">

<div class="row">
		<div class="col-sm-3 col-md-2"></div>
		
		<div class="col-sm-6 col-md-5">
		<div class="registercard" >
			<div class="card-header">
				<h3 style="text-align: center;">Register</h3>
			</div>
			
		<div class="card-body">
			<c:url var="registerForm" value="/register"/>
				<form method="POST" action="${registerForm}">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input name="username" class="form-control" placeholder="username"/>
						
					</div>
					
						<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fa fa-envelope"></i></span>
						</div>
						<input name="email" class="form-control" placeholder="e-mail"/>
						
						</div>
						
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="password" type="password" class="form-control" 
						placeholder="password (min 10 chars)"/>
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="confirmPassword" type="password" class="form-control" placeholder="re-enter password"/>
					</div>
					<div class="form-group">
						<input type="submit" value="Create" class="btn float-right login_btn_register "/>
					
					</div>
					<div class="card-footer">
					</div>
						
				</form>
				<p class="validation"><c:out value="${usernameValidation}"/><br>
				<c:out value="${passwordValidation}" /></p>
			</div>
			
			
		</div>
		</div>

		<div class="col-sm-3 col-md-6"></div>

</div>



</div>
</div>



<%-- <div class="register-text">


<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="registercard">
			<div class="card-header">
				<h3>Register</h3>
			</div>
			<div class="card-body">
			<c:url var="registerForm" value="/register"/>
				<form method="POST" action="${registerForm}">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input name="username" class="form-control" placeholder="username"/>
						
					</div>
					
						<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fa fa-envelope"></i></span>
						</div>
						<input name="email" class="form-control" placeholder="e-mail"/>
						
						</div>
						
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="password" type="password" class="form-control" 
						placeholder="password (min 10 chars)"/>
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend register-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="confirmPassword" type="password" class="form-control" placeholder="re-enter password"/>
					</div>
					<div class="form-group">
						<input type="submit" value="Create" class="btn float-right login_btn_register "/>
					
					</div>
					<div class="card-footer">
					</div>
						
				</form>
				<p class="validation"><c:out value="${usernameValidation}"/><br>
				<c:out value="${passwordValidation}" /></p>
			</div>
			
		</div>
	</div>
</div>
</div>
</div>

</body>
</html> --%>