/**
 * 初始化流程定义版本列表信息
 */
function initWorkflowDefList(currPage) {
	var para = "key=" + $("#processKey").val() + "&currPage=" + currPage;
	AjaxpostByJson(basePath + "/workflow/queryProcessDefPage.htm", para,
			initWorkflowDefPageCallback, false);
}

/**
 * 初始化流程定义版本列表回调函数
 * 
 * @param data
 */
function initWorkflowDefPageCallback(data) {
	controlPageUpAndDown(data, "currPageNum", "prevLi", "nextLi",
			"totalPageSpan", "currPageSpan", "initWorkflowDefList");
	appendWorkflowDefListData(data.content.records);
}

/**
 * 添加流程
 * 
 * @param records
 */
function appendWorkflowDefListData(records) {
	$("#workflowDefBody").empty();
	for (var i = 0; i < records.length; i++) {
		var tr = "<tr>";
		tr += "<td>" + records[i].id + "</td>";
		tr += "<td>" + records[i].key + "</td>";
		tr += "<td>" + records[i].name + "</td>";
		if (!records[i].suspended)// 可用
			tr += "<td>可用</td>";
		else
			// 不可用
			tr += "<td>暂停</td>";
		tr += "<td>";
		if (!records[i].suspended)// 可用
			tr += "<img src='"
					+ basePath
					+ "/assets/img/pause.png' title='暂停' style='cursor:pointer;' onclick=pauseWorkflowDef(\'"
					+ records[i].id
					+ "\')>&nbsp;&nbsp;<img src='"
					+ basePath
					+ "/assets/img/begin.png' title='启动流程' style='cursor:pointer;' onclick=startWorkflow(\'"
					+ records[i].key + "\')>";
		else
			// 如果不可用
			tr += "<img src='"
					+ basePath
					+ "/assets/img/recovery.png' title='恢复' style='cursor:pointer;' onclick=recoveryWorkflowDef(\'"
					+ records[i].id + "\')>";
		tr += "&nbsp;&nbsp;<img src='"
				+ basePath
				+ "/assets/img/chakanEye.png' title='查看版本信息' "
				+ "style='cursor:pointer;' onclick=viewVersionList(\'"
				+ records[i].key
				+ "\')>&nbsp;&nbsp;<img src='"
				+ basePath
				+ "/assets/img/chakanInst.png' title='查看实例' style='cursor:pointer;' onclick=viewInstanceList(\'"
				+ records[i].key + "\')></td>";
		tr += "</tr>";
		$("#workflowDefBody").append(tr);
	}
	$("#row-fluid_id").show();
	if (records.length == 0) {
		var noTr = $("<tr></tr>");
		var td = "<td colspan='5' style='text-align:center;'>暂无符合条件的记录</td>";
		noTr.html(td);
		$("#workflowDefBody").append(noTr);
		$("#row-fluid_id").hide();
	}
}

/**
 * 新增流程定义
 */
function addWorkflowDef() {
	window.location = basePath + "/workflow/workflowManagement.do";
}

/**
 * 查询版本列表
 */
function viewVersionList(processKey) {
	window.location = basePath + "/workflow/workflowVersionList.do?processKey="
			+ processKey;
}

/**
 * 查询此定义下的流程实例
 */
function viewInstanceList(processKey) {
	window.location = basePath + "/workflow/workflowInstList.do?processDefKey="
			+ processKey;
}

/**
 * 暂停工作流定义
 * 
 * @param workflowId
 */
function pauseWorkflowDef(workflowDefId) {
	AjaxpostByJson(basePath + "/workflow/pauseWorkflowDef.htm",
			"workflowDefId=" + workflowDefId, function(data) {
				if (data.successFlag)// 如果暂停成功
					initWorkflowDefList($("#currPageNum").val());
				else
					// 如果暂停失败
					alert(data.content);
			}, false);
}

/**
 * 恢复工作流定义
 */
function recoveryWorkflowDef(workflowDefId) {
	AjaxpostByJson(basePath + "/workflow/recoveryWorkflowDef.htm",
			"workflowDefId=" + workflowDefId, function(data) {
				if (data.successFlag)// 如果恢复成功
					initWorkflowDefList($("#currPageNum").val());
				else
					// 如果恢复失败
					alert(data.content);
			}, false);
}

/**
 * 启动工作流
 */
function startWorkflow(processDefinitionKey) {
	AjaxpostByJson(basePath + "/workflow/startWorkflow.htm",
			"processDefinitionKey=" + processDefinitionKey + "&userId=2",
			function(data) {
				if (data.successFlag)// 如果启动成功
					window.location = basePath
							+ "/workflow/workflowInstList.do?processDefKey="
							+ processDefinitionKey;
				else
					// 如果启动实例失败
					alert(data.content);
			}, false);
}