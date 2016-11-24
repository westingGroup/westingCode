/**
 * 查询到所有需要执行的任务
 */
function findTodoTasks(currPage) {
	var userId = $("#userId").val();
	if (userId == null || userId == "") {
		alert("请输入需要执行的用户id");
		return;
	}
	AjaxpostByJson(basePath + "/workflow/findTodoTasks.htm", "userId=" + userId
			+ "&currPage=" + currPage + "&pageSize=10", findTodoTasksCallback,
			false);
}

/**
 * 查询到所有需要执行的任务的回调函数
 * 
 * @param data
 */
function findTodoTasksCallback(data) {
	controlPageUpAndDown(data, "currPageNum", "prevLi", "nextLi",
			"totalPageSpan", "currPageSpan", "findTodoTasks");
	appendWorkflowAssignListData(data.content.records);
}

/**
 * 添加任务记录信息
 * 
 * @param records
 */
function appendWorkflowAssignListData(records) {
	$("#workflowAssignBody").empty();
	for (var i = 0; i < records.length; i++) {
		var tr = "<tr>";
		tr += "<td>" + records[i].id + "</td>";
		tr += "<td>" + records[i].processDefinitionId + "</td>";
		tr += "<td>" + records[i].processInstanceId + "</td>";
		tr += "<td>" + records[i].formKey + "</td>";
		if (records[i].assignee != null && records[i].assignee != "")
			tr += "<td>" + records[i].assignee + "</td>";
		else
			tr += "<td></td>";
		tr += "<td>"+records[i].taskStatusName+"</td>";
		if (records[i].taskStatus == "1")//代签收
			tr += "<td><img src='"
					+ basePath
					+ "/assets/img/receive.png' title='签收' style='cursor:pointer;' onclick=receiveTask(\'"
					+ records[i].id + "\')></td>";
		else if(records[i].taskStatus == 2)//处理中
			tr += "<td><img src='"
					+ basePath
					+ "/assets/img/deal.png' title='处理' style='cursor:pointer;' onclick=openTask(\'"
					+ records[i].id
					+ "\')>&nbsp;&nbsp;<img src='"
					+ basePath
					+ "/assets/img/delegate.png' title='委派' style='cursor:pointer;' onclick=delegateTask(\'"
					+ records[i].id + "\')></td>";
		else if(records[i].taskStatus == 3 || records[i].taskStatus == 4)//被委派或者完成委派
			tr += "<td><img src='"
				+ basePath
				+ "/assets/img/deal.png' title='处理' style='cursor:pointer;' onclick=openTask(\'"
				+ records[i].id
				+ "\')></td>";
		tr += "</tr>";
		$("#workflowAssignBody").append(tr);
	}
	$("#row-fluid_id").show();
	if (records.length == 0) {
		var noTr = $("<tr></tr>");
		var td = "<td colspan='7' style='text-align:center;'>暂无符合条件的记录</td>";
		noTr.html(td);
		$("#workflowAssignBody").append(noTr);
		$("#row-fluid_id").hide();
	}
}

/**
 * 打开任务页面
 */
function openTask(taskId) {
	window.location.href = basePath + "/workflow/getFormUrlByTaskId.do?taskId="
			+ taskId + "&userId=" + $("#userId").val() + "&currPage="
			+ $("#currPageNum").val();
}

/**
 * 接受任务
 * 
 * @param taskId任务id
 */
function receiveTask(taskId) {
	AjaxpostByJson(basePath + "/workflow/receiveTask.htm", "userId="
			+ $("#userId").val() + "&taskId=" + taskId, function(data) {
		if (data.successFlag)
			findTodoTasks($("#currPageNum").val());
		else
			alert(data.content);
	}, false);
}

/**
 * 委派任务
 * 
 * @param taskId任务id
 */
function delegateTask(taskId) {
	AjaxpostByJson(basePath + "/workflow/delegateTask.htm", "owner="
			+ $("#userId").val() + "&delegate=3&taskId=" + taskId, function(
			data) {
		if (data.successFlag)
			findTodoTasks($("#currPageNum").val());
		else
			alert(data.content);
	}, false);
}