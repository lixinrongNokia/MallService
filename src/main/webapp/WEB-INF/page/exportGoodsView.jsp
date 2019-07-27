<%--
  Created by IntelliJ IDEA.
  User: LIXINRONG
  Date: 2016/9/10
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>一键导入</title>
    <script type="text/javascript">
        function regType() {
            var inputFile = document.getElementById('inputFile').value;
            inputFile = inputFile.substring(inputFile.lastIndexOf('.') + 1, inputFile.length);
            if ('xls' != inputFile) {
                alert('文档格式不正确!');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form enctype="multipart/form-data" action="exportUp.do" method="post" onsubmit="return regType()">
    <input id="inputFile" type="file" name="excel"/><input type="submit" value="ok"/>
</form>
<h3 style="text-overline-color: red">${errorMsg}</h3>
</body>
</html>
