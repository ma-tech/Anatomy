<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Derived Part OFs</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANAD_PART_OF
                             
         Fields:     
         1.  APO_OID                 => oid
         2.  APO_SPECIES_FK          => speciesFK
         3.  APO_NODE_START_STAGE_FK => nodeStartFK
         4.  APO_NODE_END_STAGE_FK   => nodeStopFK
         5.  APO_PATH_START_STAGE_FK => pathStartFK
         6.  APO_PATH_END_STAGE_FK   => pathStopFK
         7.  APO_NODE_FK             => nodeFK
         8.  APO_SEQUENCE            => sequence
         9.  APO_DEPTH               => depth
         10. APO_FULL_PATH           => fullPath
         10. APO_FULL_PATH_OIDS      => fullPathOids
         10. APO_FULL_PATH_JSON_HEAD => fullPathJsonHead
         10. APO_FULL_PATH_JSON_TAIL => fullPathJsonTail
         11. APO_IS_PRIMARY          => primary
         12. APO_IS_PRIMARY_PATH     => primaryPath
         13. APO_PARENT_APO_FK       => parentFK
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Partial Full Path:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{DerivedPartOfBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{DerivedPartOfBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_OID" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_SPECIES_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="speciesFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.speciesFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_NODE_START_STAGE_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeStartFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeStartFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_NODE_END_STAGE_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeStopFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeStopFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_PATH_START_STAGE_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="pathStartFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.pathStartFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_PATH_END_STAGE_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="pathStopFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.pathStopFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_NODE_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_SEQUENCE" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="sequence" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.sequence}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_DEPTH" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="depth" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.depth}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_FULL_PATH" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPath" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPath}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_FULL_PATH_OIDS" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPathOids" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPathOids}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_FULL_PATH_JSON_HEAD" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPathJsonHead" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPathJsonHead}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_FULL_PATH_JSON_TAIL" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="fullPathJsonTail" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.fullPathJsonTail}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_IS_PRIMARY" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="primary" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.primary}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_IS_PRIMARY_PATH" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="primaryPath" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.primaryPath}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="APO_PARENT_APO_FK" 
                         actionListener="#{DerivedPartOfBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="parentFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.parentFK}" />
      </h:column>
      
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedPartOfBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedPartOfBackingBean.pageNext}"
                     disabled="#{DerivedPartOfBackingBean.firstRow + DerivedPartOfBackingBean.rowsPerPage >= DerivedPartOfBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedPartOfBackingBean.pageLast}"
                     disabled="#{DerivedPartOfBackingBean.firstRow + DerivedPartOfBackingBean.rowsPerPage >= DerivedPartOfBackingBean.totalRows}" />
      
    <h:outputText value="Page #{DerivedPartOfBackingBean.currentPage} / #{DerivedPartOfBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{DerivedPartOfBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{DerivedPartOfBackingBean.page}"
                      rendered="#{page != DerivedPartOfBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == DerivedPartOfBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{DerivedPartOfBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{DerivedPartOfBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedPartOfBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>