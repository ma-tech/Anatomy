<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>DAOAnatomyDatabaseJSF</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">

  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.hotkeys.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.cookie.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.extra.js"></script>

  <script type="text/javascript" src="./scripts/global.js"></script>
  <script type="text/javascript" src="./scripts/buildPaths.js"></script>
  
</head>

<body>

  <h:form id="form">

    <h1>DAOAnatomyDatabaseJSF - INDEX.JSP</h1>
   
    <fieldset>
  
 
    <legend>Browse Derived Part Of Perspectives ( plus Foreign Keys)</legend>
 
    <p><b><a href="listAnadPartOfPerspectivesFKsJsonTree.jsf?theilerStage=TS20&searchInput=&pageInput=0">Mouse Anatomy Paths AT theilerStage=TS20, searchInput=, pageInput=0</a></b></p>
    <p><b><a href="listAnadPartOfPerspectivesFKsJsonTree.jsf?theilerStage=TS20&searchInput=sclerotome&pageInput=50">Mouse Anatomy Paths AT theilerStage=TS20, searchInput=sclerotome, pageInput=50</a></b></p>
    <br />
    
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS20" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT theilerStage=TS20, searchInput=, pageInput=0" />
    </h:outputLink><br />
    <br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS20" />
      <f:param name="searchInput" value="sclerotome" />
      <f:param name="pageInput" value="50" />
        <h:outputText value="Mouse Anatomy Paths AT theilerStage=TS20, searchInput=sclerotome, pageInput=50" />
    </h:outputLink><br />
    <br />
    <br />

    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS01" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS01" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS02" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS02" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS03" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS03" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS04" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS04" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS05" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS05" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS06" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS06" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS07" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS07" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS08" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS08" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS09" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS09" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS10" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS10" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS11" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS11" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS12" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS12" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS13" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS13" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS14" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS14" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS15" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS15" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS16" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS16" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS17" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS17" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS18" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS18" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS19" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS19" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS20" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS20" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS21" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS21" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS22" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS22" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS23" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS23" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS24" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS24" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS25" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS25" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS26" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS26" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS27" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS27" />
    </h:outputLink><br />
    <h:outputLink value="listAnadPartOfPerspectivesFKsJsonTree.jsf">
      <f:param name="theilerStage" value="TS28" />
      <f:param name="searchInput" value="" />
      <f:param name="pageInput" value="0" />
        <h:outputText value="Mouse Anatomy Paths AT TS28" />
    </h:outputLink><br />
  
    </fieldset>

  </h:form>

</body>
</html>

</f:view>