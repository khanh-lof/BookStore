/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khanhlh.cart.CartObject;
import khanhlh.order.OrderDAO;
import khanhlh.orderDetail.OrderDetailDAO;
import khanhlh.orderDetail.OrderDetailDTO;
import khanhlh.tblProduct.TblProductDAO;
import khanhlh.tblProduct.TblProductDTO;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

//    private final String ERROR_PAGE = "error.html";
//    private final String LOGIN_PAGE = "login.html";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings(value = "unchecked")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = this.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyApplicationConstants.CheckOutFeatures.ERROR_PAGE);
        try {
            //1.Cus goes to his/her cart placement
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Cus takes cart
                CartObject cart = (CartObject) session.getAttribute("CART");

                if (cart != null) {
                    Map<String, TblProductDTO> storage = (Map<String, TblProductDTO>) session.getAttribute("STORAGE");
                    if (storage != null) {
                        //3. Cus gets all items
                        Map<String, Integer> items = cart.getItems();
                        //3.1 Call DAO
                        boolean result = false;
                        OrderDAO orderDAO = new OrderDAO();
                        int orderID = orderDAO.createNewOrder(cart.getTotalOrders(storage));
                        if (orderID > 0) {
                            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

                            //4. System update each item to DB
                            for (String item : items.keySet()) {
                                int quantityInCart = items.get(item);
                                OrderDetailDTO orderDetailDTO
                                        = new OrderDetailDTO(
                                                item,
                                                orderID,
                                                quantityInCart,
                                                storage.get(item).getPrice(),
                                                cart.getTotalItem(storage.get(item))
                                        );
                                result = orderDetailDAO.CreateNewOrderDetail(orderDetailDTO);
                                if (storage.get(item).isStatus() == false) {
                                    TblProductDAO productDAO = new TblProductDAO();
                                    result = result && productDAO.updateStatus(item);
                                }
                                if (!result) {
                                    break;
                                }
                            }
                            if (result) {
                                session.removeAttribute("CART");
                                url = siteMap.getProperty(MyApplicationConstants.CheckOutFeatures.LOGIN_PAGE);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("CheckOutServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet Naming: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
