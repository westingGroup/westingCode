<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>完成任务</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/media/css/DT_bootstrap.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/media/css/style-metro.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/common/common.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/pager.js"></script>
<script type="text/javascript">
	var taskId = "${taskId}";
	var userId = "${userId}";
	var currPage = "${currPage}";
	var basePath="<%=request.getContextPath()%>";
	/**完成任务*/
	function completeTask() {
		var result = $("#result").val();
		var remark = $("#remark").val();
		if (result == "reject" && remark=="") {//如果被拒绝
			alert("请输入审核意见");
			return;
		}
		AjaxpostByJson(basePath + "/workflow/completeTask.htm", "taskId="
				+ taskId + "&result=" + result + "&remark=" + remark, function(
				data) {
			if (data.successFlag)
				window.location.href = basePath
						+ "/workflow/workflowAssign.do?userId=" + userId
						+ "&currPage=" + currPage;
			else
				alert(data.content);
		}, false);
	}
</script>
</head>
<body>
	<div class="linklist">
		<div class="tab_search">
			<table class="searchTable" style="width: 50%;" align="center">
				<tbody class="searchTbody">
					<tr>
						<td class="left" width="25%">审批：</td>
						<td class="right" width="75%"><select style="width: 400px;"
							id="result">
								<option value="agree">同意</option>
								<option value="reject">拒绝</option>
						</select></td>
					</tr>
					<tr>
						<td class="left">意见：</td>
						<td class="right"><textarea rows="3" style="width: 400px;"
								id="remark"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn btn-main" onclick="completeTask()">完成任务</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>