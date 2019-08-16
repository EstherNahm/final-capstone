<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Goodbye!</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

<div class="goodbye-img">
<div class="container" style="margin-top:5%;">

<div class="row">
	<div class="col-sm-2 col-md-2"></div>
	
	<div class="col-sm-8 col-md-8">
	<div class="preferencescard" >
		<div class="card-header" style="text-align: center;">
				<h3>See you soon!</h3>
			</div>
			<div class="row" id="recommendedcardbody">
			
			<div class="col-sm-2 col-md-2"></div>
			<div class="col-sm-8 col-md-8">
			<div class="card-body">
			<c:url var="confirmationForm" value="/logout"/>
				<form method="POST" action="${confirmationForm}">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				
			<h4 class="welcomefont" style="text-align: center;">${Restaurant.name}</h4>
			<c:url var="recommendedImg" value="${Restaurant.imageUrl}" />
			<a href="details"><img id="recommendedImg" src="${recommendedImg}" class="img-fluid"/></a>
			
  				<div class="form-group" style="margin-top: 10%;">
					<i class="fa fa-pencil detailsicon fa-sm" aria-hidden="true"><label for="sel1" class="favenames">Are you still planning to dine here?</label></i>  
  				<select name="yesButton" class="form-control" id="sel1" style="width: 23%;">
    				<option value="0" >Yes</option>
    				<option value="1" >No</option>
  				</select>
			</div>
   									<button type="submit" class="btn" id="goodbyebutton">Logout</button>
							
			</form>
	</div>
			
			
			
			
			
			
			</div>
			
			
			
			
			<div class="col-sm-2 col-md-2"></div>
			
			
			</div>
		
	
	<div class="col-sm-2 col-md-2"></div>
</div>
</div>
</div>
</div>
</div>


</body>
</html>