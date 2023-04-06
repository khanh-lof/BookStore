<%-- 
    Document   : shopping
    Created on : Oct 14, 2022, 8:25:06 AM
    Author     : ADMIN
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="khanhlh.tblProduct.TblProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Mart</title>
    </head>
    <body>

        <h1>Web Mart</h1>
        <h1>Choose Book:</h1>
        <c:set var="storage" value="${sessionScope.STORAGE}" />
        <c:if test="${not empty storage}" >
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Add to Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${storage}" varStatus="counter">
                        <c:set var="dto" value="${book.value}" />
                    <form action="addItemController" method="POST">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                ${dto.name}
                                <input type="hidden" name="sku" value="${dto.sku}" /> 
                            </td>
                            <td>${dto.descript}</td>
                            <td>${dto.price}</td>
                            <td>
                                <input type="text" name="cboQuantity" value="" placeholder="Enter Quantity" />
                            </td>
                            <td>
                                <c:if test="${dto.status eq false }">
                                    <font color="red">
                                    Sold out!
                                    </font>
                                </c:if>
                                <c:if test="${dto.status eq true }">
                                    <input type="submit" value="Add Book To Your Cart" name="btAction" />
                                </c:if>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
            <c:set var="quantityFormatErr" value="${requestScope.QUANTITY_FM_ERR}"/>
            <c:if test="${not empty quantityFormatErr}">
                <font color = "red">
                Please Enter a valid number!
                </font>
            </c:if>
                <c:set var="overQuantitytErr" value="${requestScope.OVER_QUANTITY_ERR}"/>
            <c:if test="${not empty overQuantitytErr}">
                <font color = "red">
                Store doesn't have enough ${storage[overQuantitytErr].name} to sell (Max ${storage[overQuantitytErr].quantity})
                </font>
            </c:if>
        <form action="viewCartPage">
            <input type="submit" value="View Your Cart" name="btAction" />
        </form>
    </c:if>
    <%--
    <%
        Map<String, TblProductDTO> storage
                = (Map<String, TblProductDTO>) session.getAttribute("STORAGE");
        if (storage != null) {
    %>
    <table border="1">
        <thead>
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Add to Cart</th>
            </tr>
        </thead>
        <tbody>
            <%
                int count = 0;
                for (TblProductDTO dto : storage.values()) {
            %>
        <form action="DispatchController" method="POST">
            <tr>
                <td><%= ++count%>.</td>
                <td>
                    <%= dto.getName()%>
                    <input type="hidden" name="sku" value="<%= dto.getSku()%>" /> 
                </td>
                <td><%= dto.getDescript()%></td>
                <td><%= dto.getPrice()%></td>
                <td>
                    <input type="text" name="cboQuantity" value="" placeholder="Enter Quantity" />
                </td>
                <td>
                    <%
                        
                    if(dto.getQuantity() == 0){//if storage run out of this book
                        %>
                        Sold out!
                    <%
                    } else{
                    %>
                    <input type="submit" value="Add Book To Your Cart" name="btAction" />
                    <%
                        }
                    %>
                </td>
            </tr>
        </form>
        <%
            }
        %>
    </tbody>
</table>
<form>
    <input type="submit" value="View Your Cart" name="btAction" />
</form>
<%
    }
%> --%>

</body>
</html>
