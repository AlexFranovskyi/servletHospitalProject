<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${pageContext.request.contextPath}"> <fmt:message key="hospital" /> </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">      
      
      <c:if test="${sessionScope.role == 'ADMIN' || sessionScope.role == 'DOCTOR'}">      
	      <form action="meal_list" method="get">
        	<button class="btn btn-info mr-2" type="submit"><fmt:message key="mealList" /></button>
    	  </form>
      </c:if>
      
      <c:if test="${sessionScope.role == 'ADMIN'}">
		 	      
	      <form action="category_list" method="get">
        	<button class="btn btn-info mr-2" type="submit"><fmt:message key="categoryList" /></button>
    	  </form>
	      
	      
      	  <div class="dropdown">
			  <button class="btn btn-primary dropdown-toggle mr-3" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    <fmt:message key="registerPerson" />
			  </button>
			  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			    <a class="dropdown-item" href="user_registration_form"><fmt:message key="registerUser"/></a>
			    <a class="dropdown-item" href="patient_registration_form"><fmt:message key="registerPatient"/></a>
			  </div>
		  </div>
		  
      </c:if>     
       
      <c:if test="${sessionScope.role != 'GUEST'}">
	      <li class="nav-item">
	        <a class="nav-link" href="doctor_list"> <fmt:message key="doctors" /> <span class="sr-only">(current)</span></a>
	      </li>
	      
	      <li class="nav-item">
	        <a class="nav-link" href="patients_list"> <fmt:message key="patients" /> <span class="sr-only">(current)</span></a>
	      </li>
	      
	     <li class="nav-item">
	       <a class="nav-link" href="profile_own"> <fmt:message key="myProfile" /> <span class="sr-only">(current)</span></a>
	     </li>
      </c:if>
    </ul>
    
	<form method="get" action="${pageContext.request.contextPath}" class="form-inline mr-5">
	   <button type="submit" class="btn btn-info mr-1"> <fmt:message key="switchLanguage" /> </button>
	   <select name="lang" class="custom-select mr-sm-2">
	   	 <option value="en" <c:if test="${sessionScope.lang == 'en'}"> selected </c:if>>EN</option>
	     <option value="uk" <c:if test="${sessionScope.lang == 'uk'}"> selected </c:if>>UA</option>
		 </select>		
	</form>

    <div class="navbar-text mr-1 ml-3"> ${sessionScope.login} </div>
    <div class="navbar-text mr-3 ml-1"> ${sessionScope.role} </div>
    
    <c:if test="${sessionScope.role != 'GUEST'}">
    	<form action="logout" method="get">
        	<button class="btn btn-primary" type="submit"><fmt:message key="signOut" /></button>
    	</form>
    </c:if>
    <c:if test="${sessionScope.role == 'GUEST' && not fn:endsWith(requestScope['javax.servlet.forward.request_uri'], '/authentication')
    							&& not fn:endsWith(requestScope['javax.servlet.forward.request_uri'], '/login')}">    
    	<form action="${pageContext.request.contextPath}/authentication">
        <button class="btn btn-primary" type="submit"> <fmt:message key="signIn" /></button>
    </form>
    </c:if>
  </div>
</nav>