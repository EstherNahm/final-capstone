
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Feedback</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="styles.css">
    
</head>
<body>
<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

<div class="feedback-img">					
<div class="container" style="margin-top:5%;">	
	
<div class="row">
	<div class="col-sm-2 col-md-2"></div>


	<div class="col-sm-8 col-md-8">
	<div class="recommendedcard">			
		<div class="card-header">
			<h3 class="welcomefont" style="text-align: center;">Welcome back!</h3>
		</div>
		<div class="card-body">			
			<h5 class="welcomefont" style="text-align: center;">${Restaurant.name}</h5>
				<div class="row">
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-8 col-md-8">
			<c:url var="recommendedImg" value="${Restaurant.imageUrl}" />
			<a href="feedback"><img id="recommendedImg" src="${recommendedImg}" class="img-fluid"/></a>
				</div>
				
				<div class="col-sm-2 col-md-2"></div>
				</div>
				
				
			
			<div class="row">
			<div class="col-sm-2 col-md-2"></div>
			<div class="col-sm-8 col-md-8">
			
			<div class="flex1">	
				<c:url var="reviewForm" value="/submit_review"/>
			<form method="POST" action="${reviewForm}" style="width: 100%;">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				
			
			
			
			<!-- Button to Open the Modal -->
<button type="button" class="btn btn-primary" id="detailbutton" style="background-color: #FFA500; margin-top: 3%;" data-toggle="modal" data-target="#myModal">
<label for="comment"><i class="fa fa-comment commenticon fa-sm"><span class="favenames">&nbsp; Leave a review</span></i></label>
     		
</button>

<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content" style="background-color: rgb(169,169,169); padding: 2%;">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title" style="margin: auto;">How did you enjoy this place?</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
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
   				 <button type="submit" class="btn commentbtn" id="detailbutton" style="margin: auto; background-color: #FFA500; margin-top: 3%;">Submit</button>
    		 </form>
 	   </div>
 	   
			</div>
		</div>
      </div>
      		<c:url var="feedbackForm" value="/feedback"/>
			<form method="POST" action="${feedbackForm}" style="width: 100%;">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
			<div class="form-group" style="margin-top: 10%;">
					<i class="fa fa-pencil detailsicon fa-sm" aria-hidden="true"><label for="sel1" class="favenames">Did you dine here?</label></i>  
  				<select name="yesButton" class="form-control" id="sel1" style="width: 25%;">
    				<option value="0" >Yes</option>
    				<option value="1" >No</option>
  				</select>
			</div>
			<button type="submit" class="btn" id="detailbutton" style="background-color: #FFA500; margin-top: 3%;">Submit</button>
			</form>
		<!-- </form> -->
	</div>
	</div>
	<div class="col-sm-2 col-md-2"></div>
	</div>
</div>
	
</div> 	
</div>		
			
<div class="col-sm-2 col-md-2"></div>



						</div>
					</div>

				</div>

</body>
</html>