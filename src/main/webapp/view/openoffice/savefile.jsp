<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.zhuozhengsoft.pageoffice.*"%>
<%
FileSaver fs = (FileSaver)(request.getAttribute("FileSaver"));
fs.close();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>SaveFile</title>
  </head>
  
  <body>

    <h2><font color=red>保存成功！</font></h2>
  </body>
</html>
