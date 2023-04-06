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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khanhlh.cart.CartObject;
import khanhlh.tblProduct.TblProductDTO;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {
//    private final String SHOPPING_PAGE = "shopping.jsp";

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
        String url = siteMap.getProperty(MyApplicationConstants.AddItemToCartFeatures.SHOPPING_PAGE);
        String item = request.getParameter("sku");
        try {
            int quantity = Integer.parseInt(request.getParameter("cboQuantity"));
            //1.Cus goes to cart placement
            HttpSession session = request.getSession();
            //2. Cus takes his cart
            CartObject cart = (CartObject) session.getAttribute("CART");
            Map<String, TblProductDTO> storage = (Map<String, TblProductDTO>) session.getAttribute("STORAGE");
            if (cart == null) {
                cart = new CartObject();
            }
            if (storage != null) {
                //3. Cus choose specific item
                int available = storage.get(item).getQuantity();
                if (available >= quantity) {
                    //4. Cus drops item down
                    cart.addItemCart(item, quantity);
                    session.setAttribute("CART", cart);
                    storage.get(item).setQuantity(available - quantity);
                    if(storage.get(item).getQuantity() == 0){
                        storage.get(item).setStatus(false);
                    }
                    session.setAttribute("STORAGE", storage);
                } else {
                    request.setAttribute("OVER_QUANTITY_ERR", item);
                }
                //5. Cus continuely goes to shopping
            }
        } catch (SQLException ex) {
            log("AddItemToCartServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddItemToCartServlet Naming: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            log("AddItemToCartServlet NumberFormat: " + ex.getMessage());
            request.setAttribute("QUANTITY_FM_ERR", item);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
