/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khanhlh.registration.RegistrationDAO;
import khanhlh.utils.HashFunction;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdatePassRoleServlet", urlPatterns = {"/UpdatePassRoleServlet"})
public class UpdatePassRoleServlet extends HttpServlet {
//    private final String ERROR_PAGE = "error.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = this.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyApplicationConstants.UpdateFeatures.ERROR_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String admin = request.getParameter("txtRole");
        String searchValue = request.getParameter("LastSearchValue");
        boolean role = false;
        boolean passwordError = false;
        if (admin != null) {
            role = true;
        }
        try {
            //check password length error
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                passwordError = true;
            }
            if(password.equals("")){
                passwordError = false;
            }
            if (passwordError) {
                url = "searchController"
                            + "?txtSearchValue=" + searchValue +
                        "&update=fail";
            } else {
                //1.call DAO
                RegistrationDAO dao = new RegistrationDAO();
                password = HashFunction.sha256(password);//hash password sha256
                boolean result = dao.updateAccount(username, password, role);
                //2.refresh -> call the Search page
                if (result) {
                    url = "searchController"
                            + "?txtSearchValue=" + searchValue;
                }
            }
        } catch (SQLException ex) {
            log("UpdatePassRoleServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdatePassRoleServlet Naming: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log("UpdatePassRoleServlet Hash-NoAlgorithm: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            log("UpdatePassRoleServlet Hash-encoding: " + ex.getMessage());
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
