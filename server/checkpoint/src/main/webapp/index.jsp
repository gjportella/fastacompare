<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
	<title>IaaS Pricing Web Application</title>
</head>
<body>

	<div class="container">
		<h2>FASTA Comparison Checkpoint Web Application</h2>
		<p>&nbsp;</p>

		<h4>Rest Web Service API (in JSON format):</h4>
		<ul>
			<li>
				Availability Zone:
				<a href="${pageContext.request.contextPath}/rest/availabilityZone/id/1">1</a>,
				<a href="${pageContext.request.contextPath}/rest/availabilityZone/list">all</a>
			</li>
		</ul>
		<ul>
			<li>Swagger (OpenAPI Specification)</li>
		</ul>
	</div>

</body>
</html>