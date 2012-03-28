<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>

    <html xmlns="http://www.w3.org/1999/xhtml">

        <head>
            <title>Login</title>
            <link type="text/css" rel="stylesheet" href="./css/global.css" />
            <script type="text/javascript" src="./scripts/global.js"></script>
        </head>

        <body>

            <h:form id="login" binding="#{login.form}">

                <fieldset>
                
                  <h:panelGroup rendered="#{not login.loggedIn}">

                    <legend>Login</legend>

                    <p><a href="index.html">BACK</a></p>

                    <p>Here you can Login HERE</p>

                    <h:outputLabel for="username" 
                                   value="Username">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputText id="username" 
                                 value="#{login.user.username}" 
                                 required="true"
                                 label="User Name" 
                                 size="30" 
                                 maxlength="15"
                                 disabled="#{login.succes}">
                      <f:validateLength minimum="3" />
                    </h:inputText>
                    <h:message for="username" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="password" 
                                   value="Password">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputSecret id="password" 
                                   value="#{login.user.password}" 
                                   required="true"
                                   label="Password" 
                                   size="30" 
                                   maxlength="15" 
                                   disabled="#{login.succes}"
                                   redisplay="false">
                      <f:validateLength minimum="3" />
                    </h:inputSecret>
                    <h:message for="password" 
                               styleClass="error" />
                    <br/>

                    <h:commandButton value="Login" 
                                     action="#{login.loginUser}"
                                     styleClass="withoutLabel" 
                                     disabled="#{login.succes}" />

                  </h:panelGroup>
                  
                  <h:panelGroup rendered="#{login.loggedIn}">
                  
                    <p><a href="restricted/listFiles.jsf">List Uploaded OBO Files</a></p>
                    <p><a href="restricted/upload.jsf">Upload a New OBO File</a></p>
                  
                    <!-- 
                    <h:outputLabel for="username" 
                                   value="#{login.user.username}">
                    </h:outputLabel>
                     -->
                  
                    <h:commandButton value="Logout" 
                                     action="#{login.logoutUser}"
                                     styleClass="withoutLabel" 
                                     />
                    <br/>

                  </h:panelGroup>

                    <p><h:message for="login" 
                                  infoClass="succes" 
                                  errorClass="error" /></p>
                                  
                </fieldset>
                
            </h:form>
            
            <script type="text/javascript">
                setHighlight('${highlight}');
                setFocus('${!empty focus ? focus : 'login:username'}');
            </script>
            
            
            
        </body>
        
    </html>
    
</f:view>
