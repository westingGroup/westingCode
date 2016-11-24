/**部署资源*/
function deploy() {
	var workflowFile = $("#workflowFile").val();
	var suffix = workflowFile.substring(workflowFile.indexOf(".")+1);
	if(suffix != "bar" && suffix != "zip" && suffix != "bpmn" && suffix != "png"){
		$("#tips").html("<font color='red'>请上传合适格式的文件</font>");
		return;
	}
	$("#deployForm").ajaxSubmit({
		url : basePath + "/workflow/deployFlow.do",
		dataType : "json",
		success : function(data) {
			if(!data.successFlag)
				$("#tips").html("<font color='red'>上传失败</font>");
			else
				window.location=basePath+"/workflow/workflowDefList.do";
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

/**清空提示信息*/
function clearTips(){
	$("#tips").html("");
}