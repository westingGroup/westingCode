<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署页面</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/workflow/workflowManagement.js"></script>
<script type="text/javascript">
	var basePath="<%=request.getContextPath()%>";
</script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/workflow/deployFlow.do"
		method="post" enctype="multipart/form-data" id="deployForm">
		<fieldset id="deployFieldset" style="display: block">
			<legend>部署流程</legend>
			<div>
				<b>支持文件格式：</b>bar
			</div>

			<input type="file" id="workflowFile" name="workflowFile" onchange="clearTips()"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="tips"></span><input type="button"
				id="btnDeploy" value="Submit" onclick="deploy()" />
		</fieldset>
	</form>
</body>
</html>