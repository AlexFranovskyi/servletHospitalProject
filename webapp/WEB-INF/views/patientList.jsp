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
			
				<div><h4><fmt:message key="patientList" /></h4></div>				
		    	<table class="table table-striped ">
				  <thead>
				    <tr>
				      <th scope="col"><fmt:message key="patientName" /></th>
				      <th scope="col"><fmt:message key="birthDate" /></th>
				      <th scope="col"><fmt:message key="arriveTime" /></th>
				      <th scope="col"><fmt:message key="dischargeTime" /></th>
				      <th scope="col"><fmt:message key="diagnosis" /></th>
				      <th scope="col"><fmt:message key="doctorName" /></th>
				    </tr>
				  </thead>
				  <tbody>
				   			  
		   			 <c:forEach var="patient" items="${requestScope.page.getList()}">
		   			 	<c:set var="doctor" value="${patient.getDoctorDto()}"/>
		   			 	
		   			 	<c:choose>
						    <c:when test="${sessionScope.lang == 'uk'}">
						      <c:set var="doctorFullName" value="${doctor.getUserDto().getPersonDto().lastNameUk} ${doctor.getUserDto().getPersonDto().firstNameUk}"/>
						      <c:set var="patientFullName" value="${patient.getPersonDto().lastNameUk} ${patient.getPersonDto().firstNameUk}"/>
						      <c:set var="patientDiagnosis" value="${patient.diagnosisUk}"/>
						    </c:when>
						    <c:otherwise>
						      <c:set var="doctorFullName" value="${doctor.getUserDto().getPersonDto().lastNameEn} ${doctor.getUserDto().getPersonDto().firstNameEn}"/>
						      <c:set var="patientFullName" value="${patient.getPersonDto().lastNameEn} ${patient.getPersonDto().firstNameEn}"/>
						      <c:set var="patientDiagnosis" value="${patient.diagnosisEn}"/>
						    </c:otherwise>	  
				    	</c:choose>
		   			 
				    	<tr>
				    	  <td class="align-middle"><a href="${pageContext.request.contextPath}/patient_get?patientId=${patient.id}">${patientFullName}</a></td>
					      <td class="align-middle"><t:localDate date="${patient.getPersonDto().birthDate}"/></td>
					      <td class="align-middle"><t:localDateTime dateTime="${patient.arriveTime}"/></td>
					      
					      <td class="align-middle">
					      	<c:if test="${empty patient.dischargeDateTime}">
					      		<fmt:message key="notDischarged" />
					      	</c:if>
					      
					      <t:localDateTime dateTime="${patient.dischargeDateTime}"/>
					      </td>
					      <td class="align-middle"><c:if test="${empty patientDiagnosis}"><fmt:message key="notDefined" /></c:if>${patientDiagnosis}</td>
					    
						  <td class="align-middle">
						    <c:choose>
						    	<c:when test="${doctor.id == 0 && sessionScope.role == 'ADMIN' && patient.dischargeDateTime == null}">
						    	  	<form action="doctor_list" method="get">
					           			<input type="hidden" name="patientId" value="${patient.id}">
					        			<div class="form-group my-2">
							            	<button type="submit" class="btn btn-primary"> <fmt:message key="assignDoctor" /> </button>
						           		</div>
				        			</form>
						    	</c:when>
						    	<c:when test="${doctor.id != 0 && sessionScope.role == 'ADMIN'}">
						    		<a href="${pageContext.request.contextPath}/profile?userId=${doctor.id}&userRole=doctor">${doctorFullName}</a>
						    	</c:when>
							    <c:otherwise>
							    	${doctorFullName}  
							    </c:otherwise>
						    </c:choose>
						 </td>
					  </tr>
	   				  </c:forEach>
	   				  
				   </tbody>
				</table>
			
				<c:set var="sort" value="${requestScope.page.getSort()}" />
				<div class="form-group row">
					<div class="col">
						<c:if test="${not empty requestScope.doctorId}">
							<c:set var="extraParam" value="doctorId=${requestScope.doctorId}&"/>
						</c:if>
						<%@ include file="/WEB-INF/views/parts/paginator.jsp"%>
					</div>
					<div class="col">
					<div class="float-right">
						<div class="form-group">
							    <form method="get" action="${path}" class="form-inline"> 					        
							    	<button type="submit" class="btn btn-primary mr-3"> <fmt:message key="sorting" /> </button>
							        <c:if test="${requestScope.doctorId != null}"><input type="hidden" name="doctorId" value="${requestScope.doctorId}"></c:if>
							        <select name="sort" class="custom-select">
							    		<option value="last_name" <c:if test="${sort == 'last_name'}"> selected </c:if>> <fmt:message key="lastName" /></option>
							    		<option value="birth_date" <c:if test="${sort == 'birth_date'}"> selected </c:if>> <fmt:message key="birthDate" /></option>
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