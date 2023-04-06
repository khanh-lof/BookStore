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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khanhlh.registration.RegistrationDAO;
import khanhlh.registration.RegistrationDTO;
import khanhlh.utils.HashFunction;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
public class LoginServlet extends HttpServlet {

//    private final String SEARCH_PAGE = "search.jsp";
//    private final String INVALID_PAGE = "invalid.html";

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
        Properties siteMap = (Properties)context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyApplicationConstants.LogInFeatures.INVALID_PAGE);

        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            password = HashFunction.sha256(password);
            //call Model/DAO
            //1.1 new Object
            RegistrationDAO dao = new RegistrationDAO();
            //1,2 call method of that Obj
            RegistrationDTO result = dao.checkLogin(username, password);
            //2.process result
            if (result != null) {
                url = siteMap.getProperty(MyApplicationConstants.LogInFeatures.SEARCH_PAGE);
                HttpSession session = request.getSession();
                session.setAttribute("USER", result);
                Cookie cookies = new Cookie(username, password);
                cookies.setMaxAge(60*3);
                response.addCookie(cookies);
            }//
            //end user clicked Login
        } catch (SQLException ex) {
            log("LoginServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet Naming: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex){
            log("LoginServlet Hash-NoAlgorithm: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex){
            log("LoginServlet Hash-encoding: " + ex.getMessage());
        }
        finally {
            //response.sendRedirect(url);
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
