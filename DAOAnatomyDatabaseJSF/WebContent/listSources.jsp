<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_SOURCE</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_SOURCE</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Nodes</legend>
    <br />
  
    <%-- The sortable datatable 
         ANA_SOURCE 
         Fields: oid      => SRC_OID; 
                 name     => SRC_NAME; 
                 authors  => SRC_AUTHORS;
                 formatFK => SRC_FORMAT_FK;
                 year     => SRC_YEAR;
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Name:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{SourceBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchExtra" 
                   value="Enter an Author:" />
                   
    <h:inputText id="searchExtra" 
                 value="#{SourceBackingBean.searchExtra}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{SourceBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{SourceBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SRC_OID" 
                         actionListener="#{SourceBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SRC_NAME" 
                         actionListener="#{SourceBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="name" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.name}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SRC_AUTHORS" 
                         actionListener="#{SourceBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="authors" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.authors}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SRC_FORMAT_FK" 
                         actionListener="#{SourceBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="formatFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.formatFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SRC_YEAR" 
                         actionListener="#{SourceBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="year" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.year}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{SourceBackingBean.pageFirst}"
                     disabled="#{SourceBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{SourceBackingBean.pagePrevious}"
                     disabled="#{SourceBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{SourceBackingBean.pageNext}"
                     disabled="#{SourceBackingBean.firstRow + SourceBackingBean.rowsPerPage >= SourceBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{SourceBackingBean.pageLast}"
                     disabled="#{SourceBackingBean.firstRow + SourceBackingBean.rowsPerPage >= SourceBackingBean.totalRows}" />
      
    <h:outputText value="Page #{SourceBackingBean.currentPage} / #{SourceBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{SourceBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{SourceBackingBean.page}"
                      rendered="#{page != SourceBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == SourceBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{SourceBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{SourceBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{SourceBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>