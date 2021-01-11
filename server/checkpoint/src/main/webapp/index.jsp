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
	<title>Checkpoint Web Application</title>
</head>
<body>

	<div class="container">
		<h2>FASTA Comparison Checkpoint Web Application</h2>
		<p>&nbsp;</p>

		<h3>Rest Web Service API (in JSON format)</h3>
		<p>&nbsp;</p>

		<h3>Checkpoint Services Available:</h3>
		<p>&nbsp;</p>

		<ul>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=start">Start</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=list">List</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=view&key=">View (change key parameter)</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=save&key=&sequence=">Save (change key and sequence parameters)</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=upload&key=&sequence=&description=&content=">Upload (change key, sequence, description and content parameters)</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=finish&key=">Finish (change key parameter)</a>
			</li>
		</ul>

		<p>&nbsp;</p>
		<p>GitHub repository: <a href="http://github.com/gjportella/fastacompare">http://github.com/gjportella/fastacompare</a></p>
	</div>

</body>
</html>