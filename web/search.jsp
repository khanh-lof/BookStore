<%-- 
    Document   : search
    Created on : Oct 6, 2022, 9:51:58 PM
    Author     : ADMIN
--%>

<%--<%@page import="khanhlh.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%--xoa dong build--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font color = "red">
        Welcome, ${sessionScope.USER.lastname}
        </font>
        <form action="logOutController">
            <input type="submit" value="Log Out" name="btAction" /><br/>
        </form>
        <h1>Search Page</h1>
        <form action="searchController">
            Search Value <input type="text" name="txtSearchValue" 
                                value="${param.txtSearchValue}"> <br/>
            <input type="submit" value="Search" name="btAction" />
        </form><br/>
        <c:set var="searchValue" value="${param.txtSearchValue}"/><%--bo / build--%>
        <c:set var="updateError" value="${param.update}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <form action="updateController" method="POST">

                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="password" name="txtPassword" value="" />
                                </td>
                                <td>
                                    ${dto.lastname}
                                </td>
                                <td>

                                    <input type="checkbox" name="txtRole" value="ON" 
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                           />


                                </td>
                                <td>
                                    <c:url var="urlRewriting" value="deleteController">
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="LastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${urlRewriting}">Delete</a>
                                </td>
                                <td>
                                    <input type="hidden" name="LastSearchValue" value="${searchValue}" />
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty updateError}">
                <font color ="red"> 
                Password is required input from 6 to 20 characters<br/>
                </font>
            </c:if>
        </c:if>
        <c:if test="${empty result}">
            <h2>No record matched!</h2>
        </c:if>
    </c:if>
    <%--<%
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String username = "";
            for (Cookie cookie : cookies) {
                String temp = cookie.getName();
                if (!temp.equals("JSESSIONID")) {
                    username = temp;
                }
            }
    %>
    Welcome, <%= username%>
    <%
    } else {
    %>
    Welcome, <%= request.getParameter("txtUsername")%>
    <%
        }
    %>
    </font>
    <h1>Search Page</h1>
    <form action="DispatchController">
        Search Value <input type="text" name="txtSearchValue" 
                            <% if (request.getParameter("txtSearchValue") != null) {%>
                            value="<%= request.getParameter("txtSearchValue")%>"
                            <%} else {
                            %>
                            value=""
                            <%
                                } %>" /><br/>
        <input type="submit" value="Search" name="btAction" />
    </form><br/>

        <%
            String searchValue = request.getParameter("txtSearchValue");
            if (searchValue != null) {
                List<RegistrationDTO> result
                        = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                if (result != null) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Full name</th>
                    <th>Role</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 0;
                    for (RegistrationDTO dto : result) {
                        String urlRewriting = "DispatchController"
                                + "?btAction=delete"
                                + "&pk=" + dto.getUsername()
                                + "&LastSearchValue=" + searchValue;
                %>
            <form action="DispatchController" method="POST">
                <tr>
                    <td>
                        <%=++count%>
                        .</td>
                    <td>
                        <%= dto.getUsername()%>
                        <input type="hidden" name="txtUsername" value="<%= dto.getUsername()%>" />
                    </td>
                    <td>
                        <input type="text" name="txtPassword" value="<%= dto.getPassword()%>" />
                    </td>
                    <td>
                        <%= dto.getLastname()%>
                    </td>
                    <td>
                        <input type="checkbox" name="txtRole" value="ON"
                               <%
                                   if (dto.isRole()) {
                               %>
                               checked="checked"
                               <%
                                   }
                               %>
                               />
                    </td>
                    <td>
                        <a href="<%= urlRewriting%>">Delete</a>
                    </td>
                    <td>
                        <input type="hidden" name="LastSearchValue" value="<%= searchValue%>" />
                        <input type="submit" value="Update" name="btAction" />
                    </td>
                </tr> 
            </form>   
            <%
                }//end for dto is result
            %>--%>
    <%--</tbody>
    </table>--%>

    <%--<%
    } else {
    %>--%>
    <%--<h2>
        No record is matched!!!
    </h2>--%>
    <%--%><%
            }
        }//end if search Value has valid
    %>--%>
</body>
</html>
