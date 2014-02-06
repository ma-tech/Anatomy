<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_VERSION</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_VERSION</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Nodes</legend>
    <br />
  
    <%-- The sortable datatable 
         ANA_VERSION 
         Fields: oid      => VER_OID
                 number   => VER_NUMBER
                 date     => VER_DATE
                 comments => VER_COMMENTS 
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Date:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{VersionBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchExtra" 
                   value="Enter a Comment:" />
                   
    <h:inputText id="searchExtra" 
                 value="#{VersionBackingBean.searchExtra}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{VersionBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{VersionBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="VER_OID" 
                         actionListener="#{VersionBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="VER_NUMBER" 
                         actionListener="#{VersionBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="number" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.number}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="VER_DATE" 
                         actionListener="#{VersionBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="date" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.date}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="VER_COMMENTS" 
                         actionListener="#{VersionBackingBean.sort}">
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
                     action="#{VersionBackingBean.pageFirst}"
                     disabled="#{VersionBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{VersionBackingBean.pagePrevious}"
                     disabled="#{VersionBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{VersionBackingBean.pageNext}"
                     disabled="#{VersionBackingBean.firstRow + VersionBackingBean.rowsPerPage >= VersionBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{VersionBackingBean.pageLast}"
                     disabled="#{VersionBackingBean.firstRow + VersionBackingBean.rowsPerPage >= VersionBackingBean.totalRows}" />
      
    <h:outputText value="Page #{VersionBackingBean.currentPage} / #{VersionBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{VersionBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{VersionBackingBean.page}"
                      rendered="#{page != VersionBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == VersionBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{VersionBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{VersionBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{VersionBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>