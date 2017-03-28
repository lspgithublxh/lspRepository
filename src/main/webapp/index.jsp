<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body>
<h2>Hello World!</h2>
        <form action="loginUser" method="post" >
           userNams:<input type="text" name="user.name" />
           password:   <input type="password" name="user.home" />
        <input type="submit" value="login" >
        </form>
        
         <a href="loginout">loginout</a>
</body>
</html>
