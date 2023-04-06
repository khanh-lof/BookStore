/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khanhlh.registration.RegistrationDAO;
import khanhlh.registration.RegistrationDTO;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "FirstRequestServlet", urlPatterns = {"/FirstRequestServlet"})
public class FirstRequestServlet extends HttpServlet {

//    private final String LOGIN_PAGE = "login.html";
//    private final String SEARCH_PAGE = "search.jsp";

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
        String url = siteMap.getProperty(MyApplicationConstants.FirstRequestFeatures.LOGIN_PAGE);
        try {
            //1. get all cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. get last cookie
                Cookie lastCookie = cookies[0];
                for (Cookie cookie : cookies) {
                    if(!cookie.getName().equals("JSESSIONID")){
                        lastCookie = cookie;
                    }
                }
                //3. get username and password form last cookie
                String username = lastCookie.getName();
                String password = lastCookie.getValue();
                //4. call DAO
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(username, password);
                //5. process
                if (result != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", result);
                    url = siteMap.getProperty(MyApplicationConstants.FirstRequestFeatures.SEARCH_PAGE);;
                }
            }//cookies has existed
        } catch (SQLException ex) {
            log("FirstRequestServlet SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("FirstRequestServlet Naming: " + ex.getMessage());
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
