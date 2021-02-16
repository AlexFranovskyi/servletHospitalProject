<%@ taglib prefix="mytag" uri="/WEB-INF/tld/mytaglib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<c:set var="allPages" value="${requestScope.page.getAllPages()}" />
<c:set var="pageNumber" value="${requestScope.page.getPageNumber()}" />
<c:set var="sort" value="${requestScope.page.getSort()}" />

<mytag:pagesNaming var="pageNumbers" allPages="${allPages}" pageNumber="${pageNumber}"/>
		
	<div>
		<ul class="pagination">
    		<li class="page-item disabled">
      			<a class="page-link" href="#" tabindex="-1"> <fmt:message key="pages" /> </a>
    		</li>
    		<c:forEach var="p" items="${pageNumbers}">
    			<c:choose>
    				<c:when test="${p - 1 == pageNumber}">
		    			<li class="page-item active">
			      			<a class="page-link" href="#" tabindex="-1">${p}</a>
			    		</li>
    				</c:when>
    				<c:when test="${p == -1}">
			    		<li class="page-item disabled">
			      			<a class="page-link" href="#" tabindex="-1">...</a>
			    		</li>
		    		</c:when>
    				<c:otherwise>
			    		<li class="page-item">
			      			<a class="page-link" href="${path}?${extraParam}pageNumber=${p-1}&sort=${sort}" tabindex="-1">${p}</a>
			    		</li>   
		    	 	</c:otherwise>	
    			</c:choose>
    		</c:forEach>
    	</ul>
	</div>