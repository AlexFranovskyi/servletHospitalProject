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
						
			<div><h4><fmt:message key="categoryList" /></h4></div>				
	    	<table class="table table-striped ">
			  <thead>
			    <tr>
			      <th scope="col"><fmt:message key="category" /></th>
			      <th scope="col"><fmt:message key="actions" /></th>
			    </tr>
			  </thead>
			  <tbody>
			   			  
	   			 <c:forEach var="category" items="${requestScope.categoryList}">
	   			 	<c:choose>
					    <c:when test="${sessionScope.lang == 'uk'}">
					      <c:set var="name" value="${category.nameUk}"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="name" value="${category.nameEn}"/>
					    </c:otherwise>	  
			    	</c:choose>
	   			 
			    	<tr>
			    	  <td class="align-middle">${name}</td>
				    
					  <td class="align-middle">
					    <c:if test="${sessionScope.role == 'ADMIN'}">
					    	<c:if test="${not empty requestScope.doctorId}">
						    	<div class="btn allign-top" role="group">
						    	  	<form action="assign_category" method="post">
					           			<input type="hidden" name="categoryId" value="${category.id}">
					           			<input type="hidden" name="doctorId" value="${doctorId}">
					        			<div class="form-group my-2">
							            	<button type="submit" class="btn btn-success"> <fmt:message key="assignCategory" /> </button>
						           		</div>
				        			</form>
					    		</div>
					    	</c:if>
				    		
				    		<div class="btn" role="group">
					    		<a class="btn btn-info" data-toggle="collapse" href="#collapse${category.id}" role="button" aria-expanded="false" aria-controls="collapseExample">
							  		<fmt:message key="updateCategory" />
							  	</a>
							  	
								<div class="collapse" id="collapse${category.id}">
									<div class="form-group">
								    	<form method="post" action="category_update">
								    		<input type="hidden" name="categoryId" value="${category.id}">
								    		<label for="nameEn"><fmt:message key="enterEnglish" /></label>
								    		<div class="form-group mb-3 w-100">
								            	<input type="text" class="form-control" name="nameEn" required pattern="[A-Z][a-z]*" />
								    		</div>
								    		<label for="nameUk"><fmt:message key="enterUkrainian" /></label>
								            <div class="form-group mb-3">
								            	<input type="text" class="form-control" name="nameUk" required pattern="[А-ЯІЇЄҐ][а-яіїєґ]*"/>
								    		</div>
								    		<div class="form-group my-2">
									            <button type="submit" class="btn btn-success"><fmt:message key="send" /></button>
								            </div>
								        </form>
								    </div>
								</div>
							</div>
					    	<div class="btn allign-top" role="group">
					    	  	<form action="category_delete" method="post">
				           			<input type="hidden" name="categoryId" value="${category.id}">
				        			<div class="form-group my-2">
						            	<button type="submit" class="btn btn-danger"> <fmt:message key="deleteCategory" /> </button>
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
				<c:if test="${sessionScope.role == 'ADMIN'}">
	       			<a class="btn btn-primary" data-toggle="collapse" href="#collapseOne" role="button" aria-expanded="false" aria-controls="collapseExample">
				  		<fmt:message key="addCategory" />
				  	</a>
					<div class="collapse" id="collapseOne">
						<div class="form-group mt-3">
					    	<form method="post" action="create_category">
					    		<label for="nameEn"><fmt:message key="enterEnglish" /></label>
					    		<div class="form-group mb-3 w-50">
					            	<input type="text" class="form-control" name="nameEn" required />
					    		</div>
					    		<label for="nameUk"><fmt:message key="enterUkrainian" /></label>
					            <div class="form-group mb-3 w-50">
					            	<input type="text" class="form-control" name="nameUk" required />
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
		
	</jsp:body>
</t:commonHtml>