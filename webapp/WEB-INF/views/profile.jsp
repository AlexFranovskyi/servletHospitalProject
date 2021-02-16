<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

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
			<h3><fmt:message key="${requestScope.message}" /></h3>
		</c:if>
		<c:set var="user" value="${requestScope.user}"/>
		<c:if test="${not empty requestScope.doctor}">
			<c:set var="doctor" value="${requestScope.doctor}"/>
			<c:set var="user" value="${doctor.getUserDto()}"/>		
		</c:if>
		   			 	
   		<c:choose>
		    <c:when test="${sessionScope.lang == 'uk'}">
		      <c:set var="userFullName" value="${user.getPersonDto().lastNameUk} ${user.getPersonDto().firstNameUk}"/>
		      <c:if test="${not empty doctor}"> <c:set var="category" value="${doctor.getCategoryDto().nameUk}"/></c:if>
		    </c:when>
		    <c:otherwise>
		      <c:set var="userFullName" value="${user.getPersonDto().lastNameEn} ${user.getPersonDto().firstNameEn}"/>
		      <c:if test="${doctor != null}"> <c:set var="category" value="${doctor.getCategoryDto().nameEn}"/></c:if>
		    </c:otherwise>	  
    	</c:choose>
		
			<div class="mb-4"><h3><fmt:message key="profile" /></h3></div>
			<div class="row mt-3">
				<div class="col-4"><b><fmt:message key="fullName"/>:</b> ${userFullName}</div>
			</div>
			<div class="row mt-3">
				<div class="col-3"><b><fmt:message key="birthDate" />:</b> <t:localDate date="${user.getPersonDto().birthDate}"/></div>
				<div class="col-3"><b><fmt:message key="role" />:</b> <fmt:message key="${fn:toLowerCase(user.role)}"/></div>
			</div>
			<c:if test="${not empty doctor}">
				<div class="row mt-3 d-flex align-items-center">
					<div class="col-3"><b><fmt:message key="category" />:</b>
						<c:if test="${doctor.getCategoryDto().id gt 0}">${category}</c:if>
						<c:if test="${doctor.getCategoryDto().id == 0}"><fmt:message key="notAssigned"/></c:if>
					</div>
					<div class="col-3">
						<c:if test="${sessionScope.role == 'ADMIN'}">
							<form action="category_list" method="get">
			           			<input type="hidden" name="doctorId" value="${doctor.id}">
			        			<div class="form-group my-2">
					            	<button type="submit" class="btn btn-primary"> <fmt:message key="assignCategory" /> </button>
				           		</div>
		        			</form>
						</c:if>
					</div>
				</div>
				
				<div class="row mt-3">
					<div class="col">
						<b><fmt:message key="patientAmount" />:</b>
						${doctor.patientAmount}&nbsp
					    <a href="${pageContext.request.contextPath}/doctor_patients_list?doctorId=${doctor.id}"><fmt:message key="list" /></a>
					</div>
				</div>
			</c:if>
						
		</div>		
	
	</jsp:body>
</t:commonHtml>