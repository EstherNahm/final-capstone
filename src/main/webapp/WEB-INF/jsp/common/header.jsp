<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


<!-- 
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>  -->

 


<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" href="css/site.css"></link> 


<link href="https://fonts.googleapis.com/css?family=Work+Sans&display=swap" rel="stylesheet"></link>
<link href="https://fonts.googleapis.com/css?family=Noto+Sans&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans|Open+Sans+Condensed:300&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Abel|Montserrat|Noto+Sans|Open+Sans+Condensed:300&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Abel|Noto+Sans|Open+Sans+Condensed:300&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Abel|Montserrat|Noto+Sans|Open+Sans+Condensed:300|PT+Serif&display=swap" rel="stylesheet">


</head>
<body>

<c:if test="${empty currentUser}">
<nav class="navbar navbar-dark navbar-expand-sm" style="background-image: linear-gradient(#606D7D, #171B21);">


<div class="fire">
  <a class="navbar-brand" href="about">
  <i class="fa fa-fire" aria-hidden="true" style="color: #FFA500;" id="navbar-brand"></i>Restaurant Tinder</a></div>

  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" style="background-color: #FFA500;">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="welcome">Welcome</a>
      </li>
      <li class="nav-item  hide-header">
        <a class="nav-link" href="preferences">Preferences</a>
      </li>
      <li class="nav-item  hide-header">
        <a  class="nav-link" href="getMatched">Get Matched!</a>
      </li>    
       <li class="nav-item  hide-header">
       	<a class="nav-link" href="viewFavorites">Favorites</a>
      </li>    
      <li class="nav-item  hide-header">
       	<a class="nav-link" href="feedback">Feedback</a>
      </li>    
      <li class="nav-item  hide-header">
       	<a class="nav-link" href="visited">Visited</a>
      </li>    
       <li class="nav-item hide-header">
	<a class="nav-link" href="confirmation">Logout</a>
	 </li>
       <li class="nav-item">
   <a class="nav-link" href="about">About</a>
      </li>    
    </ul>
  </div>  
</nav>
</c:if>


<c:if test="${not empty currentUser}">
<nav class="navbar navbar-dark navbar-expand-sm" style="background-image: linear-gradient(#606D7D, #171B21);">


<div class="fire">
  <a class="navbar-brand" href="about">
  <i class="fa fa-fire" aria-hidden="true" style="color: #FFA500;" id="navbar-brand"></i>Restaurant Tinder</a></div>

  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" style="background-color: #FFA500;">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav ml-auto">
      <!-- <li class="nav-item">
        <a class="nav-link" href="welcome">Welcome</a>
      </li> -->
      <li class="nav-item">
        <a class="nav-link" href="preferences">Preferences</a>
      </li>
      <li class="nav-item">
        <a  class="nav-link"href="getMatched">Get Matched!</a>
      </li>    
       <li class="nav-item">
       	<a class="nav-link" href="viewFavorites">Favorites</a>
      </li>    
      <!--  <li class="nav-item">
       	<a class="nav-link" href="feedback">Feedback</a>
      </li>     -->
      <li class="nav-item">
       	<a class="nav-link" href="visited">Visited</a>
      </li>   
       <li class="nav-item">
	<a class="nav-link" href="confirmation">Logout</a>
	 </li>
     <!--   <li class="nav-item">
   <a class="nav-link" href="about">About</a>
      </li>  -->   
    </ul>
  </div>  
</nav>
</c:if>

</body>

<%-- <c:url var="jQueryJs" value="/js/jquery.min.js" />
<c:url var="bootstrapJs" value="/js/bootstrap.min.js" />

<script src="${jQueryJs}"></script>
<script src="${bootstrapJs}"></script>

<script type="text/javascript">
	
$(document).on("scroll", function(){
	if
  ($(document).scrollTop() > 20){
	  $(".shown-header").addClass("shrink");
	}
	else
	{
		$(".shown-header").removeClass("shrink");
	}
});
</script>

</head>

<body>
 --%>
  <!-- <a class="navbar-brand" href="#"><span>Logo</span> Here</a> -->
<%-- <c:if test="${empty currentUser}">
<nav class="navbar navbar-expand-md navbar-dark fixed-top" id="banner">
	<div class="container">
					<ul class="navbar-nav ml-auto">
      <li class="nav-item hide-header">
	<a class="nav-link" href="welcome">Welcome</a>
	 </li>
	 <li class="nav-item hide-header">
	<a class="nav-link" href="preferences">Preferences</a>
	 </li>
	 <li class="nav-item hide-header">
	<a  class="nav-link"href="recommended">Recommended</a>
	 </li>
	 <li class="nav-item hide-header">
	<a class="nav-link" href="favorites">Favorites</a>
	 </li>
	 <li class="nav-item hide-header">
	<a class="nav-link" href="logout">Logout</a>
	 </li>
	 <li class="nav-item hide-header">
	<a class="nav-link" href="about">About</a>
	 </li>
	 
    </ul>
      </div>

</nav>
				</c:if>
 				<c:if test="${not empty currentUser}"> --%>
 <%-- 				<nav class="navbar navbar-expand-md navbar-dark fixed-top shown-header" id="banner">
	<div class="container">
					<ul class="navbar-nav ml-auto">
      <li class="nav-item">
	<a class="nav-link" href="welcome">Welcome</a>
	 </li>
	 <li class="nav-item">
	<a class="nav-link" href="preferences">Preferences</a>
	 </li>
	 <li class="nav-item">
	 <c:url value="/recommended" var="recommendedPath"/>
	<a  class="nav-link"href="${recommendedPath}">Recommended</a>
	 </li>
	 <li class="nav-item">
	<a class="nav-link" href="favorites">Favorites</a>
	 </li>
	 <li class="nav-item">
	<a class="nav-link" href="logout">Logout</a>
	 </li>
	 <li class="nav-item">
	<a class="nav-link" href="about">About</a>
	 </li>
	 
    </ul>
      </div>

</nav>
				</c:if>
	
</body> --%>

</html>
