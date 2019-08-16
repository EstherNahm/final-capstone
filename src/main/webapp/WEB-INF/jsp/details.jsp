<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<!DOCTYPE html>
<html lang="en">
<head>
<title>Details</title>
<meta charset="utf-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"> -->
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
<!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script> -->
<!--  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script> -->


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"> -->
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script> -->
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
 -->
</head>
<body>

	<script>
function initMap() {
  // The location of Uluru ${Restaurant.latitude}, ${Restaurant.longitude}
  var uluru = {lat: ${Restaurant.latitude}, lng: ${Restaurant.longitude}};
  // The map, centered at Uluru
  var map = new google.maps.Map(
      document.getElementById('map'), {zoom: 14, center: uluru});
  // The marker, positioned at Uluru
  var marker = new google.maps.Marker({position: uluru, map: map});
}
</script>


	<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />

	<div class="details-img">
		<div class="container" style="margin-top: 5%">
			<div class="row">
				<div class="col-sm-1 col-md-1"></div>
				<div class="col-sm-6 col-md-6">
					<div class="detailscard">
						<div class="card-header">
							<div class="card-header-details">

								<h4 class="detailsarrow">
									<a class="detailslink" href="return"><i
										class="fa fa-angle-double-left" id="arrows" aria-hidden="true"></i></a>
								</h4>
								<h5 id="detailsheader">${Restaurant.name}</h5>

							</div>
						</div>

						<div class="details-picture">
							<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
							<c:url var="detailsImg" value="${Restaurant.imageUrl}" />
							<div class="detailsImg">
								<img src="${detailsImg}" class="img-fluid" id="detailedImg" />
							</div>
						</div>

						<div class="detailsreview">

							

			<%-- <c:url var="saveVisitDetailsForm" value="/saveFinalVisit"/>
			<form method="POST" action="${saveVisitDetailsForm}" style="width: 100%;">
			<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
			<div class="form-group" style="margin-top: 5%; margin-left: 5%;">
					<input type="checkbox" class="custom-control-input"
										id="customCheck" name="example1" onChange="this.form.submit()"> <label
										class="custom-control-label favenames" for="customCheck">Did you visit here?
										</label>								
			</div>

			</form> --%>
				
				<c:url var="reviewForm" value="submit_review" />
							<form method="POST" action="${reviewForm}">
								<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
								<input type="hidden" id="visitId" name="visitId" value="1">
								
			<hr style="background-color: white; margin-top: 5%; margin-bottom: 5%;">
								
								
						 		<div class="form-group">
									<!-- Button to Open the Modal -->
									<button type="button" class="btn btn-primary" id="detailbutton"
										data-toggle="modal" data-target="#myModal">
										<label for="comment"><i class="fa fa-comment commenticon fa-sm"><span
												class="favenames">&nbsp; Leave a review</span></i></label>

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
														<input class="stars" type="radio" value="5" name="rating"
															id="r1"> <label for="r1"></label> <input
															class="stars" type="radio" value="4" name="rating"
															id="r2"> <label for="r2"></label> <input
															class="stars" type="radio" value="3" name="rating"
															id="r3"> <label for="r3"></label> <input
															class="stars" type="radio" value="2" name="rating"
															id="r4"> <label for="r4"></label> <input
															class="stars" type="radio" value="1" name="rating"
															id="r5"> <label for="r5"></label>
													</div>

												</div>

												<div class="form-group">
													<textarea class="form-control" rows="3" id="comment"
														name="text" placeholder="Enter your comment here"></textarea>
												</div>
												<button type="submit" class="btn commentbtn"
													style="margin: auto;" id="detailbutton">Submit</button>
											</div>

										</div>
									</div>
								</div> 
								
							</form>
