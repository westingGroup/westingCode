/**
 * 初始化流程版本列表
 */
function initWorkflowVersionList(currPage) {
	var para = "key=" + processKey + "&name=" + $("#processName").val()
			+ "&currPage=" + currPage;
	AjaxpostByJson(basePath + "/workflow/queryProcessVersionPage.htm", para,
			initWorkflowVersionListCallback, false);
}

/**
 * 初始化流程版本列表回调函数
 */
function initWorkflowVersionListCallback(data) {
	controlPageUpAndDown(data, "currPageNum", "prevLi", "nextLi",
			"totalPageSpan", "currPageSpan", "initWorkflowVersionList");
	appendWorkflowVersionListData(data.content.records);
}

/**
 * 添加版本信息到页面中
 * 
 * @param records
 */
function appendWorkflowVersionListData(records) {
	$("#workflowVersionBody").empty();
	for (var i = 0; i < records.length; i++) {
		var tr = "<tr>";
		tr += "<td>" + records[i].name + "</td>";
		tr += "<td>" + records[i].key + "</td>";
		if (!records[i].suspended)// 可用
			tr += "<td>可用</td>";
		else
			// 不可用
			tr += "<td>暂停</td>";
		tr += "<td>" + records[i].version + "</td>";
		tr += "<td>" + records[i].deploymentId + "</td>";
		tr += "<td>" + records[i].deploymentTime + "</td>";
		tr += "</tr>";
		$("#workflowVersionBody").append(tr);
	}
	$("#row-fluid_id").show();
	if (records.length == 0) {
		var noTr = $("<tr></tr>");
		var td = "<td colspan='6' style='text-align:center;'>暂无符合条件的记录</td>";
		noTr.html(td);
		$("#workflowVersionBody").append(noTr);
		$("#row-fluid_id").hide();
	}
}

/**
 * 回退到流程定义页面
 */
function backToWorkflowDefList() {
	window.location = basePath + "/workflow/workflowDefList.do";
}