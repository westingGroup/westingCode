<%@ page language="java" import="java.util.*" contentType="text/html;charset=gb2312"%>  
<html>  
    <head>   
        <title>URL传递参数中文处理示例</title>  
    </head>  
    <%  
        String param = request.getParameter("param");  
    %>  
    <body>  
        <a href="encode.jsp?param='中文'">请点击这个链接</a><br>  
        你提交的参数为：<%=param%>  
    </body>  
</html>  