<hr style="background-color: white; margin-top: 5%; margin-bottom: 5%;">

						</div>


						<div class="details-review">
						



							<div style="color: white; margin-left: 3%;" class="detailsReview">
								
								<c:set var="currentId" value="${Restaurant.id}" />
								<div class="row">
								<c:forEach var="visitReview" items="${surveyList}">
								
									<div class="col-sm-3 col-md-3"><br>
									
						
				<p class="welcomefont"><i class="fa fa-user detailsicon fa-sm" aria-hidden="true"></i>&nbsp;${visitReview.username}
				<br>${visitReview.dateOfVisit}</p>
				<c:if test="${visitReview.isUsersReview }">
				<c:url var="deleteAction" value="/deleteReview"/>
								<form action="${deleteAction }">
								<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
								<input type="hidden" name="visitId" value="${visitReview.visitId}" />
								<button type="submit" class="btn commentbtn" style="background-color: #b2cfb2;; width: 40px; " id="trash"><i class="fa fa-trash fa-lg" aria-hidden="true"></i></button>
								</form>
								</c:if>
				</div>
				
				<div class="col-sm-1 col-md-1"></div>
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
								
												<p>${visitReview.review}</p>
												
					
					
					
				
													
																	
				</div>
								
						</c:forEach>		
								
								
								
						<div class="row">
						<div class="col-sm-8 col-md-8"></div>
						<div class="col-sm-4 col-md-4">
							<c:url var="showAllAction" value="/details"/>
								<form action="${showAlAction}" method="POST">
								<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
								<input type="hidden" name="showAllReviews" value="true" />
								<input type="hidden" name="restaurantId" value="${Restaurant.id}" />
												<button type="submit" class="btn commentbtn"
													style="margin-left: 33%;" id="detailbutton">see more reviews</button>
													</form>
													
										</div>			
									</div>		
													
													
								</div>

							</div>
						</div>





					</div>
				</div>


				<div class="col-sm-4 col-md-4">
					<div class="detailscard">
						<div class="card-header">
							<h5 id="detailsheader">Details</h5>
						</div>

						<div class="favorites-icon" id="detailscard2"
							style="margin-bottom: 5%;">

							<i class="fa fa-home detailsicon fa-sm"><a
								class="detailslink" href="${Restaurant.url}">&nbsp;${Restaurant.name}</a></i><br>
							<br> <i class="fa fa-cutlery detailsicon fa-sm"><span
								class="favenames">
								<c:forEach var="category" items="${Restaurant.categories}">
									<a>&nbsp;${category}<br/>&nbsp;&nbsp;&nbsp;</a>
								</c:forEach></span></i>
								<!-- <br> -->
							<br> <i class="fa fa-road detailsicon fa-sm"><span class="favenames">&nbsp; ${Restaurant.address}</span></i><br>
							<br><i class="fa fa-star-o detailsicon fa-sm">&nbsp;<span class="favenames">
							
							<c:set var="iterator" value="0"/>
								<c:forEach begin="1" end="5">
								<c:choose>
								<c:when test="${iterator < avgRating}">
									<span><i class="fa fa-star" aria-hidden="true" style="color: yellow;"></i></span>
								</c:when>
								<c:otherwise>
									<span><i class="fa fa-star-o" aria-hidden="true" style="color: yellow;"></i></span>
								</c:otherwise>
								</c:choose>
								<c:set var="iterator" value="${iterator+1}"/>
								</c:forEach>
								
							</span></i>
							
							<br><br><i class="fa fa-phone detailsicon fa-sm"><span
								class="favenames">&nbsp; ${Restaurant.displayPhone}</span></i><br>
							<br> 
							
							
							
							
							<i class="fa fa-clock-o detailsicon fa-sm"
								aria-hidden="true"> <span class="favenames"><strong>Hours:</strong>
									<c:forEach var="hours" items="${Restaurant.openCloseTimes}">
										<div class="hours">
											<br> ${hours}
										</div>
									</c:forEach> </span>
							</i>

						</div>
						
						
						
						<div class="detailscard2">
						
			<hr style="background-color: white; margin-top: 5%; margin-bottom: 9%;">
						
						
							<c:url var="saveForm" value="saveVisit" />
							<form method="POST" action="${saveForm}">
								<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />

								<div class="custom-control custom-checkbox"
									style="margin-bottom: .5rem;">
									<input type="checkbox" class="custom-control-input"
										id="customCheck" name="example1" onChange="this.form.submit()"> <label
										class="custom-control-label favenames" for="customCheck">Yes,
										I'm planning to eat here!</label>
								
								<!-- <button type="submit" class="btn" id="detailbutton">Submit</button> -->
								</div>
							</form>
							<br>
			<hr style="background-color: white; margin-top: 2%; margin-bottom: 5%;">
							<!--The div element for the map -->
							<div id="map"></div>
							<!-- Replace the value of the key parameter with your own API key. -->
							<script async defer
								src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBp_kJSPZVG1AAicmzxTeQ3zUdBkD06Kis&callback=initMap">

