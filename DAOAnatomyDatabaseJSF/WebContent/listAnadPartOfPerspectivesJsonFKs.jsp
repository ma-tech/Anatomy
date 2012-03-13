<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF_PERSPECTIVES with FOREIGN Keys, in JSON Format</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF_PERSPECTIVES, with FOREIGN Keys, in JSON Format</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Derived Part Of Perspectives ( plus Foreign Keys)</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANAD_PART_OF_PERSPECTIVE
                             
         Fields:     
         1.  POP_PERSPECTIVE_FK => perspectiveFK
         2.  FULL_PATH_JSON     => fullPathJson
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Partial Full Path:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfPerspectivesJsonFKBackingBean.searchTerm}" 
                 size="25"
                 maxlength="25" />
    
    <br />
                 
    <h:outputLabel for="searchTable" 
                   value="Select a Source:" />
                   
    <h:selectOneMenu id="searchTable" value="#{DerivedPartOfPerspectivesJsonFKBackingBean.searchPerspective}">
      <f:selectItem id="item0" itemLabel="Adult Kidney (GenePaint)" itemValue="Adult Kidney (GenePaint)" />
      <f:selectItem id="item1" itemLabel="Renal" itemValue="Renal" />
      <f:selectItem id="item2" itemLabel="Urogenital" itemValue="Urogenital" />
      <f:selectItem id="item3" itemLabel="Whole mouse" itemValue="Whole mouse" />
    </h:selectOneMenu>
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{DerivedPartOfPerspectivesJsonFKBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="POP_PERSPECTIVE_FK" 
                         actionListener="#{DerivedPartOfPerspectivesJsonFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="perspectiveFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.perspectiveFK}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="FULL_PATH_JSON" 
                         actionListener="#{DerivedPartOfPerspectivesJsonFKBackingBean.sort}">
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
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfPerspectivesJsonFKBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfPerspectivesJsonFKBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pageNext}"
                     disabled="#{DerivedPartOfPerspectivesJsonFKBackingBean.firstRow + DerivedPartOfPerspectivesJsonFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesJsonFKBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pageLast}"
                     disabled="#{DerivedPartOfPerspectivesJsonFKBackingBean.firstRow + DerivedPartOfPerspectivesJsonFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesJsonFKBackingBean.totalRows}" />
      
    <h:outputText value="Page #{DerivedPartOfPerspectivesJsonFKBackingBean.currentPage} / #{DerivedPartOfPerspectivesJsonFKBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{DerivedPartOfPerspectivesJsonFKBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{DerivedPartOfPerspectivesJsonFKBackingBean.page}"
                      rendered="#{page != DerivedPartOfPerspectivesJsonFKBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == DerivedPartOfPerspectivesJsonFKBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{DerivedPartOfPerspectivesJsonFKBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{DerivedPartOfPerspectivesJsonFKBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedPartOfPerspectivesJsonFKBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>