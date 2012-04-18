<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>INDEX</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - INDEX</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Things</legend>
    <br />
  
    <%-- The sortable datatable 
                                     ANA_OBJECT
                                     
         Fields: oid              => OBJ_OID; 
                 creationDateTime => OBJ_CREATION_DATETIME; 
                 creatorFK        => OBJ_CREATOR_FK;
                 table            => OBJ_TABLE;
                 description      => OBJ_DESCRIPTION;
    --%>


    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Search Term:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{ThingBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchTable" 
                   value="Select a Source:" />
                   
    <h:selectOneMenu id="searchTable" value="#{ThingBackingBean.searchTable}">
      <f:selectItem id="item0" itemLabel="Everything" itemValue="" />
      <f:selectItem id="item1" itemLabel="Synonyms" itemValue="ANA_SYNONYM" />
      <f:selectItem id="item2" itemLabel="Nodes" itemValue="ANA_NODE" />
      <f:selectItem id="item3" itemLabel="Timed Nodes" itemValue="ANA_TIMED_NODE" />
      <f:selectItem id="item4" itemLabel="Relationships" itemValue="ANA_RELATIONSHIP" />
      <f:selectItem id="item5" itemLabel="Stages" itemValue="ANA_STAGE" />
      <f:selectItem id="item6" itemLabel="Sources" itemValue="ANA_SOURCE" />
      <f:selectItem id="item7" itemLabel="Editors" itemValue="ANA_EDITOR" />
      <f:selectItem id="item8" itemLabel="Versions" itemValue="ANA_VERSION" />
      <f:selectItem id="item9" itemLabel="Perspective Ambits" itemValue="ANA_PERSPECTIVE_AMBIT" />
    </h:selectOneMenu>
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{ThingBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{ThingBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="OBJ_OID" 
                         actionListener="#{ThingBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="OBJ_CREATION_DATETIME" 
                         actionListener="#{ThingBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="creationDateTime" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.creationDateTime}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="OBJ_CREATOR_FK" 
                         actionListener="#{ThingBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="creatorFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.creatorFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="OBJ_TABLE" 
                         actionListener="#{ThingBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="table" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.table}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="OBJ_DESCRIPTION" 
                         actionListener="#{ThingBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="description" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.description}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{ThingBackingBean.pageFirst}"
                     disabled="#{ThingBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{ThingBackingBean.pagePrevious}"
                     disabled="#{ThingBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{ThingBackingBean.pageNext}"
                     disabled="#{ThingBackingBean.firstRow + ThingBackingBean.rowsPerPage >= ThingBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{ThingBackingBean.pageLast}"
                     disabled="#{ThingBackingBean.firstRow + ThingBackingBean.rowsPerPage >= ThingBackingBean.totalRows}" />
      
    <h:outputText value="Page #{ThingBackingBean.currentPage} / #{ThingBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{ThingBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{ThingBackingBean.page}"
                      rendered="#{page != ThingBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == ThingBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{ThingBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <h:commandButton value="Set" 
                     action="#{ThingBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{ThingBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>