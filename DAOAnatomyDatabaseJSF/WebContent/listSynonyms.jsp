<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_SYNONYM</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_SYNONYM</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Synonyms</legend>
    <br />
  
    <%-- The sortable datatable: 
                            ANA_SYNONYM 
         Fields: oid     => SYN_OID; 
                 thingFK => SYN_OBJECT_FK; 
                 name    => SYN_SYNONYM; 
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a partial Synonym String:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{SynonymBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{SynonymBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{SynonymBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SYN_OID" 
                         actionListener="#{SynonymBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SYN_OBJECT_FK" 
                         actionListener="#{SynonymBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="thingFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.thingFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="SYN_SYNONYM" 
                         actionListener="#{SynonymBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="name" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.name}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{SynonymBackingBean.pageFirst}"
                     disabled="#{SynonymBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{SynonymBackingBean.pagePrevious}"
                     disabled="#{SynonymBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{SynonymBackingBean.pageNext}"
                     disabled="#{SynonymBackingBean.firstRow + SynonymBackingBean.rowsPerPage >= SynonymBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{SynonymBackingBean.pageLast}"
                     disabled="#{SynonymBackingBean.firstRow + SynonymBackingBean.rowsPerPage >= SynonymBackingBean.totalRows}" />
      
    <h:outputText value="Page #{SynonymBackingBean.currentPage} / #{SynonymBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{SynonymBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{SynonymBackingBean.page}"
                      rendered="#{page != SynonymBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == SynonymBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{SynonymBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{SynonymBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{SynonymBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>