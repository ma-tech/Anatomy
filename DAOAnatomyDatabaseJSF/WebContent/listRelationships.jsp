<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANA_RELATIONSHIP</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANA_RELATIONSHIP</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Relationships</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANA_RELATIONSHIP 
                             
         Fields: oid      => REL_OID; 
                 typeFK   => REL_RELATIONSHIP_TYPE_FK; 
                 childFK  => REL_CHILD_FK;
                 parentFK => REL_PARENT_FK;
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Parent ID:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{RelationshipBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{RelationshipBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{RelationshipBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="REL_OID" 
                         actionListener="#{RelationshipBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="REL_RELATIONSHIP_TYPE_FK" 
                         actionListener="#{RelationshipBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="typeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.typeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="REL_CHILD_FK" 
                         actionListener="#{RelationshipBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="childFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.childFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="REL_PARENT_FK" 
                         actionListener="#{RelationshipBackingBean.sort}">
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
                     action="#{RelationshipBackingBean.pageFirst}"
                     disabled="#{RelationshipBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{RelationshipBackingBean.pagePrevious}"
                     disabled="#{RelationshipBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{RelationshipBackingBean.pageNext}"
                     disabled="#{RelationshipBackingBean.firstRow + RelationshipBackingBean.rowsPerPage >= RelationshipBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{RelationshipBackingBean.pageLast}"
                     disabled="#{RelationshipBackingBean.firstRow + RelationshipBackingBean.rowsPerPage >= RelationshipBackingBean.totalRows}" />
      
    <h:outputText value="Page #{RelationshipBackingBean.currentPage} / #{RelationshipBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{RelationshipBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{RelationshipBackingBean.page}"
                      rendered="#{page != RelationshipBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == RelationshipBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{RelationshipBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{RelationshipBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{RelationshipBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>