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
		<p class="text-left">Лікарня</p>
		<p class="text-left">Адміністратору системи доступний список Лікарів за категоріями (педіатр, травматолог,
		хірург, ...) і список Пацієнтів. Реалізувати можливість сортування:</p>
		
		<p class="text-left">1) пацієнтів:</p>
		<p class="text-left">- за алфавітом;</p>
		<p class="text-left">- за датою народження;</p>
		<p class="text-left">2) лікарів:</p>
		<p class="text-left">- за алфавітом;</p>
		<p class="text-left">- за категорією;</p>
		<p class="text-left">- за кількістю пацієнтів.</p>
		
		<p class="text-left">Адміністратор реєструє в системі пацієнтів і лікарів і призначає пацієнтові лікаря.</p>
		
		<p class="text-left">Лікар визначає діагноз, робить призначення пацієнту (процедури, ліки, операції), які
		фіксуються в Лікарняній картці. Призначення може виконати Медсестра (процедури, ліки) або
		Лікар (будь-яке призначення).</p>
		
		<p class="text-left">Пацієнт може бути виписаний з лікарні, при цьому фіксується остаточний діагноз.
		(Опціонально: реалізувати можливість збереження/експорту документа з інформацією про
		виписку пацієнта).</p>
	
		<c:if test="${sessionScope.role == 'GUEST'}">
        	<Strong><fmt:message key="messageForUnauthorized" /> <a href="${pageContext.request.contextPath}/authentication"><fmt:message key="signInProposal" /></a></Strong>
        </c:if>
	
	</div>
	</jsp:body>
</t:commonHtml>