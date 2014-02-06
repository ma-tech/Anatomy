<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_PERSPECTIVE_AMBIT</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_PERSPECTIVE_AMBIT</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Perspective Ambits</legend>
    <br />
  
    <%-- The sortable datatable 
         ANA_PERSPECTIVE_AMBIT 
         Fields: oid           => PAM_OID; 
                 perspectiveFK => PAM_PERSPECTIVE_FK; 
                 nodeFK        => PAM_NODE_FK;
                 isStart       => PAM_IS_START;
                 isStop        => PAM_IS_STOP;
                 comments      => PAM_COMMENTS;

    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Perspective:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{PerspectiveAmbitBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchExtra" 
                   value="Enter a Comment:" />
                   
    <h:inputText id="searchExtra" 
                 value="#{PerspectiveAmbitBackingBean.searchExtra}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{PerspectiveAmbitBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{PerspectiveAmbitBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_OID" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_PERSPECTIVE_FK" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="perspectiveFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.perspectiveFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_NODE_FK" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_IS_START" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="isStart" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.isStart}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_IS_STOP" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="isStop" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.isStop}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PAM_COMMENTS" 
                         actionListener="#{PerspectiveAmbitBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="comments" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.comments}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{PerspectiveAmbitBackingBean.pageFirst}"
                     disabled="#{PerspectiveAmbitBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{PerspectiveAmbitBackingBean.pagePrevious}"
                     disabled="#{PerspectiveAmbitBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{PerspectiveAmbitBackingBean.pageNext}"
                     disabled="#{PerspectiveAmbitBackingBean.firstRow + PerspectiveAmbitBackingBean.rowsPerPage >= PerspectiveAmbitBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{PerspectiveAmbitBackingBean.pageLast}"
                     disabled="#{PerspectiveAmbitBackingBean.firstRow + PerspectiveAmbitBackingBean.rowsPerPage >= PerspectiveAmbitBackingBean.totalRows}" />
      
    <h:outputText value="Page #{PerspectiveAmbitBackingBean.currentPage} / #{PerspectiveAmbitBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{PerspectiveAmbitBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{PerspectiveAmbitBackingBean.page}"
                      rendered="#{page != PerspectiveAmbitBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == PerspectiveAmbitBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{PerspectiveAmbitBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{PerspectiveAmbitBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{PerspectiveAmbitBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>