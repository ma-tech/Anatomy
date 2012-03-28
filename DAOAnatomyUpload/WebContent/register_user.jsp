<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <title>Register</title>
            <link type="text/css" rel="stylesheet" href="./css/global.css" />
            <script type="text/javascript" src="./scripts/global.js"></script>
        </head>
        <body>
            <h:form id="register" binding="#{register.form}">
                <fieldset>
                    <legend>Register</legend>
                    
                    <p><a href="index.html">BACK</a></p>
                    
                    <p>Register yourself HERE.</p>

                    <h:outputLabel for="username" 
                                   value="Username:">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputText id="username" 
                                 value="#{register.user.username}" 
                                 required="true"
                                 label="Username" 
                                 size="30" 
                                 maxlength="15"
                                 disabled="#{register.succes}" 
                                 validator="#{register.validateUsername}">
                      <f:validateLength minimum="3" />
                    </h:inputText>
                    <h:message for="username" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="password" 
                                   value="Password:">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputSecret id="password" 
                                   value="#{register.user.password}" 
                                   required="true"
                                   label="Password" 
                                   size="30" 
                                   maxlength="15" 
                                   redisplay="true"
                                   disabled="#{register.succes}">
                      <f:validateLength minimum="3" />
                    </h:inputSecret>
                    <h:message for="password" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="confirm" 
                                   value="Confirm Password:">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputSecret id="confirm" 
                                   value="#{register.user.password}" 
                                   required="true"
                                   label="Confirm password" 
                                   size="30" 
                                   maxlength="15" 
                                   redisplay="true"
                                   disabled="#{register.succes}" 
                                   validator="#{register.validatePassword}">
                      <f:attribute name="passwordId" 
                                   value="register:password" />
                      <f:validateLength minimum="3" />
                    </h:inputSecret>
                    <h:message for="confirm" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="passcode" 
                                   value="Enter Pass Code:">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputSecret id="passcode" 
                                   required="true"
                                   label="Pass Code" 
                                   size="30" 
                                   maxlength="15" 
                                   redisplay="true"
                                   disabled="#{register.succes}" 
                                   validator="#{register.validatePasscode}">
                      <f:attribute name="passCode" 
                                   value="register:passcode" />
                      <f:validateLength minimum="3" />
                    </h:inputSecret>
                    <h:message for="passcode" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="email" 
                                   value="Email address">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputText id="email" 
                                 value="#{register.user.email}" 
                                 required="true"
                                 label="Email address" 
                                 size="30" 
                                 maxlength="60"
                                 disabled="#{register.succes}" 
                                 validator="#{register.validateEmail}" />
                    <h:message for="email" 
                               styleClass="error" />
                    <br/>

                    <h:outputLabel for="organisation" 
                                   value="Organisation">
                      <span class="required">*</span>
                    </h:outputLabel>
                    <h:inputText id="organisation" 
                                 value="#{register.user.organisation}" 
                                 required="true"
                                 label="Organisation" 
                                 size="30" 
                                 maxlength="30"
                                 disabled="#{register.succes}" />
                    <h:message for="organisation" 
                               styleClass="error" />
                    <br/>

                    <h:commandButton value="Register" 
                                     action="#{register.registerUser}"
                                     styleClass="withoutLabel" 
                                     disabled="#{register.succes}" />
                    <br/>

                    <p><h:message for="register" 
                                  infoClass="succes" 
                                  errorClass="error" /></p>
                    
                </fieldset>
            </h:form>
            <script type="text/javascript">
                setHighlight('${highlight}');
                setFocus('${!empty focus ? focus : 'register:username'}');
            </script>
        </body>
    </html>
</f:view>
