<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF_PERSPECTIVES</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF_PERSPECTIVES</h1>
   
    <fieldset>
  
    <p><a href="rawData.html">BACK</a></p>
  
    <legend>Browse Derived Part Of Perspectives</legend>
    <br />
  
    <%-- The sortable datatable 
                             ANA_RELATIONSHIP 
                             
         Fields: perspectiveFK => POP_PERSPECTIVE_FK; 
                 partOfFK      => POP_APO_FK; 
                 ancestor      => POP_IS_ANCESTOR;
                 nodeFK        => POP_NODE_FK;
    --%>
    
    <%-- Set Search Term --%>
    <h:outputLabel for="searchTerm" 
                   value="Enter a Parent Node OID:" />
                   
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfPerspectivesBackingBean.searchTerm}" 
                 size="15"
                 maxlength="15" />
    
    <br />
                 
    <h:outputLabel for="searchTable" 
                   value="Select a Source:" />
                   
    <h:selectOneMenu id="searchTable" value="#{DerivedPartOfPerspectivesBackingBean.searchPerspective}">
      <f:selectItem id="item0" itemLabel="Adult Kidney (GenePaint)" itemValue="Adult Kidney (GenePaint)" />
      <f:selectItem id="item1" itemLabel="Renal" itemValue="Renal" />
      <f:selectItem id="item2" itemLabel="Urogenital" itemValue="Urogenital" />
      <f:selectItem id="item3" itemLabel="Whole mouse" itemValue="Whole mouse" />
    </h:selectOneMenu>
    
    <br />
                 
    <h:commandButton value="Search" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pageFirst}" />
                                          
    <h:message for="searchTerm" 
               errorStyle="color: red;" />

    <br/>
    <br/>
    
    <h:dataTable value="#{DerivedPartOfPerspectivesBackingBean.dataList}" 
                 var="item">
                 
      <h:column>
        <f:facet name="header">
          <h:commandLink value="POP_PERSPECTIVE_FK" 
                         actionListener="#{DerivedPartOfPerspectivesBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="perspectiveFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.perspectiveFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="POP_IS_ANCESTOR" 
                         actionListener="#{DerivedPartOfPerspectivesBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="ancestor" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.ancestor}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="POP_NODE_FK" 
                         actionListener="#{DerivedPartOfPerspectivesBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="nodeFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.nodeFK}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="POP_APO_FK" 
                         actionListener="#{DerivedPartOfPerspectivesBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="partOfFK" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.partOfFK}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfPerspectivesBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfPerspectivesBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pageNext}"
                     disabled="#{DerivedPartOfPerspectivesBackingBean.firstRow + DerivedPartOfPerspectivesBackingBean.rowsPerPage >= DerivedPartOfPerspectivesBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pageLast}"
                     disabled="#{DerivedPartOfPerspectivesBackingBean.firstRow + DerivedPartOfPerspectivesBackingBean.rowsPerPage >= DerivedPartOfPerspectivesBackingBean.totalRows}" />
      
    <h:outputText value="Page #{DerivedPartOfPerspectivesBackingBean.currentPage} / #{DerivedPartOfPerspectivesBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{DerivedPartOfPerspectivesBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{DerivedPartOfPerspectivesBackingBean.page}"
                      rendered="#{page != DerivedPartOfPerspectivesBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == DerivedPartOfPerspectivesBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{DerivedPartOfPerspectivesBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <%-- AND HERE --%>
    <h:commandButton value="Set" 
                     action="#{DerivedPartOfPerspectivesBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{DerivedPartOfPerspectivesBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>