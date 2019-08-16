<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Visited</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  
</head>
<body>
<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

 <div class="visited-img">

<div class="container" style="margin-top:30px">


    

	<div class="row">
		<div class="col-sm-2 col-md-2"></div>
	

    	<div class="col-sm-8 col-md-8">
     	  <div class="favoritescard">
     	  		<div class="card-header">
				<h4 id="favoritesheader">Visited</h4>
				</div>
				
				
				<div class="row">
				
				<div class="col-sm-1 col-md-1"></div>
				
				
				<div class="col-sm-10 col-md-10">
				<c:forEach var="restaurant" items="${restaurants}">
					<div class="card-header">
							<div class="card-header-favorites">
								<h5 id="favoritesheader1"><i class="fa fa-cutlery visitedicon" aria-hidden="true"><span class="welcomefont">&nbsp;${restaurant.name}</span></i></h5>
								<c:url var="deleteForm" value="/deleteVisit"/>
								<form action="${deleteForm}" method=POST>
								<input type="hidden" name="restaurantId" value="${restaurant.id}"/>
								<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
								<button type="submit" class="btn commentbtn"
													style="background-color: #D9C7B8;; width: 40px; " id="trash"><i class="fa fa-trash fa-lg" aria-hidden="true"></i></button>
								</form>
								

							</div>
						</div>
				<div class="favorites-picture">
					<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/> 
		 			<c:url var="favoritesImg" value="${restaurant.imageUrl}" />
		 			<c:url var="detailAction" value="/details">
		 			<c:param name="restaurantId" value="${restaurant.id}"/>
		 			</c:url>
					<a href="${detailAction }" class="favoritesImg mx-auto"><img id="recommendedImg" src="${favoritesImg}" class="img-fluid"/>
					</a>
				
				
					
					<div class="favorites-icon" style="margin-left: 5%;">
					
		 			<p class="welcomefont">
		 			<%-- <i class="fa fa-cutlery favoritesicon fa-sm"></i><a>&nbsp;${restaurant.name}</a><br><br> --%>
		 			<i class="fa fa-home visitedicon fa-sm"></i><a>&nbsp;${restaurant.address }</a><br><br>
		 			<i class="fa fa-phone visitedicon fa-sm"></i><a>&nbsp;${restaurant.displayPhone}</a>
		 			</p>
					
					
		<!-- 			
			Button to Open the Modal
<button type="button" class="btn btn-primary" id="visitedbutton" data-toggle="modal" data-target="#myModal">
<label for="comment"><i class="fa fa-comment commenticon fa-sm"><span class="favenames">&nbsp; Leave a review</span></i></label>
     		
</button>


The Modal
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content" style="background-color: rgb(169,169,169); padding: 2%;">

      Modal Header
      <div class="modal-header">
        <h4 class="modal-title" style="margin: auto;">How did you enjoy this place?</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      Modal body
      <div class="modal-body">
       <div class="rating" style="margin: auto;">
					<input class="stars" type="radio" value="5" name="rating" id="r1">
					 <label for="r1"></label>

 					<input class="stars" type="radio"  value="4" name="rating" id="r2">
 					<label for="r2"></label>

 					<input class="stars" type="radio" value="3" name="rating" id="r3">
 					<label for="r3"></label>

 					<input class="stars" type="radio" value="2" name="rating" id="r4">
 					<label for="r4"></label>

 					<input class="stars" type="radio" value="1" name="rating" id="r5">
 					<label for="r5"></label>
				</div>
     		 
			 </div> 
			 
			 <div class="form-group">
     			 <textarea class="form-control" rows="3" id="comment" name="text" placeholder="Enter your comment here"></textarea>
   			</div>
   				 <button type="submit" class="btn commentbtn" id="favoritebutton" style="margin: auto;">Submit</button>
    		
 	   
			</div>
		</div>
      </div> -->
   
      </div>	
      
      
      
      <div class="row" style="margin-left: 3%; margin-right: 3%;">
     
				<c:set var="currentId" value="${restaurant.id}"/>
					<c:forEach var="visitReview" items="${reviews[currentId]}">
      
      			
				
			
				<div class="col-sm-3 col-md-3" style="padding-top: 5%;">
				<p class="welcomefont"><i class="fa fa-user favoritesicon fa-sm" aria-hidden="true"></i>&nbsp;${visitReview.username}</p>
				</div>
				
	
				<div class="col-sm-8 col-md-8">
								<c:set var="iterator" value="0"/>
								<p class="welcomefont"><br><c:forEach begin="1" end="5">
								<c:choose>
								<c:when test="${iterator < visitReview.rating}">
									<i class="fa fa-star" aria-hidden="true" style="color: yellow;"></i>
								</c:when>
								<c:otherwise>
									<i class="fa fa-star-o" aria-hidden="true" style="color: yellow;"></i>
								</c:otherwise>
								</c:choose>
								<c:set var="iterator" value="${iterator+1}"/>
								</c:forEach>
								
								<br>
								
											${visitReview.review}</p>
				
				
				</div>
				</c:forEach>
			
				
				</div>
   <hr style="background-color: white; margin-top: 5%; margin-bottom: 5%;">
				
					
				
			

 		
			
			
			
		</div>
		</c:forEach>
		
		</div>
		<div class="col-sm-1 col-md-1"></div>
		
		</div>
					</div>
					
					
				</div>
				</div>
				</div>
				</div>
				</body>
				</html>
     	  		
     