</script>

						</div>
					</div>




				</div>


				<div class="col-sm-1 col-md-1"></div>




			</div>


		</div>

	</div>

</body>
</html>


<%-- <div class="col-sm-1 col-md-1"></div> 
	

    	<div class="col-sm-10 col-md-10">
     	  <div class="detailscard">
     	  		<div class="card-header">
     	  		<div class="card-header-details">
     	  		
     	  		<h4 class="detailsarrow" ><a class="detailslink" href="recommended"><i class="fa fa-angle-double-left" id="arrows" aria-hidden="true"></i></a></h4>
					<h4 id="detailsheader">Details</h4>
					
				</div>
					
				</div>
			
				<div class="row" id="details">
     	  			<div class="col-sm-6 col-md-6">
						<div class="details-picture">
							<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/> 
		 					<c:url var="detailsImg" value="${Restaurant.imageUrl}" />
							<div class="detailsImg"><img src="${detailsImg}" class="img-fluid" id="detailedImg"/>
							</div>
						</div>
					</div>
				
					<div class="col-sm-6 col-md-6">
						<div class="favorites-icon">
		 					
		 					<i class="fa fa-home detailsicon fa-sm"><a class="detailslink" href="${Restaurant.url}">&nbsp;${Restaurant.name}</a></i><br><br>
		 					<i class="fa fa-cutlery detailsicon fa-sm"><span class="favenames">&nbsp; ${Restaurant.categories}</span></i><br><br>
		 					<i class="fa fa-road detailsicon fa-sm"><span class="favenames">&nbsp; ${Restaurant.address}</span></i><br><br>
		 					<i class="fa fa-phone detailsicon fa-sm"><span class="favenames">&nbsp; ${Restaurant.displayPhone}</span></i><br><br>
		 					
	 		 					
						<!-- 	<form>
  								<div class="custom-control custom-checkbox" style="margin-bottom: 1rem;">
   									 <input type="checkbox" class="custom-control-input" id="customCheck" name="example1">
   									 <label class="custom-control-label favenames" for="customCheck">Yes, I'm planning to eat here!</label>
 								 </div>
   									<button type="submit" class="btn" id="detailbutton">Submit</button>
							</form>  -->
		 					
						</div> 
					</div>
				</div>
	
		
		<div class="row">
			<div class="col-sm-6 col-md-6">	
			<div class="hours">
				<i class="fa fa-clock-o detailsicon fa-sm" aria-hidden="true">
		 		<span class="favenames">Hours:
		 			<c:forEach var="hours" items="${Restaurant.openCloseTimes}">
		 			 	<div class="hours" style="margin-left:15px;">
		 					<br>${hours} <br>
		 			 	</div>
		 			</c:forEach>
		 		</span>
		 		</i>
			</div>
			</div>
		
		
		<div class="col-sm-6 col-md-6">			
			<div class="flex">	
				<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>
				<c:url var="reviewForm" value="/SubmitReview"/>
			<form method="POST" action="${reviewForm}">
			<div class="form-group">
					<i class="fa fa-pencil detailsicon fa-sm" aria-hidden="true"><label for="sel1" class="favenames">Have you eaten here?</label></i>  
  				<select class="form-control" id="sel1">
    				<option>Yes</option>
    				<option>No</option>
  				</select>
			</div>

			<div class="form-group">
     		 <label for="comment"><i class="fa fa-comment detailsicon fa-sm"><span class="favenames">&nbsp; How was it?</span></i></label>
     		
     		 <div class="rating">
					<input class="stars" type="radio" name="rating" id="r1">
					 <label for="r1"></label>

 					<input class="stars" type="radio" name="rating" id="r2">
 					<label for="r2"></label>

 					<input class="stars" type="radio" name="rating" id="r3">
 					<label for="r3"></label>

 					<input class="stars" type="radio" name="rating" id="r4">
 					<label for="r4"></label>

 					<input class="stars" type="radio" name="rating" id="r5">
 					<label for="r5"></label>
				</div>
     		 
			 </div> 
			 <div class="form-group">
     			 <textarea class="form-control" rows="3" id="comment" name="text">
     			 </textarea>
   			</div>
   				 <button type="submit" class="btn" id="detailbutton">Submit</button>
    
 	   </form>
			</div>
		</div>
		
		
		
	</div>
				
				
		</div>
		</div>
		<div class="col-sm-1 col-md-1"></div>
	</div>
	</div>
	</div>
	</body>			
				
 --%>
