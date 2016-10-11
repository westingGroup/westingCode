<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$.post("<%=request.getContextPath()%>/demo/queryTodo", 
	    			{ "inputJson": "" }, 
	    			function (data) { 
	    				//alert("存在待办任务："+data); 
	    				$('#todo').html("待办任务："+data);
	    				//var name = data.toString();
	    				var nameArr =  data.toString().split(",");
	    				var name = nameArr[5];
	    				$('#demoName').val(name);

	    				
	    			});
		});
		
	</script>

<script type="text/javascript">
    function deploy(){
    	var obj=document.getElementById("fileName");
    	//alert(obj.value);
    	//alert( getFullPath(obj) );
/*     	obj.select();     
    	var realpath = document.selection.createRange().text;    
    	alert(realpath); */
     	var fileName = getFullPath(obj);
  		
    	$.post("<%=request.getContextPath()%>/demo/deployFlow", 
    			{ "fileName": fileName }, 
    			function (data) { 
    				alert("执行了:"+data); 
    			});
    	
    	
/* 	 	$.post("westingCommerce/demo/deployFlow",
			{  "fileName": fileName }, 
			function(data){			
				alert(data);
		},"json");    */

    }
    
    function getFullPath(obj) 
    { 
	    if(obj) 
	    { 
	    //ie 
	    if (window.navigator.userAgent.indexOf("MSIE")>=1) 
	    { 
	    obj.select(); 
	    return document.selection.createRange().text; 
	    } 
	    //firefox 
	    else if(window.navigator.userAgent.indexOf("Firefox")>=1) 
	    { 
	    if(obj.files) 
	    { 
	    return obj.files.item(0).getAsDataURL(); 
	    } 
	    return obj.value; 
	    } 
	    return obj.value; 
	    } 
    }
    
    
    function startProcess(){
    	//alert($('#demoName').val());
     	$.post("<%=request.getContextPath()%>/demo/startProcess", 
    			{ "inputJson": $('#demoName').val() }, 
    			function (data) { 
    				alert("提交:"+data); 
    				
    			}); 
    }
    
    
    function approveProcess(){
    	$.post("<%=request.getContextPath()%>/demo/approve", 
    			{ "inputJson": $('#todo').html() }, 
    			function (data) { 
    				alert("审批:"+data); 
    				$('#demoName').val(""); 
    				$.post("<%=request.getContextPath()%>/demo/queryTodo", 
    		    			{ "inputJson": "" }, 
    		    			function (data) { 
    		    				$('#todo').html("待办任务："+data);
    		    			});
    			});
    }

</script>

</head>
<body>
	<fieldset id="deployFieldset" style="display: block">
		<legend>部署流程</legend>
		<div>
			<b>支持文件格式：</b>bar
		</div> 

		<input type="file" id="fileName" name="fileName" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="btnDeploy" value="Submit" onclick="deploy()"/>

	</fieldset>

	<fieldset id="demoInfo" style="display: block">
	
	Name: <input type="text" id="demoName" value=""/>
	<br>
	
	<input type="button" id="btnStartProcess" value="提交" onclick="startProcess()"/>
	
	<input type="button" id="btnApproveProcess" value="审批" onclick="approveProcess()"/>
	</fieldset>
	
	<fieldset id="todoField" style="display: block">
		<div id="todo"></div>
	</fieldset>
	
	
</body>
</html>