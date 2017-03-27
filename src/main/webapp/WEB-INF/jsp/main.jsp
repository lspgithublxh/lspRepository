<%@page contentType="text/html; charset=UTF-8"%> 
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html>
<html> 
<head> 
    <title>SSH</title> 
</head> 
<body> 
    <h2><s:property  value="message"/></h2> 
    <h3><s:property value="%{#request.name}" /></h3>
    <table>
     <s:iterator value="#request.user" var="myUser">
     	<tr>
     	  <td> <s:property value="#myUser.id" /> </td>
     	  <td> <s:property value="#myUser.name" /> </td>
     	  <td> <s:property value="#myUser.degree" /> </td>
     	  <td> <s:property value="#myUser.age" /> </td>
     	 </tr>
     </s:iterator> 
    </table>
</body> 
</html> 