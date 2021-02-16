<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="dateTime" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" required="false" type="java.lang.String" %>

<c:if test="${empty pattern}">
    <c:set var="pattern" value="MM-dd-yyyy HH:mm"/>
</c:if>

<fmt:parseDate value="${dateTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
<fmt:formatDate value="${parsedDateTime}" type="both" pattern="${pattern}"/>