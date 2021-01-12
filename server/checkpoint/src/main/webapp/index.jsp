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

		<h3>Rest Web Service API (in text/plain and JSON formats)</h3>
		<p>&nbsp;</p>

		<h3>1. Checkpoint Services Available:</h3>
		<p>&nbsp;</p>

		<ul>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=start">Start</a> (optionally inform the key parameter)
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=list">List</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=view&key=">View</a> (change the key parameter)
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=detailLastSequence&key=">Detail Last Sequence</a> (change the key parameter)
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=save&key=&sequence=">Save</a> (change the key and sequence parameters)
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/rest/service?c=finish&key=">Finish</a> (change the key parameter)
			</li>
		</ul>

		<p>&nbsp;</p>
		<h3>2. Upload Service Available:</h3>
		<p>&nbsp;</p>
		
		<ul>
			<li>
				Must be an HTTP POST request with key, sequence, description and content (multipart/form-data) parameters.<br/>
				URL: ${pageContext.request.contextPath}/rest/upload
			</li>
		</ul>

		<p>&nbsp;</p>
		<p>GitHub repository: <a href="http://github.com/gjportella/fastacompare">http://github.com/gjportella/fastacompare</a></p>
		<p>Copyright (c) 2021 - Gustavo Portella - License GPLv3. This software comes with ABSOLUTELY no warranty.</p>
	</div>

</body>
</html>