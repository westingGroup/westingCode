/**
 * 初始化工作流实例(type==1,所有流程；type==2,运行中流程)
 */
function initWorkflowInstList(currPage) {
	AjaxpostByJson(basePath + "/workflow/queryWorkflowInstPager.htm",
			"type="+type+"&currPage=" + currPage + "&pageSize=10&processDefKey="+processDefKey,
			initWorkflowInstPageCallback, false);
}

/**
 * 初始化工作流实例回调函数
 * @param data
 */
function initWorkflowInstPageCallback(data) {
	controlPageUpAndDown(data, "currPageNum", "prevLi", "nextLi",
			"totalPageSpan", "currPageSpan", "initWorkflowInstList");
	appendWorkflowInstListData(data.content.records);
}

/**
 * 添加工作流实例
 * @param records
 */
function appendWorkflowInstListData(records){
	$("#workflowInstBody").empty();
	for(var i = 0; i < records.length; i++){
		var tr = "<tr>";
		tr += "<td>" + records[i].id + "</td>";
		tr += "<td>" + records[i].processDefinitionId + "</td>";
		tr += "<td>" + records[i].businessKey + "</td>";
		if(records[i].startTime != null)
			tr += "<td>" + records[i].startTime + "</td>";
		else
			tr += "<td></td>";
		if(records[i].endTime != null)
			tr += "<td>" + records[i].endTime + "</td>";
		else 
			tr += "<td></td>";
		if(records[i].endTime == null)//如果未完成，则为进行中
			tr += "<td>运行中</td>";
		else
			tr += "<td>已处理</td>";
		tr += "</tr>";
		$("#workflowInstBody").append(tr);
	}
	
	$("#row-fluid_id").show();
	if (records.length == 0) {
		var noTr = $("<tr></tr>");
		var td = "<td colspan='5' style='text-align:center;'>暂无符合条件的记录</td>";
		noTr.html(td);
		$("#workflowInstBody").append(noTr);
		$("#row-fluid_id").hide();
	}
}

/**
 * 初始化运行中的实例列表
 * @param type
 * @param currPage
 */
function initWorkflowInstListByType(sourceType, currPage){
	type = sourceType;
	initWorkflowInstList(currPage);
}

/**
 * 返回到定义页面
 */
function backToDefList(){
	window.location = basePath + "/workflow/workflowDefList.do"
}