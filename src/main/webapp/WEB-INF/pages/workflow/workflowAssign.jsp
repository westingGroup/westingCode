<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的任务</title>
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
	src="<%=request.getContextPath()%>/assets/js/workflow/workflowAssign.js"></script>
<script type="text/javascript">
	var basePath="<%=request.getContextPath()%>";
	var userId = "${userId}";
	var currPage = "${currPage}";
	$(function(){
		if(userId != null && userId != ""){
			$("#userId").val(userId);
			findTodoTasks(currPage);
		}
	});
</script>
</head>
<body>
	<div class="linklist">
		<h3 style="margin-left: 20px; float: left">我的任务</h3>
		<div class="tab_search">
			<div>
				<table class="searchTable">
					<tbody>
						<tr>
							<td style="width: 20%;">用户id：</td>
							<td class="right"><input type="text" id="userId" /></td>
							<td><button class="btn btn-main"
									onclick="findTodoTasks(1)">查询</button></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="linklist">
				<div class="linktit">查询结果</div>
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th>编号</th>
							<th>流程定义编号</th>
							<th>流程实例编号</th>
							<th>访问网址</th>
							<th>指派人</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="workflowAssignBody"></tbody>
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