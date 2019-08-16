<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recommended</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function(){
  		$('[data-toggle="popover"]').popover();   
		});
	</script>
 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>

<body>
<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
<div class="recommended-img">
<div class="container" style="margin-top:5%;">
	<div class="row">
		<div class="col-sm-2 col-md-2">
		</div>


		<div class="col-sm-8 col-md-8">
			<div class="recommendedcard" >
				<div class="card-header" id="card-header-recommended">
						
  <!-- Button to Open the Modal -->
  				<button type="button" id="recommendedbutton" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
   				<i class="fa fa-question fa-sm"></i>
  				</button>

  <!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-scrollable modal-sm">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">About the icons:</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <i class="fa fa-angle-left fa-sm">&nbsp;<strong>See the previous match</strong></i>
          <i class="fa fa-angle-right fa-sm">&nbsp;<strong>See the next match</strong></i>
          <i class="far fa-thumbs-down fa-sm">&nbsp;<strong>Don't show me this match again today</strong></i>
          <i class="far fa-thumbs-up fa-sm">&nbsp;<strong>Add this match to my favorites page</strong></i>     
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Get matched!</button>
        </div>
        
      </div>
    </div>
  </div>
 
	<h3 id="recommendedheader">${Restaurant.name} 
	
	
	<div class="card-header">	
	<i class="fa fa-star-o detailsicon fa-sm">&nbsp;<span class="favenames">
	
							
							<c:set var="iterator" value="0"/>
								<c:forEach begin="1" end="5">
								<c:choose>
								<c:when test="${iterator < avgRating}">
									<span><i class="fa fa-star" aria-hidden="true" style="color: #FFD700;"></i></span>
								</c:when>
								<c:otherwise>
									<span><i class="fa fa-star-o" aria-hidden="true" style="color: #FFD700;"></i></span>
								</c:otherwise>
								</c:choose>
								<c:set var="iterator" value="${iterator+1}"/>
								</c:forEach>
							
							</span></i>			
							</div>		
							</h3>
							
							
				</div>
					<div class="row" id="recommendedcardbody">
				
						<div class="col-sm-1 col-md-2">
							<div class="arrow">
							
							<c:if test="${hasPrevious}">
								<a href="goBack" id="thumbs"><i class="fa fa-angle-left fa-5x"></i></a>
							</c:if>
							
							</div>
						
						</div>

						<div class="col-sm-10 col-md-8" id="recommendedpic">
							<c:url var="recommendedImg" value="${Restaurant.imageUrl}" />
							<a href="details"><img id="recommendedImg" src="${recommendedImg}" class="img-fluid"/></a>
						</div>
					
						<div class="col-sm-1 col-md-2">
							<div class="arrow">	
							<a href="recommended" id="thumbs"><i class="fa fa-angle-right fa-5x"></i></a>
							</div>	
						</div>
					</div>
					
					 <div class="row" id="thumbs">
						<div class="col-sm-2 col-md-2"></div>
						<div class="col-sm-8 col-md-8" id="logo">
						<div class="logo"> 
						<a href="recommended" data-toggle="popover" data-content="No thanks!" data-placement="left" id="thumbs"><i class="far fa-thumbs-down fa-3x"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:choose>	
						<c:when test="${isLiked}">
							<c:url var="deleteAction" value="/deleteFavorite">
							<c:param name="restaurantId" value="${Restaurant.id}"/>
							</c:url>
							<a href="${deleteAction}" data-toggle="popover" data-content="Removed from favorites!" id="thumbs">
							<i class="selectedfavorite far fa-thumbs-up fa-3x"></i></a>
						</c:when>
						<c:otherwise>
							<a href="submitFavorite" data-toggle="popover" data-content="Added to favorites!" id="thumbs">
							<i class="far fa-thumbs-up fa-3x"></i></a>
						</c:otherwise>
						</c:choose>
			
			</div> 
						 


							
						</div>
						</div>	
						</div>
					</div>
				</div>
			</div>
		<div class="col-sm-2 col-md-2"></div>
	</div>
</div>
</body>
</html>
