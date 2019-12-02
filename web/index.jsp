<%--
  Created by IntelliJ IDEA.
  User: yangself
  Date: 2019/11/30
  Time: 4:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>上传界面</title>
  </head>
  <body>
  <form action="${pageContext.request.contextPath }/Servlet" method="post" enctype="multipart/form-data">
    用户名：<input type="text" name="username"><br>
    上传文件：<input type="file" name="fileup">${msg }<br>
    <input type="submit" value="上传">
  </form>
  </body>
</html>
