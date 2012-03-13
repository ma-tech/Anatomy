<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF with FOREIGN KEYS</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF, with FOREIGN KEYS</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Derived Part OFs ( plus Foreign Keys)</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANAD_PART_OF
                             
         Fields:     
         1.  APO_OID             => oid
         2.  APO_SPECIES_FK      => speciesFK
         3.  NODE_START_STAGE    => nodeStart
         4.  NODE_END_STAGE      => nodeStop
         5.  PATH_START_STAGE    => pathStart
         6.  PATH_END_STAGE      => pathStop
         7.  NODE_ID             => node
         8.  APO_SEQUENCE        => sequence
         9.  APO_DEPTH           => depth
         10. APO_FULL_PATH       => fullPath
         11. APO_IS_PRIMARY      => primary
         12. APO_IS_PRIMARY_PATH => primaryPath
         13. APO_PARENT_APO_FK   => parentFK
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Partial Full Path:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfFKBackingBean.searchTerm}" 
                 size="25"
                 maxlength="25" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{DerivedPartOfFKBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{DerivedPartOfFKBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_OID" 
                         actionListener="#{DerivedPartOfFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="NODE_ID" 
                         actionListener="#{DerivedPartOfFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="node" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.node}" />
      </h:column>

      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_DEPTH" 
                         actionListener="#{DerivedPartOfFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="depth" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.depth}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_FULL_PATH" 
                         actionListener="#{DerivedPartOfFKBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPath" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPath}" />
      </h:column>

      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedPartOfFKBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfFKBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfFKBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfFKBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedPartOfFKBackingBean.pageNext}"
                     disabled="#{DerivedPartOfFKBackingBean.firstRow + DerivedPartOfFKBackingBean.rowsPerPage >= DerivedPartOfFKBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedPartOfFKBackingBean.pageLast}"
                     disabled="#{DerivedPartOfFKBackingBean.firstRow + DerivedPartOfFKBackingBean.rowsPerPage >= DerivedPartOfFKBackingBean.totalRows}" />
      
    <h:outputText value="Page #{DerivedPartOfFKBackingBean.currentPage} / #{DerivedPartOfFKBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{DerivedPartOfFKBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{DerivedPartOfFKBackingBean.page}"
                      rendered="#{page != DerivedPartOfFKBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == DerivedPartOfFKBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{DerivedPartOfFKBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{DerivedPartOfFKBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedPartOfFKBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>