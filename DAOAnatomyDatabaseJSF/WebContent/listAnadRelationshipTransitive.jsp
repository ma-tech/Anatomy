<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_RELATIONSHIP_TRANSITIVE</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_RELATIONSHIP_TRANSITIVE</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Relationship Transitives</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANAD_RELATIONSHIP_TRANSITIVE 
                             
         Fields: 
         1. RTR_OID                   => int(10)
         2. RTR_RELATIONSHIP_TYPE_FK  => varchar(20)
         3. RTR_DESCENDENT_FK         => int(10)
         4. RTR_ANCESTOR_FK           => int(10)
	
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Descendant ID:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{DerivedRelationshipTransitiveBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{DerivedRelationshipTransitiveBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="RTR_OID" 
                         actionListener="#{DerivedRelationshipTransitiveBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="oid" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="RTR_RELATIONSHIP_TYPE_FK" 
                         actionListener="#{DerivedRelationshipTransitiveBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="relTypeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.relTypeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="RTR_DESCENDENT_FK" 
                         actionListener="#{DerivedRelationshipTransitiveBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="descendantFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.descendantFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="RTR_ANCESTOR_FK" 
                         actionListener="#{DerivedRelationshipTransitiveBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="ancestorFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.ancestorFK}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pageFirst}"
                     disabled="#{DerivedRelationshipTransitiveBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pagePrevious}"
                     disabled="#{DerivedRelationshipTransitiveBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pageNext}"
                     disabled="#{DerivedRelationshipTransitiveBackingBean.firstRow + DerivedRelationshipTransitiveBackingBean.rowsPerPage >= DerivedRelationshipTransitiveBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pageLast}"
                     disabled="#{DerivedRelationshipTransitiveBackingBean.firstRow + DerivedRelationshipTransitiveBackingBean.rowsPerPage >= DerivedRelationshipTransitiveBackingBean.totalRows}" />
      
    <h:outputText value="Page #{DerivedRelationshipTransitiveBackingBean.currentPage} / #{DerivedRelationshipTransitiveBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{DerivedRelationshipTransitiveBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{DerivedRelationshipTransitiveBackingBean.page}"
                      rendered="#{page != DerivedRelationshipTransitiveBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == DerivedRelationshipTransitiveBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{DerivedRelationshipTransitiveBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{DerivedRelationshipTransitiveBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedRelationshipTransitiveBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>