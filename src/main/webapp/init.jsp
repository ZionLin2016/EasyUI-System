<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>proTest Init Database</title>
<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript">
	location.replace('${pageContext.request.contextPath}/initController/init');
</script>
</head>
<body>init database
</body>
</html>