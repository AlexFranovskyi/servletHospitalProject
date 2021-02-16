<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:commonHtml>
	<jsp:body>
		<div class="mb-5">
			<%@ include file="/WEB-INF/views/parts/navbar.jsp" %>
		</div>
				
		<div class="container">
			
			<c:if test="${requestScope.message != null}">
				<h4><fmt:message key="${requestScope.message}" /></h4>
			</c:if>
			
			<c:if test="${requestScope.page != null}">
			
				<div><h4><fmt:message key="doctorList" /></h4></div>				
		    	<table class="table table-striped ">
				  <thead>
				    <tr>
				      <th scope="col"><fmt:message key="doctorName" /></th>
				      <th scope="col"><fmt:message key="birthDate" /></th>
				      <th scope="col"><fmt:message key="category" /></th>
				      <th scope="col"><fmt:message key="patientAmount" /></th>
				      
				      <c:if test="${not empty requestScope.action}">
				      	<th scope="col"><fmt:message key="actions" /></th>
				      </c:if>
				      
				    </tr>
				  </thead>
				  <tbody>
				   			  
		   			 <c:forEach var="doctor" items="${requestScope.page.getList()}">
		   			 	
		   			 	<c:choose>
						    <c:when test="${sessionScope.lang == 'uk'}">
						      <c:set var="doctorFullName" value="${doctor.getUserDto().getPersonDto().lastNameUk} ${doctor.getUserDto().getPersonDto().firstNameUk}"/>
						      <c:set var="category" value="${doctor.getCategoryDto().nameUk}"/>
						    </c:when>
						    <c:otherwise>
						      <c:set var="doctorFullName" value="${doctor.getUserDto().getPersonDto().lastNameEn} ${doctor.getUserDto().getPersonDto().firstNameEn}"/>
						      <c:set var="category" value="${doctor.getCategoryDto().nameEn}"/>
						    </c:otherwise>	  
				    	</c:choose>
		   			 
				    	<tr>
				    	  <td class="align-middle">
					    	  <c:choose>
						    	  <c:when test="${sessionScope.role == 'ADMIN'}">
						    	  	<a href="${pageContext.request.contextPath}/profile?userRole=doctor&userId=${doctor.id}">${doctorFullName}</a>
						    	  </c:when>
						    	  <c:otherwise>
						    	  	${doctorFullName}
						    	  </c:otherwise>
					    	  </c:choose>
				    	  </td>
					      <td class="align-middle"><t:localDate date="${doctor.getUserDto().getPersonDto().birthDate}"/></td>
					      <td class="align-middle">
					      	<c:choose>
						      	<c:when test="${doctor.getCategoryDto().id == 0 && sessionScope.role == 'ADMIN'}">
						      		<form action="category_list" method="get">
					           			<input type="hidden" name="doctorId" value="${doctor.id}">
					        			<div class="form-group my-2">
							            	<button type="submit" class="btn btn-primary"> <fmt:message key="assignCategory" /> </button>
						           		</div>
				        			</form>
						      	</c:when>
						      	<c:when test="${doctor.getCategoryDto().id == 0}">
						      		<fmt:message key="notAssigned" />
						      	</c:when>
						      	<c:otherwise>
						      		${category}
						      	</c:otherwise>
					      	</c:choose>
					      </td>
					      
					      <td class="align-middle">${doctor.patientAmount}&nbsp
					      		<a href="${pageContext.request.contextPath}/doctor_patients_list?doctorId=${doctor.id}"><fmt:message key="list" /></a>
					      </td>
					      
					      <c:if test="${requestScope.action != null}">
					      	<td class="align-middle">
					      		<c:if test="${doctor.id != 0 && sessionScope.role == 'ADMIN'}">
						    	  	<form action="assign_doctor" method="post">
					           			<input type="hidden" name="patientId" value="${patientId}">
					           			<input type="hidden" name="doctorId" value="${doctor.id}">
					        			<div class="form-group my-2">
							            	<button type="submit" class="btn btn-primary"> <fmt:message key="assignDoctor" /> </button>
						           		</div>
				        			</form>
						    	</c:if>
					      	</td>
					      </c:if>
					      
					  </tr>
	   				  </c:forEach>
	   				  
				   </tbody>
				</table>
			
				<c:set var="sort" value="${requestScope.page.getSort()}" />
				<div class="form-group row">
					<c:if test="${requestScope.action == 'doctor assigning'}">
				      	<c:set var="extraParam" value="patientId=${requestScope.patientId}&"/>
				    </c:if>
				
					<div class="col"><%@ include file="/WEB-INF/views/parts/paginator.jsp"%></div>
					<div class="col">
					<div class="float-right">
						<div class="form-group">
							    <form method="get" action="${path}" class="form-inline"> 					        
							    	<button type="submit" class="btn btn-primary mr-3"> <fmt:message key="sorting" /> </button>
							        <select name="sort" class="custom-select">
							    		<option value="last_name" <c:if test="${sort == 'last_name'}"> selected </c:if>> <fmt:message key="lastName" /></option>
							    		<option value="c.name" <c:if test="${sort == 'c.name'}"> selected </c:if>> <fmt:message key="category" /></option>
									</select>
							    </form>
						</div>
					</div>
					</div>
				</div>
			</c:if>
			
		</div>
		
	</jsp:body>
</t:commonHtml>