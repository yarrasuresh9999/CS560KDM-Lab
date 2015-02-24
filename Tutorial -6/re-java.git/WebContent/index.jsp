<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Relationship Extraction Java Starter Application</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/watson-bootstrap-dark.css">
<link rel="stylesheet" href="css/browser-compatibility.css">
<link rel="stylesheet" href="css/watson-code.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<div class="header row">
			<div class="col-lg-3">
				<img src="images/app.png">
			</div>
			<div class="col-lg-8">
				<h2>Relationship Extraction Java Starter Application</h2>
				<p>The Relationship Extraction service makes sense of large
					unstructured data sets. It enables browsing of large collections of
					data via predefined sets of concepts and relations among them. We
					offer two APIs: one for English and one for Spanish news models.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-6">
				<h2>Try the service</h2>
				<div class="well">
					<form method="post" class="form-horizontal"  action="demo">
						<fieldset>
							<div class="form-group row">
								<div class="col-lg-12">
									<label for="textArea" class="control-label">Enter or
										paste text from an article</label>
									<textarea id="textArea" name="txt" rows="12" required
										class="form-control">${txt}</textarea>
									<span class="help-block">The annotator is adapted to a
										news domain, so news articles will garner the best results.</span>
								</div>
							</div>
							<div class="form-group row">
								<label for="select" class="col-lg-12 control-label">Select
									SIRE model:</label>
								<div class="col-lg-12">
									<select id="select" name="sid" class="form-control"><option
											value="ie-en-news">English News</option>
										<option value="ie-es-news">Spanish News</option></select>
								</div>
							</div>
							<div style="margin-bottom: 0px; padding-top: 5px;"
								class="form-group row">
								<div class="col-lg-5 col-lg-push-7">
									<input type="hidden" name="rt" value="xml">
									<button type="submit" class="btn btn-block">Analyze</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			<div class="col-lg-6">
				<h2>Output</h2>
				<c:if test="${not empty error}">
					<div class="well">
						<p style="font-weight:bold;color:red;">Error: ${error}</p>
					</div>
				</c:if>
				<c:if test="${not empty relationship}">
					<p class="help-block">The Relationship Extraction service outputs the extracted 
					metadata in an XML format known as "DOCXML"</p>
					<div>
					<pre><code class="language-markup"><c:out value="${relationship}"></c:out></code></pre>
					</div>
				</c:if>
				
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/css_browser_selector.js"></script>
	<script type="text/javascript" src="js/prism.min.js"></script>	
</body>
</html>