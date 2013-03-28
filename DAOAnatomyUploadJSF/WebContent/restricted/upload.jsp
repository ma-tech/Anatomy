<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>


<!DOCTYPE html>

<f:view>
  <html lang="en">
    <head>
      <title>Anatomy - Upload An OBO File</title>
      
      <link type="text/css" rel="stylesheet" href="../css/global.css">
      <script type="text/javascript" src="../scripts/global.js"></script>
    </head>

    <body>

    <h1>Anatomy - Upload An OBO File</h1>


    <fieldset>
    
    <legend>Upload An OBO File</legend>
    <br />
  
      <h:form id="uploadFileForm" 
              enctype="multipart/form-data">

        <h:panelGroup>
        
          <h:commandButton value="Logout" 
                   actionListener="#{UploadFileForm.logout}"
                   />
  
          <p><a href="../index.html">BACK</a></p>

          <p><a href="listFiles.jsf">List Uploaded Files</a></p>

          <h:outputLabel for="file" 
                         value="Select file">
           <span class="required">*</span>
          </h:outputLabel>

          <t:inputFileUpload id="file" 
                             value="#{UploadFileForm.uploadedFile}" />
          <h:message for="file" 
                     styleClass="error" />
          <br/>

          <h:commandButton value="Upload File" 
                           action="#{UploadFileForm.upload}" />

        </h:panelGroup>
        
        <p><h:message for="uploadFileForm" 
                      infoClass="succes" 
                      errorClass="error" /></p>
                      
      </h:form>
    </fieldset>
                

    </body>
  </html>
</f:view>
