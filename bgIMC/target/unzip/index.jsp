<%@ page language="java" import="bg.imc.web.*" %>
<jsp:useBean id="bean" scope="request" class="bg.imc.web.BeanInfos" />


<html>
<body>
<h2>Hello World!</h2>
<%=bean.getAction() %> 
<hr/>
<%=bean.getComment() %> 
<hr/>
<h:form id="helloForm">

<a href="test?action=loginFormRequest">login</a>
<br/>
<a href="test?action=listUsers">listUsers</a>
<br/>
<a href="test?action=info">info</a>
<br/>
<a href="test?action=infoDebug">infoDebug</a>

<h2>Hello World faces!</h2>
<a href="welcome.faces">welcome.faces</a>
</h:form>
</body>
</html>
