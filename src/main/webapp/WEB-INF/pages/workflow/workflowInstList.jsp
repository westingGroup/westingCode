<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程实例页面</title>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/workflow/workflowInstList.js"></script>
<script type="text/javascript">
	var basePath="<%=request.getContextPath()%>";
	var processDefKey = "${processDefKey}";
	var type = 1;
	$(function() {
		initWorkflowInstList(1);
	});
</script>
</head>
<body>
	<div class="linklist">
		<h3 style="margin-left: 20px; float: left">流程实例</h3>
		<div class="tab_search">
			<div>
				<table class="searchTable">
					<tbody>
						<tr>
							<td class="left"><button class="btn btn-main"
									onclick="initWorkflowInstListByType(1,1)">所有实例</button>
								<button class="btn btn-main"
									onclick="initWorkflowInstListByType(2,1)">运行实例</button>
								<button class="btn btn-main" onclick="backToDefList()">返回</button></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="linklist">
				<div class="linktit">查询结果</div>
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th>流程实例id</th>
							<th>流程定义id</th>
							<th>业务key</th>
							<th>启动时间</th>
							<th>结束时间</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody id="workflowInstBody"></tbody>
				</table>
				<!-- begin 上一页下一页操作 -->
				<div class="row-fluid" id="row-fluid_id" style="display: none">
					<div class="span6">
						<div class="dataTables_info" id="sample_editable_1_info">
							<span class="hidden-480" id="currPageSpan">第1页</span>&nbsp;&nbsp;<span
								class="hidden-480" id="totalPageSpan">共1页</span>
						</div>
					</div>
					<div class="span6">
						<div class="dataTables_paginate paging_bootstrap pagination">
							<ul>
								<li class="prev" id="prevLi"><a href="javascript:;">← <span
										class="hidden-480">上一页</span></a></li>
								<li class="next" id="nextLi"><a href="javascript:;"><span
										class="hidden-480">下一页</span> → </a></li>
							</ul>
						</div>
					</div>
					<!-- 当前页的内容 -->
					<input type="hidden" name="currPageNum" id="currPageNum"></input>
				</div>
				<!-- end 上一页下一页操作 -->
			</div>
		</div>
	</div>
</body>
</html>