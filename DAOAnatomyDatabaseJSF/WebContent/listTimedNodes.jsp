<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_TIMED_NODE</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_TIMED_NODE</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse TimedNodes</legend>
    <br />
  
    <%-- The sortable datatable
                                    ANA_TIMED_NODE 
                                    
         Fields: oid             => ATN_OID; 
                 nodeFK          => ATN_NODE_FK; 
                 stageFK         => ATN_STAGE_FK;
                 stageModifierFK => ATN_STAGE_MODIFIER_FK;
                 publicId        => ATN_PUBLIC_ID;
                 publicEmapaId   => ANO_PUBLIC_ID;
                 stageName       => STG_NAME;
                 stageSeq        => STG_SEQUENCE;
    --%>
    
        <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter an EMAP ID:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{TimedNodeBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{TimedNodeBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
     
    <h:dataTable value="#{TimedNodeBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ATN_OID" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ATN_NODE_FK" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ATN_STAGE_FK" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="stageFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.stageFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ATN_STAGE_MODIFIER_FK" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="stageModifierFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.stageModifierFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ATN_PUBLIC_ID" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="publicId" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.publicId}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_PUBLIC_ID" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="publicEmapaId" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.publicEmapaId}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_NAME" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="stageName" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.stageName}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_SEQUENCE" 
                         actionListener="#{TimedNodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="stageSeq" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.stageSeq}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{TimedNodeBackingBean.pageFirst}"
                     disabled="#{TimedNodeBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{TimedNodeBackingBean.pagePrevious}"
                     disabled="#{TimedNodeBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{TimedNodeBackingBean.pageNext}"
                     disabled="#{TimedNodeBackingBean.firstRow + TimedNodeBackingBean.rowsPerPage >= TimedNodeBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{TimedNodeBackingBean.pageLast}"
                     disabled="#{TimedNodeBackingBean.firstRow + TimedNodeBackingBean.rowsPerPage >= TimedNodeBackingBean.totalRows}" />
      
    <h:outputText value="Page #{TimedNodeBackingBean.currentPage} / #{TimedNodeBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{TimedNodeBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{TimedNodeBackingBean.page}"
                      rendered="#{page != TimedNodeBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == TimedNodeBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{TimedNodeBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{TimedNodeBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{TimedNodeBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>