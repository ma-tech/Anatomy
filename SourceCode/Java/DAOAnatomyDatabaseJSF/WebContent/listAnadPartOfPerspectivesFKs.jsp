<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF_PERSPECTIVES with FOREIGN KEYS</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF_PERSPECTIVES, with FOREIGN KEYS</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Derived Part Of Perspectives ( plus Foreign Keys)</legend>
  
    <%-- The sortable datatable 
                             ANAD_PART_OF
                             
         Fields:     
         1.  POP_PERSPECTIVE_FK => perspectiveFK
         2.  FULL_PATH          => fullPath
         3.  POP_IS_ANCESTOR    => ancestor
         4.  PUBLIC_ID          => node
    --%>
    
    <h:outputLabel for="searchTerm" 
                   value="Enter a Full or Partial Node Name or a Full or Partial Node Name or EMAPA Id Number:" />
    <%-- Set Search Term --%>
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfPerspectivesFKBackingBean.searchTerm}" 
                 size="25"
                 maxlength="25" />
    <br />
    <br />
    <br />
    <br />
    <br />

    <h:outputLabel for="searchStartStage" 
                   value="Select a Stage:" />
    <h:selectOneMenu id="searchStartStage" 
                      value="#{DerivedPartOfPerspectivesFKBackingBean.searchStartStage}">
   	  <f:selectItem id="itemTS01S" itemValue="0" itemLabel="TS01" />
   	  <f:selectItem id="itemTS02S" itemValue="1" itemLabel="TS02" />
   	  <f:selectItem id="itemTS03S" itemValue="2" itemLabel="TS03" />
   	  <f:selectItem id="itemTS04S" itemValue="3" itemLabel="TS04" />
   	  <f:selectItem id="itemTS05S" itemValue="4" itemLabel="TS05" />
   	  <f:selectItem id="itemTS06S" itemValue="5" itemLabel="TS06" />
   	  <f:selectItem id="itemTS07S" itemValue="6" itemLabel="TS07" />
   	  <f:selectItem id="itemTS08S" itemValue="7" itemLabel="TS08" />
   	  <f:selectItem id="itemTS09S" itemValue="8" itemLabel="TS09" />
   	  <f:selectItem id="itemTS10S" itemValue="9" itemLabel="TS10" />
   	  <f:selectItem id="itemTS11S" itemValue="10" itemLabel="TS11" />
   	  <f:selectItem id="itemTS12S" itemValue="11" itemLabel="TS12" />
   	  <f:selectItem id="itemTS13S" itemValue="12" itemLabel="TS13" />
   	  <f:selectItem id="itemTS14S" itemValue="13" itemLabel="TS14" />
   	  <f:selectItem id="itemTS15S" itemValue="14" itemLabel="TS15" />
   	  <f:selectItem id="itemTS16S" itemValue="15" itemLabel="TS16" />
   	  <f:selectItem id="itemTS17S" itemValue="16" itemLabel="TS17" />
   	  <f:selectItem id="itemTS18S" itemValue="17" itemLabel="TS18" />
   	  <f:selectItem id="itemTS19S" itemValue="18" itemLabel="TS19" />
   	  <f:selectItem id="itemTS20S" itemValue="19" itemLabel="TS20" />
   	  <f:selectItem id="itemTS21S" itemValue="20" itemLabel="TS21" />
   	  <f:selectItem id="itemTS22S" itemValue="21" itemLabel="TS22" />
   	  <f:selectItem id="itemTS23S" itemValue="22" itemLabel="TS23" />
   	  <f:selectItem id="itemTS24S" itemValue="23" itemLabel="TS24" />
   	  <f:selectItem id="itemTS25S" itemValue="24" itemLabel="TS25" />
   	  <f:selectItem id="itemTS26S" itemValue="25" itemLabel="TS26" />
   	  <f:selectItem id="itemTS27S" itemValue="26" itemLabel="TS27" />
   	  <f:selectItem id="itemTS28S" itemValue="27" itemLabel="TS28" />
    </h:selectOneMenu>
    <br />
    <br />

    <h:commandButton value="Search" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageFirst}" />
    <br />

    <h:messages infoStyle="color: green;" 
                errorStyle="color: red;" />
    <br />

    <h:dataTable value="#{DerivedPartOfPerspectivesFKBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="EMAPA_PUBLIC_ID" 
                         actionListener="#{DerivedPartOfPerspectivesFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeEmapa" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeEmapa}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="EMAP_PUBLIC_ID" 
                         actionListener="#{DerivedPartOfPerspectivesFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeEmap" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeEmap}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="FULL_PATH" 
                         actionListener="#{DerivedPartOfPerspectivesFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPath" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPath}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="FULL_PATH_JSON" 
                         actionListener="#{DerivedPartOfPerspectivesFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPathJson" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPathJson}" />
      </h:column>

      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageNext}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow + DerivedPartOfPerspectivesFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesFKBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageLast}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow + DerivedPartOfPerspectivesFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesFKBackingBean.totalRows}" />
      
    <h:outputText value="Tree No. #{DerivedPartOfPerspectivesFKBackingBean.currentPage} From #{DerivedPartOfPerspectivesFKBackingBean.totalPages} Possible Tree Paths" />
    <br />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedPartOfPerspectivesFKBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>