<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome!!!</title>
</head>
<body>
This is first page of BabyGallery.ear!<br>

<h1>User</h1>

<h2>add</h2>

<form action="registerNewUser.action" method="get">
    姓名:<input name="name"><br>
    E-mail:<input name="email"><br>
    宝宝名字:<input name="babyName"><br>
    <input type="submit" value="提交">
</form>
<h2>search</h2>

<form action="findUser.action" method="get">
    姓名:<input name="name">
    <input type="submit" value="查找">
</form>

<form action="findUser.action" method="get">
    id:<input name="id">
    <input type="submit" value="查找">
</form>
<h2>更新User</h2>
需要传入 json 对象

<h1>Viewer</h1>

<h1>Messages</h1>

<h2>添加新 Message</h2>

<form action="registerNewMessage.action" method="get">
    姓名:<input name="name"><br>
    E-mail:<input name="email"><br>
    宝宝名字:<input name="babyName"><br>
    <input type="submit" value="提交">
</form>
<h2>查找 Message</h2>

<form action="registerNewUser.action" method="get">
    姓名:<input name="name"><br>
    E-mail:<input name="email"><br>
    宝宝名字:<input name="babyName"><br>
    <input type="submit" value="提交">
</form>

<a href="listMessages.action?user-id=1">查找 id=1的所有信息</a><br>
<a href="registerNewUser.action?name=张山">注册新用户张山</a><br>
<a href="findUser.action?userId=1">查找id=1的用户</a><br>

<form action="upload-images.action" method="post" enctype="multipart/form-data">
    文件1:<input type="file" name="image"><br/>
    文件2:<input type="file" name="image"><br/>
    文件3:<input type="file" name="image"><br/>
    文件4:<input type="file" name="image"><br/>
    文件5:<input type="file" name="image"><br/>
    文件6:<input type="file" name="image"><br/>
    消息内容:<input type="text" name="content"><br/>
    日期：<input type="text" name="markpoint"><br/>
    <input type="submit" value="上传">
</form>

</body>
</html>
