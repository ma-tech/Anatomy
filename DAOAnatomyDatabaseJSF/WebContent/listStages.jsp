<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_STAGE</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_STAGE</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Stages</legend>
    <br />
  
    <%-- The sortable datatable 
                                ANA_STAGE
                                
         Fields: oid         => STG_OID; 
                 speciesFK   => STG_SPECIES_FK;  
                 name        => STG_NAME;
                 sequence    => STG_SEQUENCE;
                 description => STG_DESCRIPTION;
                 extraText   => STG_SHORT_EXTRA_TEXT;
                 publicId    => STG_PUBLIC_ID;
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter an Alternative Stage Name:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{StageBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{StageBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
     
    <h:dataTable value="#{StageBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_OID" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_SPECIES_FK" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="speciesFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.speciesFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_NAME" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="name" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.name}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_SEQUENCE" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="sequence" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.sequence}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_DESCRIPTION" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="description" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.description}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_SHORT_EXTRA_TEXT" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="extraText" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.extraText}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="STG_PUBLIC_ID" 
                         actionListener="#{StageBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="publicId" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.publicId}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{StageBackingBean.pageFirst}"
                     disabled="#{StageBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{StageBackingBean.pagePrevious}"
                     disabled="#{StageBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{StageBackingBean.pageNext}"
                     disabled="#{StageBackingBean.firstRow + StageBackingBean.rowsPerPage >= StageBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{StageBackingBean.pageLast}"
                     disabled="#{StageBackingBean.firstRow + StageBackingBean.rowsPerPage >= StageBackingBean.totalRows}" />
      
    <h:outputText value="Page #{StageBackingBean.currentPage} / #{StageBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{StageBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{StageBackingBean.page}"
                      rendered="#{page != StageBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == StageBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{StageBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{StageBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{StageBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>