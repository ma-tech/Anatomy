<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_EDITOR</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_EDITOR</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Nodes</legend>
    <br />
  
    <%-- The sortable datatable 
                                  ANA_EDITOR 
                                  
         Fields: oid  => EDI_OID; 
                 name => EDI_NAME; 

    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter an Editor:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{EditorBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{EditorBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
        
    <h:dataTable value="#{EditorBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="EDI_OID" 
                         actionListener="#{EditorBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="EDI_NAME" 
                         actionListener="#{EditorBackingBean.sort}">
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
                     action="#{EditorBackingBean.pageFirst}"
                     disabled="#{EditorBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{EditorBackingBean.pagePrevious}"
                     disabled="#{EditorBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{EditorBackingBean.pageNext}"
                     disabled="#{EditorBackingBean.firstRow + EditorBackingBean.rowsPerPage >= EditorBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{EditorBackingBean.pageLast}"
                     disabled="#{EditorBackingBean.firstRow + EditorBackingBean.rowsPerPage >= EditorBackingBean.totalRows}" />
      
    <h:outputText value="Page #{EditorBackingBean.currentPage} / #{EditorBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{EditorBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{EditorBackingBean.page}"
                      rendered="#{page != EditorBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == EditorBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{EditorBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{EditorBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{EditorBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>