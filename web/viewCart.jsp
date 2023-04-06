<%-- 
    Document   : viewCart
    Created on : Oct 14, 2022, 9:06:59 AM
    Author     : ADMIN
--%>

<%@page import="khanhlh.tblProduct.TblProductDTO"%>
<%@page import="java.util.Map"%>
<%@page import="khanhlh.cart.CartObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Mart</title>
    </head>
    <body>
        <h1>Web Mart!</h1>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}">
            <c:set var="items" value="${cart.items}" />
            <c:if test="${not empty items}" >
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>
                    <form action="removeItemController">
                        <c:set var="storage" value="${sessionScope.STORAGE}"/>
                        <c:forEach var="item" items="${items}" varStatus="counter">
                            <c:set var="value" value="${item.value}" />
                            <c:set var="dto" value="${storage[item.key]}" />
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.name}</td>
                                <td>${value}</td>
                                <td>
                                    <input type="checkbox" name="chkItem" value="${item.key}" /> 
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3"><a href="shopping.jsp">Click here to buy more Book</a></td>
                            <td><input type="submit" value="Remove Selected Item" name="btAction" /> </td>
                        </tr>
                        </tbody>
                </table>
            </form>
            <form action="checkOutController">
                <input type="submit" value="Check out" name="btAction" /> 
            </form>


        </c:if>
    </c:if>
    <c:if test="${empty items}">
        <h2>
            No cart is existed!
        </h2>
        <a href="shopping.jsp">Click here to buy more Book</a>
    </c:if>

    <%--
    <%
        //1. Cus goes to his/her placement
        if (session != null) {
            //2. Cus takes his/her cart
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart != null) {
                //3. Cus takes item
                Map<String, Integer> items = cart.getItems();
                if (items != null) {
                    //4. System traverse to show
    %>
    <table border="1">
        <thead>
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
        </thead>

            <tbody>
            <form action="DispatchController">
                <%
                    int count = 0;
                    Map<String, TblProductDTO> storage
                            = (Map<String, TblProductDTO>) session.getAttribute("STORAGE");
                    for (String key : items.keySet()) {
                        Integer value = items.get(key);
                %>
                <tr>
                    <td><%= ++count%></td>
                    <td><%= storage.get(key).getName()%></td>
                    <td><%= value%></td>
                    <td>
                        <input type="checkbox" name="chkItem" value="<%= key%>" /> 
                    </td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="3"><a href="shopping.jsp">Click here to buy more Book</a></td>
                    <td><input type="submit" value="Remove Selected Item" name="btAction" /> </td>
                </tr>

                </tbody>
        </table>
        <input type="submit" value="Check out" name="btAction" />
    </form>
    <%
                    return;
                } //item is valid
            }// cart is valid
        }//session is valid


    %>
    <h2>
        No cart is existed!
    </h2> --%>
</body>
</html>
