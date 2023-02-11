<%@ page pageEncoding="UTF-8"%>
<html>
    <head>
        <title>addheadline</title>
    </head>
    <body>
        <table>
            <h2 style="color: red"> 表单提交：</h2><br>
            <form id="headlineInfo" method="post" action="/simpleframework/headline/add">
                头条说明:<input type="text" name="lineName"><br>
                头条链接:<input type="text" name="lineLink"><br>
                头条图片地址:<input type="text" name="lineImg"><br>
                优先级:<input type="text" name="priority"><br>
                结果：<h3>状态码：${result.code} 信息：${result.msg}</h3><br>
                <input type="submit" value="提交">
            </form>
        </table>
    </body>
</html>