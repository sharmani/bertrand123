<%@ page contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<f:view>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr">
<head><meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
	  <meta name="robots" content="all"/>
	 <meta http-equiv="Content-Style-Type" content="text/css"/>
	 <!-- default platform documentation stylesheets -->
	 <link href="styles.css" media="screen" title="onyx" type="text/css" rel="stylesheet"/>
     <link rel="alternate stylesheet" media="screen" type="text/css" title="essai" href="styles_1.css" />
     <title> jsf face helloworld </title>
      
</head>
	<body>  
	
	
	

<h:outputText value="Hello le World ! (en JSF !)" />
<br/>
<h:outputText value="#{beanInfos.comment}" />
<hr/>
<h:outputText value="#{beanInfos.ref}" />
<p/>
 <h:form id="test">
<h:commandLink action="bg" value="aaaaaaaaaaaaaaaaaa" />
<br/>

<h:commandLink action="bg" value="http://java.sun.com/javaee/javaserverfaces/ajax/tutorial.jsp" />
</h:form>

<p/>
 <h:form id="helloForm">
    <h:inputText id="ref" value="#{beanInfos.ref}" />
    <br/>
	<h:commandLink action="#{beanInfos.doTest}" value="beanInfos.doTest" />
 </h:form >
<p/>

<p/>

<br/>

<br/>



</body>
 </html>
</f:view>