<%-- 
    Document   : CreateNewAccount
    Created on : Oct 25, 2022, 7:33:20 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>New Account</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="signUpController" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}" />
            Username* <input type="text" name="txtUsername" 
                             value="${param.txtUsername}" />(6-20 chars)<br/>
            <c:if test="${not empty errors.usernameLengthError}">
                <font color ="red"> 
                ${errors.usernameLengthError}<br/>
                </font>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color ="red"> 
                ${errors.usernameIsExisted}<br/>
                </font>
            </c:if>
            <!--catch tiep 4 loi -->
            Password* <input type="password" name="txtPassword" value="" />(6-20 chars)<br/>
            <c:if test="${not empty errors.passwordLenghtError}">
                <font color ="red"> 
                ${errors.passwordLenghtError}<br/>
                </font>
            </c:if>
            Confirm Password* <input type="password" name="txtConfirm" value="" /> <br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color ="red"> 
                ${errors.confirmNotMatched}<br/>
                </font>
            </c:if>
            Full Name <input type="text" name="txtFullname" value="${param.txtFullname}" />(2-50 chars)<br/>
            <c:if test="${not empty errors.fullNameLengthError}">
                <font color ="red"> 
                ${errors.fullNameLengthError}<br/>
                </font>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
    </body>
</html>
