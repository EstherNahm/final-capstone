<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Preferences</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<div class="preferences-img">

<div class="container" style="margin-top:10%;">

<div class="row">
		<div class="col-sm-3 col-md-3"></div>

		<div class="col-sm-6 col-md-6">
		<div class="preferencescard" >
		<div class="card-header" style="text-align: center;">
				<h3>Preferences</h3>
				<p class="welcomefont">Tell us what you're looking for!</p>
			</div>
			<div class="card-body">
			<c:url var="preferencesForm" value="/preferences"/>
				<form method="POST" action="${preferencesForm}">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-city"></i></span>
						</div>
						<c:choose>
							<c:when test="${user.zipcode == null}">
								<c:set var="placeholder" value="zipcode"/>
							</c:when>
							<c:otherwise>
								<c:set var="placeholder" value="${user.zipcode}"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${user.foodPreferences.size() == 0}">
								<c:set var="preference" value="type"/>
							</c:when>
							<c:otherwise>
								<c:set var="preference" value="${user.foodPreferences.get(0)}"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${user.budget > 0}">
								<c:set var="defaultSelect" value="${user.budget}"/>
							</c:when>
							<c:otherwise>
								<c:set var="defaultSelect" value="0"/>
							</c:otherwise>
						</c:choose>
						<input name="zipcode" class="form-control" placeholder="${placeholder}">
						
					</div>
					
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-utensils"></i></span>
						</div>
						<input name="foodType" class="form-control" placeholder="${preference}">
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
						</div>
						<select name="budget" class="browser-default custom-select">
							<c:choose>
								<c:when test="${defaultSelect == 5}">
									<option value="5" selected>(no budget preference)</option>
								</c:when>
								<c:otherwise>
									<option value="5">(no budget preference)</option>
								</c:otherwise>
							</c:choose>
  							<c:choose>
								<c:when test="${defaultSelect == 1}">
									<option value="1" selected>$ (under $10/person)</option>
								</c:when>
								<c:otherwise>
									<option value="1">$ (under $10/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 2}">
									<option value="2" selected>$$ ($11-30/person)</option>
								</c:when>
								<c:otherwise>
									<option value="2">$$ ($11-30/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 3}">
									<option value="3" selected>$$$ ($31-60/person)</option>
								</c:when>
								<c:otherwise>
									<option value="3" >$$$ ($31-60/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 4}">
									<option value="4" selected>$$$$ (over $61/person)</option>
								</c:when>
								<c:otherwise>
									<option value="4">$$$$ (over $61/person)</option>
								</c:otherwise>
							</c:choose>
							
						</select>
					
					</div>
					<div class="form-group">
						<a href="recommended"> <input type="submit" value="Submit" class="btn float-right login_btn_preference"></a>
					
					</div>
					<div class="card-footer">
					</div>
					
					<div class="validation">${validateZipcode}<br/>
					${validateFood}<br/>
					${validateBudget}</div>
						
				</form>
			</div>
			
		</div>
	</div>
		
		
		</div>
		</div>
		
		<div class="col-sm-3 col-md-3"></div>


</div>

</body>
</html>





<%-- <div class="preferences-text">


<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="preferencescard">
			<div class="card-header">
				<h3>Preferences</h3>
				<p>Tell us what you're looking for!</p>
			</div>
			<div class="card-body">
			<c:url var="preferencesForm" value="/preferences"/>
				<form method="POST" action="${preferencesForm}">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-city"></i></span>
						</div>
						<c:choose>
							<c:when test="${user.zipcode == null}">
								<c:set var="placeholder" value="zipcode"/>
							</c:when>
							<c:otherwise>
								<c:set var="placeholder" value="${user.zipcode}"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${user.budget > 0}">
								<c:set var="defaultSelect" value="${user.budget}"/>
							</c:when>
							<c:otherwise>
								<c:set var="defaultSelect" value="0"/>
							</c:otherwise>
						</c:choose>
						<input name="zipcode" class="form-control" placeholder="${placeholder}">
						
					</div>
					
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-utensils"></i></span>
						</div>
						<input name="foodType" class="form-control" placeholder="type">
					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend preference-icon">
							<span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
						</div>
						<select name="budget" class="browser-default custom-select">
							<c:choose>
								<c:when test="${defaultSelect == 0}">
									<option value="0" selected>Budget</option>
								</c:when>
								<c:otherwise>
									<option value="0">Budget</option>
								</c:otherwise>
							</c:choose>
  							<c:choose>
								<c:when test="${defaultSelect == 1}">
									<option value="1" selected>$ (under $10/person)</option>
								</c:when>
								<c:otherwise>
									<option value="1">$ (under $10/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 2}">
									<option value="2" selected>$$ ($11-30/person)</option>
								</c:when>
								<c:otherwise>
									<option value="2">$$ ($11-30/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 3}">
									<option value="3" selected>$$$ ($31-60/person)</option>
								</c:when>
								<c:otherwise>
									<option value="3" >$$$ ($31-60/person)</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${defaultSelect == 4}">
									<option value="4" selected>$$$$ (over $61/person)</option>
								</c:when>
								<c:otherwise>
									<option value="4">$$$$ (over $61/person)</option>
								</c:otherwise>
							</c:choose>
						</select>
					
					</div>
					<div class="form-group">
						<a href="recommended"> <input type="submit" value="Submit" class="btn float-right login_btn_preference"></a>
					
					</div>
					<div class="card-footer">
					</div>
					
					<div class="validation">${validateZipcode}<br/>
					${validateBudget}</div>
						
				</form>
			</div>
			
		</div>
	</div>
</div>
</div>
</div>
</body>
</html --%>