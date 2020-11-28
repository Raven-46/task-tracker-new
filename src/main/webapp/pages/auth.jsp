<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>
	console.log("in auth");
	if("${status}"=="ok")
		{
			localStorage.clear();
			localStorage.setItem('token', "${jwt}");
			window.location.href = "http://localhost:8080/my/items";
		}
	else if("${status}"=="invalid")
		{
			alert("Please create a new account.");
		}
	else if("${status}"=="emailExists")
		{
			alert("Looks like you already have a regular account!");
		}
	else if("${status}"=="new")
		{
			localStorage.clear();
			localStorage.setItem('email', "${email}");
			localStorage.setItem('secret', "${secret}");
			window.location.href = "http://localhost:8080/just-once";
		}
</script>
</head>
<body>
</body>
</html>