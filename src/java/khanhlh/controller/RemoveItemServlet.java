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
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khanhlh.cart.CartObject;
import khanhlh.tblProduct.TblProductDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "RemoveItemServlet", urlPatterns = {"/RemoveItemServlet"})
public class RemoveItemServlet extends HttpServlet {

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
        try {
            //1. Cus goes to his/her cart placement
            HttpSession session = request.getSession(false);

            if (session != null) {
                //2. Cus take cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, TblProductDTO> storage = (Map<String, TblProductDTO>) session.getAttribute("STORAGE");
                    if (storage != null) {
                        //3. Cus gets all items
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {
                            //4. Cus get all selected remove items
                            String[] selectedItems = request.getParameterValues("chkItem");

                            if (selectedItems != null) {
                                //5. System remove item by item from cart
                                for (String item : selectedItems) {
                                    int available = storage.get(item).getQuantity();
                                    int quantityInCart = items.get(item);
                                    cart.removeItemFromCart(item);
                                    storage.get(item).setQuantity(available + quantityInCart);
                                    if(storage.get(item).getQuantity() > 0){
                                        storage.get(item).setStatus(true);
                                    }
                                }//remove Items
                                session.setAttribute("CART", cart);
                                session.setAttribute("STORAGE", storage);
                                
                            }
                        }
                    }//storage is valid
                }//cart is valid
            }
        } catch (SQLException ex) {
            log("RemoveItemServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("RemoveItemServlet Naming: " + ex.getMessage());
        } finally {
            //6. System refresh cart
            //call again Function view Your cart
            String url= "viewCartPage";
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
