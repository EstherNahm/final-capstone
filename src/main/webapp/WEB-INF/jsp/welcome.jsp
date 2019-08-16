<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome!</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>

<div class="welcome-image">
<div class="container" style="margin-top:5%;">

<div class="row">

<div class="col-sm-1 col-md-1"></div>

<div class="col-sm-10 col-md-10">
	<div class="welcome-text">
		<h1 class="welcomefont"><a href="about" class="welcomefont" id="welcomelogo">RES·TAU·RANT TIN·DER</a></h1><br>
		<h3 class="welcomefont">Welcome to #MatchAndDine.</h3>
		<h3 class="welcomefont">Make every meal count!</h3><br>
		<h5 class="welcomefont">
			Restaurant Tinder is more than a guide.<br>
			It’s a cultural movement.<br>
			Spark your dining life.</h5><br>
	</div>
	</div>
<div class="col-sm-1 col-md-1"></div>
</div>
	
	<div class="row">
		<div class="col-sm-4 col-md-3"></div>
	
	
		<div class="col-sm-4 col-md-6">
			<div class="card">

			<div class="card-header" style="text-align: center;">
				<h3>Sign In</h3>
			</div>
		<div class="card-body">
			<c:url var="loginForm" value="/login"/>
				<form method="POST" action="${loginForm}">
				<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend welcome-icon">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input name="username" type="text" class="form-control" placeholder="username">
						
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend welcome-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="password" type="password" class="form-control" placeholder="password">
					</div>
					<div class="form-group">
						<input type="submit" value="Login" class="btn float-right login_btn">
					
					</div>
					<div class="card-footer">
						<br><br><p class="welcomefont" style="text-align: right;">
						First time here?&nbsp; <a href="register">Sign Up</a>
						</p>
				
					<p class="validation"><c:out value="${message}"/></p>
					</div>
					</form>
			
		</div>
		</div>
		</div>
			<div class="col-sm-4 col-md-3"></div>
		</div>


</div>
</div>





</body>
</html>



<%-- <div class="welcome-text">
<h1 class="welcomefont"><a href="about" class="welcomefont" id="welcomelogo">| RES·TAU·RANT |<br>
| TIN·DER |</a></h1><br>
<h3 class="welcomefont">Welcome to #MatchAndDine.</h3>
<h3 class="welcomefont">Make every meal count!</h3><br>
<h5 class="welcomefont">
	Restaurant Tinder is more than a guide.<br>
	It’s a cultural movement.<br>
	Spark your dining life.</h5><br>

<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="card">
			<div class="card-header">
				<h3>Sign In</h3>
			</div>
			<div class="card-body">
			<c:url var="loginForm" value="/login"/>
				<form method="POST" action="${loginForm}">
				<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend welcome-icon">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input name="username" type="text" class="form-control" placeholder="username">
						
					</div>
					
					
					<div class="input-group form-group">
						<div class="input-group-prepend welcome-icon">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="password" type="password" class="form-control" placeholder="password">
					</div>
					<div class="form-group">
						<input type="submit" value="Login" class="btn float-right login_btn">
					
					</div>
					<div class="card-footer">
					
				<div class="d-flex justify-content-center links">
					First time here?<a href="register">Sign Up</a>
				</div>
				
				<p class="validation"><c:out value="${message}"/></p>
				
			
			</div>
						
				</form>
			</div>
			
		</div>
	</div>
</div>
</div>
</div>

</body>
</html>
 --%>