<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form enctype="multipart/form-data" action="${pageContext.request.contextPath}/v1/api/admin/upload/multiple" method="post">
		<input name="files" type="file" multiple/>
		<input type="submit" value="Upload File" id="upload" />
	</form>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.add_more').click(function(e) {
				e.preventDefault();
				$(this).before("<input name='files[]' type='file'/>");
			});
		});
	</script>
</body>
</html>