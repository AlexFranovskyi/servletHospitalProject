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
				<h2><fmt:message key="${requestScope.message}" /></h2>
			</c:if>
			
			<form method="post" action="register_user">
	            <div class="form-group row">
		        <label class="col-sm-3 col-form-label"> <fmt:message key="firstNameEn" />:</label>
			        <div class="col-sm-6">
			        	<input type="text" name="firstNameEn" class="form-control" required pattern="[A-Z][a-z]*" placeholder="John"/>
			        </div>
	        	</div>
	        	
	        	<div class="form-group row">
		        <label class="col-sm-3 col-form-label"> <fmt:message key="firstNameUk" />:</label>
			        <div class="col-sm-6">
			        	<input type="text" name="firstNameUk" class="form-control" required pattern="[А-ЯІЇЄҐ][а-яіїєґ]*" placeholder="Джон"/>
			        </div>
	        	</div>
	        	
	        	<div class="form-group row">
		        <label class="col-sm-3 col-form-label"> <fmt:message key="lastNameEn" />:</label>
			        <div class="col-sm-6">
			        	<input type="text" name="lastNameEn" class="form-control" required pattern="[A-Z][a-z]*" placeholder="Dow"/>
			        </div>
	        	</div>
	        	
	        	<div class="form-group row">
		        <label class="col-sm-3 col-form-label"> <fmt:message key="lastNameUk" />:</label>
			        <div class="col-sm-6">
			        	<input type="text" name="lastNameUk" class="form-control" required pattern="[А-ЯІЇЄҐ][а-яіїєґ]*" placeholder="Дов"/>
			        </div>
	        	</div>
	            
	            <div class="form-group row">
	        	<label class="col-sm-3 col-form-label"> <fmt:message key="birthDate" />:</label>
			        <div class="col-sm-2">
			        	<input type="text" name="birthDate" class="form-control" required pattern="[0-9]{2}-[0-9]{2}-[0-9]{4}" placeholder="mm-dd-yyyy"/>
			        </div>
	       		</div>
	       		
	       		<div class="form-group row">
	        	<label class="col-sm-3 col-form-label"> <fmt:message key="login" />:</label>
			        <div class="col-sm-6">
			        	<input type="text" name="login" class="form-control" required pattern="[a-zA-Z0-9._-]{3,}" placeholder="John-Dow_1.2"/>
			        </div>
	       		</div>
	            
	            
	            <div class="form-group row">
	        	<label class="col-sm-3 col-form-label"> <fmt:message key="password" />: </label>
			        <div class="col-sm-6">
			        	<input type="password" name="password" class="form-control" required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{4,}" placeholder="Pass1"/>
			        </div>
	       		</div>
	            
	            <div class="form-group row">
	            <label class="col-sm-3 col-form-label"> <fmt:message key="role" />:</label>
	            	<div class="col-sm-2">
	            		<select name="role" class="form-control">
	        					<option value="ADMIN"><fmt:message key="admin" /></option>
	        					<option value="DOCTOR"><fmt:message key="doctor" /></option>
	        					<option value="NURSE"><fmt:message key="nurse" /></option>
						</select>
					</div>
	            </div>
	            
	            <div>
	            	<button class="btn btn-primary mb-2 mr-3" type="submit"><fmt:message key="register" /></button>
	            </div>
        	</form>
		
		</div>>
	
	</jsp:body>
</t:commonHtml>