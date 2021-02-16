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
		
			<div class="mb-4"><h3><fmt:message key="medicalCard" /></h3></div>
			<div class="row mt-3">
				<div class="col"><b><fmt:message key="patientName"/>:</b> ${patientFullName}</div>
				<div class="col"><b><fmt:message key="birthDate" />:</b> <t:localDate date="${patient.getPersonDto().birthDate}"/></div>
				<div class="col"></div>
			</div>
			
			<div class="row mt-3 d-flex align-items-center">
				<div class="col-4"><b><fmt:message key="arriveTime" />:</b> <t:localDateTime dateTime="${patient.arriveTime}"/></div>
				<div class="col-4"><b><fmt:message key="dischargeTime" />:</b> 
					<c:if test="${empty patient.dischargeDateTime}"><fmt:message key="notDischarged" /></c:if>
					<c:if test="${not empty patient.dischargeDateTime}"><t:localDateTime dateTime="${patient.dischargeDateTime}"/></c:if>
				</div>
				<div class="col-4">
					<c:if test="${empty patient.dischargeDateTime && sessionScope.role == 'DOCTOR' && 
										doctor.getUserDto().login == sessionScope.login}">
	        			<form action="discharge_patient" method="post">
	        				<input type="hidden" name="patientId" value="${patient.id}">
		        			<div class="form-group my-2">
				            	<button type="submit" class="btn btn-success"> <fmt:message key="dischargePatient" /> </button>
			           		</div>
	        			</form>
			    	</c:if>
				</div>
			</div>
			
			<div class="row mt-3 d-flex align-items-center">
				<div class="col-4">
						<b><fmt:message key="doctorName" />:</b>
						<c:if test="${doctor.id == 0}">&nbsp<fmt:message key="notAssigned" /></c:if>
						<span> ${doctorFullName}</span>
				</div>
				<div class="col-4">
					<c:if test="${patient.dischargeDateTime == null && sessionScope.role == 'ADMIN'}">
					<form action="doctor_list" method="get">
	           			<input type="hidden" name="patientId" value="${patient.id}">
	        			<div class="form-group my-2">
			            	<button type="submit" class="btn btn-primary">
			            		<c:if test="${doctor.id == 0}"><fmt:message key="assignDoctor" /></c:if>
			            		<c:if test="${doctor.id gt 0}"><fmt:message key="reassignDoctor" /></c:if>
			            	</button>
		           		</div>
        			</form>
        			</c:if>
				</div>
			</div>
			
			<div class="row mt-3 d-flex align-items-center">
				<div class="col-4"><b><fmt:message key="diagnosis" />:</b> 
					<c:if test="${empty patientDiagnosis}"><fmt:message key="notDefined" /></c:if>
					${patientDiagnosis}
				</div>
				<div class="col">
					<c:if test="${empty patient.dischargeDateTime && sessionScope.login == doctor.getUserDto().login}">
	        			<a class="btn btn-info" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
					  		<fmt:message key="defineDiagnosis" />
					  	</a>
						<div class="collapse" id="collapseExample">
							<div class="form-group mt-3">
						    	<form method="post" action="define_diagnosis">
						    		<input type="hidden" name="patientId" value="${patient.id}">
						    		<label for="diagnosisEn"><fmt:message key="enterEnglish" /></label>
						    		<div class="form-group mb-3 w-50">
						            	<input type="text" class="form-control" name="diagnosisEn" required pattern="[A-Z][a-z\d\s,-]*" />
						    		</div>
						    		<label for="diagnosisUk"><fmt:message key="enterUkrainian" /></label>
						            <div class="form-group mb-3 w-50">
						            	<input type="text" class="form-control" name="diagnosisUk" required pattern="[А-ЯІЇЄҐ][а-яіїєґ\d\s,-]*"/>
						    		</div>
						    		<div class="form-group my-2">
							            <button type="submit" class="btn btn-success"><fmt:message key="send" /></button>
						            </div>
						        </form>
						    </div>
						</div>
        			</c:if>
				</div>
				
			</div>
			
			<div class="mt-4 mb-3"><h4><fmt:message key="prescriptions" /></h4></div>
			
			<table class="table table-striped ">
			  <thead>
			    <tr>
			      <th scope="col"><fmt:message key="description" /></th>
			      <th scope="col"><fmt:message key="type" /></th>
			      <th scope="col"><fmt:message key="completionTime" /></th>
			      <th scope="col"><fmt:message key="actions" /></th>
			    </tr>
			  </thead>
			  <tbody>
			   			  
	   			 <c:forEach var="prescription" items="${patient.getPrescriptionDtoList()}">
	   			  <c:if test="${prescription.id > 0}">
	   			 	
	   			 	<c:choose>
					    <c:when test="${sessionScope.lang == 'uk'}">
					      <c:set var="description" value="${prescription.descriptionUk}"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="description" value="${prescription.descriptionEn}"/>
					    </c:otherwise>	  
			    	</c:choose>
	   			 
			    	<tr>
			    	  <td class="align-middle">${description}</td>
				      <td class="align-middle"><fmt:message key="${fn:toLowerCase(prescription.type)}"/></td>
				      <td class="align-middle">
				      	<c:choose>
				      		<c:when test="${empty prescription.completionTime}">
				      			<fmt:message key="notCompleted" />
				      		</c:when>
				      		<c:otherwise>
				      			<t:localDateTime dateTime="${prescription.completionTime}"/>
				      		</c:otherwise>
				      	</c:choose>
				      </td>
				      
				      <td class="align-middle">
				      	<div class="form-row">
				      	<c:if test="${not (sessionScope.role == 'NURSE' && prescription.type == 'OPERATION') && sessionScope.role != 'ADMIN'}">
					      	<c:if test="${empty prescription.completionTime && empty patient.dischargeDateTime}">
					    	  	<form action="complete_prescription" method="post">
					    	  		<input type="hidden" name="patientId" value="${patient.id}">
				           			<input type="hidden" name="prescriptionId" value="${prescription.id}">
				           			<input type="hidden" name="prescriptionType" value="${prescription.type}">
				        			<div class="form-group my-2 mr-3">
						            	<button type="submit" class="btn btn-success"> <fmt:message key="complete" /> </button>
					           		</div>
			        			</form>
			        		</c:if>
			        		<c:if test="${empty prescription.completionTime && empty patient.dischargeDateTime 
			        					&& sessionScope.role == 'DOCTOR' && doctor.getUserDto().login == sessionScope.login}">
			        			<form action="delete_prescription" method="post">
			        				<input type="hidden" name="patientId" value="${patient.id}">
				           			<input type="hidden" name="prescriptionId" value="${prescription.id}">
				        			<div class="form-group my-2">
						            	<button type="submit" class="btn btn-danger"> <fmt:message key="delete" /> </button>
					           		</div>
			        			</form>
					    	</c:if>
				      	</c:if>
				      	</div>
				      </td>
				  	</tr>
   				  </c:if>
   				</c:forEach>
			   </tbody>
			</table>
			
			<div>
				<c:if test="${empty patient.dischargeDateTime && sessionScope.role == 'DOCTOR' && 
																sessionScope.login == doctor.getUserDto().login}">
	       			<a class="btn btn-primary" data-toggle="collapse" href="#collapseOne" role="button" aria-expanded="false" aria-controls="collapseExample">
				  		<fmt:message key="addPrescription" />
				  	</a>
					<div class="collapse" id="collapseOne">
						<div class="form-group mt-3">
					    	<form method="post" action="create_prescription">
					    		<input type="hidden" name="patientId" value="${patient.id}">
					    		<label for="descriptionEn"><fmt:message key="enterEnglish" /></label>
					    		<div class="form-group mb-3 w-50">
					            	<input type="text" class="form-control" name="descriptionEn" required />
					    		</div>
					    		<label for="descriptionUk"><fmt:message key="enterUkrainian" /></label>
					            <div class="form-group mb-3 w-50">
					            	<input type="text" class="form-control" name="descriptionUk" required />
					    		</div>
					    		<label for="prescriptionType"><fmt:message key="chooseType" /></label>
					            <div class="form-group mb-3 w-25">
						    		<select name="prescriptionType" class="form-control">
				        					<option value="DRUGS"><fmt:message key="drugs" /></option>
				        					<option value="PROCEDURE"><fmt:message key="procedure" /></option>
				        					<option value="OPERATION"><fmt:message key="operation" /></option>
									</select>
					    		</div>
					    		
					    		<div class="form-group my-2">
						            <button type="submit" class="btn btn-primary"><fmt:message key="send" /></button>
					            </div>
					        </form>
					    </div>
					</div>
   				</c:if>
			</div>
			
			<div class="mt-4"><h4><fmt:message key="mealList" /></h4></div>				
	    	<table class="table table-striped ">
			  <thead>
			    <tr>
			      <th scope="col"><fmt:message key="meal" /></th>
			      <th scope="col"><fmt:message key="actions" /></th>
			    </tr>
			  </thead>
			  <tbody>
			   			  
	   			 <c:forEach var="meal" items="${patient.getMealDtoSet()}">
	   			 	<c:choose>
					    <c:when test="${sessionScope.lang == 'uk'}">
					      <c:set var="name" value="${meal.nameUk}"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="name" value="${meal.nameEn}"/>
					    </c:otherwise>	  
			    	</c:choose>
	   			 
			    	<tr>
			    	  <td class="align-middle">${name}</td>
				    
					  <td class="align-middle">
				    	<c:if test="${sessionScope.role == 'DOCTOR' && sessionScope.login == doctor.getUserDto().login && meal.id != 0}">
					    	<div class="btn">
					    	  	<form action="remove_patient_meal" method="post">
				           			<input type="hidden" name="mealId" value="${meal.id}">
				           			<input type="hidden" name="patientId" value="${patient.id}">
				        			<div class="form-group my-2">
						            	<button type="submit" class="btn btn-warning"> <fmt:message key="deleteMeal" /> </button>
					           		</div>
			        			</form>
				    		</div>
						</c:if>
					 </td>
				  </tr>
   				  </c:forEach>
   				  
			   </tbody>
			</table>
			
			<div>
				<c:if test="${sessionScope.role == 'DOCTOR' && sessionScope.login == doctor.getUserDto().login}">
	       			<div class="btn">
			    	  	<form action="meal_list" method="post">
		           			<input type="hidden" name="patientId" value="${patient.id}">
		        			<div class="form-group my-2">
				            	<button type="submit" class="btn btn-primary"> <fmt:message key="addMeal" /> </button>
			           		</div>
	        			</form>
		    		</div>
   				</c:if>
			</div>
			
		</div>		
	
	</jsp:body>
</t:commonHtml>