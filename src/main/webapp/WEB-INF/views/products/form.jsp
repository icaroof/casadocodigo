<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais -
	Casa do Código</title>
</head>
<body>
	<form:form action="${s:mvcUrl('PC#save').build()}" method="post" commandName="product">
		<div>
			<label>Title</label>
			<input type="text" name="title" />
			<form:errors path="title" />
		</div>
		<div>
			<label>Description</label>
			<textarea rows="10" cols="20" name="description"></textarea>
			<form:errors path="description" />
		</div>
		<div>
			<label>Pages</label> <input type="text" name="pages" />
			<form:errors path="pages" />
		</div>
		<c:forEach items="${types}" var="priceType" varStatus="status">
			<div>
				<label>${priceType}</label>
				<input type="text" name="prices[${status.index}].value"/>
				<input type="hidden" name="prices[${status.index}].type" value="${priceType}">
			</div>
		</c:forEach>
		<button type="submit">Register</button>
	</form:form>
</body>
</html>