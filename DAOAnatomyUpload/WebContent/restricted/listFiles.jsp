<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>Anatomy - List Uploaded OBO Files</title>
 
  <link type="text/css" rel="stylesheet" href="../css/global.css">
  <script type="text/javascript" src="../scripts/global.js"></script>

</head>

<body>

  <h:form id="form">

    <h1>Anatomy - List Uploaded OBO Files</h1>
   
    <fieldset>
  
    <h:commandButton value="Logout" 
                   actionListener="#{OBOFileBackingBean.logout}"
                   />
  
    <p><a href="../index.html">BACK</a></p>
  
    <p><a href="upload.jsf">Upload a New OBO File</a></p>
  
    <legend>Browse Uploaded OBO Files</legend>
    <br />
  
    <%-- The sortable datatable 
                                     file_test
         Fields: 
           id
           name
           contenttype
           content       - blob
           contentlength
    --%>

    <h:dataTable value="#{OBOFileBackingBean.dataList}" 
                 var="item">
                
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Id" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_OID" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.oid}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Name" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_FILE_NAME" />
          </h:commandLink>
        </f:facet>
        <h:outputLink value="obo?oid=#{item.oid}">
          <h:outputText value="#{item.name}" />
        </h:outputLink>
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Date" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_FILE_CONTENT_DATE" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.contentdate}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Status" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_FILE_VALIDATION" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.validation}" />
        <h:outputText value=" - " 
                      rendered="#{item.validation == 'NEW'}" />
        <h:commandLink value="VALIDATE!" 
                         actionListener="#{OBOFileBackingBean.validate}"
                         rendered="#{item.validation == 'NEW'}">
          <f:attribute name="validateField" 
                         value="#{item.oid}" />
        </h:commandLink>
        <h:outputText value=" - " 
                      rendered="#{item.validation == 'VALIDATED'}" />
        <h:commandLink value="UPDATE!" 
                         actionListener="#{OBOFileBackingBean.update}"
                         rendered="#{item.validation == 'VALIDATED'}">
          <f:attribute name="validateField" 
                         value="#{item.oid}" />
        </h:commandLink>
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Author" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_FILE_AUTHOR" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.author}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Text Report" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_TEXT_REPORT_NAME" />
          </h:commandLink>
        </f:facet>
        <h:outputLink value="txt?oid=#{item.oid}">
          <h:outputText value="#{item.textreportname}" />
        </h:outputLink>
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Date" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_TEXT_REPORT_DATE" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.textreportdate}" />
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="PDF Report" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_PDF_REPORT_NAME" />
          </h:commandLink>
        </f:facet>
        <h:outputLink value="pdf?oid=#{item.oid}">
          <h:outputText value="#{item.pdfreportname}" />
        </h:outputLink>
      </h:column>
      
      <h:column>
        <f:facet name="header">
          <h:commandLink value="Date" 
                         actionListener="#{OBOFileBackingBean.sort}">
            <f:attribute name="sortField" 
                         value="AOF_PDF_REPORT_DATE" />
          </h:commandLink>
        </f:facet>
        <h:outputText value="#{item.pdfreportdate}" />
      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{OBOFileBackingBean.pageFirst}"
                     disabled="#{OBOFileBackingBean.firstRow == 0}" />
                      
    <h:commandButton value="prev" 
                     action="#{OBOFileBackingBean.pagePrevious}"
                     disabled="#{OBOFileBackingBean.firstRow == 0}" />
    
    <h:commandButton value="next" 
                     action="#{OBOFileBackingBean.pageNext}"
                     disabled="#{OBOFileBackingBean.firstRow + OBOFileBackingBean.rowsPerPage >= OBOFileBackingBean.totalRows}" />
                     
    <h:commandButton value="last" 
                     action="#{OBOFileBackingBean.pageLast}"
                     disabled="#{OBOFileBackingBean.firstRow + OBOFileBackingBean.rowsPerPage >= OBOFileBackingBean.totalRows}" />
      
    <h:outputText value="Page #{OBOFileBackingBean.currentPage} / #{OBOFileBackingBean.totalPages}" />
    <br />

    <%-- The paging links --%>
    <t:dataList value="#{OBOFileBackingBean.pages}" 
                var="page">
                
      <h:commandLink value="#{page}" 
                     actionListener="#{OBOFileBackingBean.page}"
                      rendered="#{page != OBOFileBackingBean.currentPage}" />
                      
      <h:outputText value="<b>#{page}</b>" 
                    escape="false"
                    rendered="#{page == OBOFileBackingBean.currentPage}" />
    </t:dataList>
    <br />

    <%-- Set rows per page --%>
    <h:outputLabel for="rowsPerPage" 
                   value="Rows per page" />
                   
    <h:inputText id="rowsPerPage" 
                 value="#{OBOFileBackingBean.rowsPerPage}" 
                 size="3"
                 maxlength="3" />
                 
    <h:commandButton value="Set" 
                     action="#{OBOFileBackingBean.pageFirst}" />
                                          
    <h:message for="rowsPerPage" 
               errorStyle="color: red;" />

    <%-- Cache bean with data list, paging and sorting variables for next request --%>
    <t:saveState value="#{OBOFileBackingBean}" />
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>