<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_NODE</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_NODE</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Nodes</legend>
    <br />
  
    <%-- The sortable datatable 
                                  ANA_NODE 
                                  
         Fields: oid           => ANO_OID; 
                 speciesFK     => ANO_SPECIES_FK; 
                 componentName => ANO_COMPONENT_NAME;
                 primary       => ANO_IS_PRIMARY;
                 group         => ANO_IS_GROUP;
                 publicId      => ANO_PUBLIC_ID;
                 description   => ANO_DESCRIPTION;
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter an EMAPA ID:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{NodeBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchExtra" 
                   value="Enter an Description:" />
                   
    <h:inputText id="searchExtra" 
                 value="#{NodeBackingBean.searchExtra}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{NodeBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{NodeBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_OID" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_SPECIES_FK" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="speciesFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.speciesFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_COMPONENT_NAME" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="componentName" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.componentName}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_IS_PRIMARY" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="primary" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.primary}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_IS_GROUP" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="group" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.group}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_PUBLIC_ID" 
                         actionListener="#{NodeBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="publicId" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.publicId}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="ANO_DESCRIPTION" 
                         actionListener="#{NodeBackingBean.sort}">
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
                     action="#{NodeBackingBean.pageFirst}"
                     disabled="#{NodeBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{NodeBackingBean.pagePrevious}"
                     disabled="#{NodeBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{NodeBackingBean.pageNext}"
                     disabled="#{NodeBackingBean.firstRow + NodeBackingBean.rowsPerPage >= NodeBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{NodeBackingBean.pageLast}"
                     disabled="#{NodeBackingBean.firstRow + NodeBackingBean.rowsPerPage >= NodeBackingBean.totalRows}" />
      
    <h:outputText value="Page #{NodeBackingBean.currentPage} / #{NodeBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{NodeBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{NodeBackingBean.page}"
                      rendered="#{page != NodeBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == NodeBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{NodeBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{NodeBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{NodeBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>