<%@ page language="java" import="java.util.*" contentType="text/html;charset=gb2312"%>  
<html>  
    <head>   
        <title>URL���ݲ������Ĵ���ʾ��</title>  
    </head>  
    <%  
        String param = request.getParameter("param");  
    %>  
    <body>  
        <a href="encode.jsp?param='����'">�����������</a><br>  
        ���ύ�Ĳ���Ϊ��<%=param%>  
    </body>  
</html>  