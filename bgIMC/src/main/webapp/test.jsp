<%@ page contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<f:view>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr">
<head><meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
	  <meta name="description" "/>
	  <meta name="robots" content="all"/>
	 <meta http-equiv="Content-Style-Type" content="text/css"/>
	 <!-- default platform documentation stylesheets -->
	 <link href="styles.css" media="screen" title="onyx" type="text/css" rel="stylesheet"/>
     <link rel="alternate stylesheet" media="screen" type="text/css" title="essai" href="styles_1.css" />
     <title> jsf face helloworld </title>
</head>
	<body>  

<h1>test</h1>
<hr/>
<br/>
<h2> comment </h2>
<h:outputText value="#{beanInfos.comment}" />
<hr/>
<h2> ref</h2>
<h:outputText value="#{beanInfos.ref}" />
<h2> heloWorld</h2>


<p/>
<h1> -----------</h1>
<p/>
 <h:form id="helloForm">
    <h:inputText id="ref" value="#{beanInfos.ref}" />
    <br/>
	<h:commandLink action="#{beanInfos.doTest}" value="beanInfos.doTest" />
 </h:form >
<p/>

     </body>
 </html>
</f:view>