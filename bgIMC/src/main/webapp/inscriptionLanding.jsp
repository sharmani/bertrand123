<%@ page contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<f:view>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr">
<head>

<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
<meta name="Description" content="formation publicité en ligne"/>
<meta name="Keywords" content="adwords, addwords, formation,toulouse"/>
<meta name="Robots" content="All"/>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>

<link  href="css_imc.css"  type="text/css" rel="stylesheet"/>
<link  href="css_adwords_maquette.css"  type="text/css" rel="stylesheet"/>

<title>Inscriptions Formations</title>
</head>
<body>  
<table border="0" >
<tr class="m">
<td class="m" style="width:15%;">
</td>
<td class="m">	
	<img  style="position:relative;" src="images/enTete.jpg"  alt=""/>	
</td>
<td class="m" style="width:15%;">
</td>
</tr >
<tr class="m" style="padding-bottom:0px;padding-top:0px;margin-top:0px;margin-bottom:0px;">
<td class="m"></td>
<td class="m" style="background-image: url(images/fond_tittle.jpg);padding-bottom:0px;padding-top:0px;margin-top:0px;margin-bottom:0px; ">

</td>
<td class="m"> </td>
</tr>
<tr class="m">
<td class="m"></td>
<td class="m" style="background-image: url(images/fond_inter.jpg);height:25px;padding-bottom:0px;padding-top:0px;margin-top:0px;margin-bottom:0px; "> 
<div style="padding-left:10px;margin-left:160px;margin-top:15px;border: 1px solid #c3d4eb; background-color:#e6e6fa;">
	<h1>Nous vous remercions de votre inscription</h1>
	<p/>
 <h:form id="inscriptionForm">
    <table>
    	<tr> <td> Société:</td> <td><h:outputText id="company" value="#{beanInfos.company}" /></td> </tr>    
    	<tr> <td> Nom:</td> <td><h:outputText id="name" value="#{beanInfos.name}" /></td> </tr>    
	    <tr> <td> Prénom:</td> <td><h:outputText id="firstName" value="#{beanInfos.firstName}" />
 	    <tr> <td> eMail:</td> <td><h:outputText id="e_mail" value="#{beanInfos.e_mail}" />
 	    <tr> <td> téléphone:</td> <td><h:outputText id="telephone" value="#{beanInfos.telephone}" />
 	    <tr> <td> date:</td> <td><h:outputText id="date" value="#{beanInfos.date}" />
	
	</table>
	
	Un mail de confimation vous sera envoyé!
	
	<div style="color:#ff0000;font-size:18px;font-weight: bold;margin-left:50px;">
	<a href="http://projectifs.fr/adwords.aspx">Retour</a>
	</div>
 </h:form >
<p/>
	
	

</td>
<td class="m"> </td>
</tr>
</table>









<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-1418274-5");
pageTracker._initData();
pageTracker._trackPageview();
</script>
</body>
</f:view>