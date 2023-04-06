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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khanhlh.registration.RegistrationCreateError;
import khanhlh.registration.RegistrationDAO;
import khanhlh.registration.RegistrationDTO;
import khanhlh.utils.HashFunction;
import khanhlh.utils.MyApplicationConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {
//    private final String ERROR_PAGE="CreateNewAccount.jsp";
//    private final String LOGIN_PAGE="login.html";
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
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String fullName = request.getParameter("txtFullname");
        String confirm = request.getParameter("txtConfirm");
        boolean errorFound = false;
        RegistrationCreateError errors = new RegistrationCreateError();
        ServletContext context = this.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMap.getProperty(MyApplicationConstants.SignUpFeatures.ERROR_PAGE);
        try {
            //1. Check user's error
            if(username.trim().length()<6 || username.trim().length()>20){
                errorFound = true;
                errors.setUsernameLengthError("Username is required input from 6 to 20 characters");
            }
            if(password.trim().length()<6 || password.trim().length()>30){
                errorFound = true;
                errors.setPasswordLenghtError("Password is required input from 6 to 20 characters");
            }
            else if(!confirm.trim().equals(password.trim())){
                errorFound = true;
                errors.setConfirmNotMatched("Confirm is not matched");
            }
            if(fullName.trim().length()<2 || fullName.trim().length()>50){
                errorFound = true;
                errors.setFullNameLengthError("Full Name is required input from 2 to 50 characters");
            }
            if(errorFound){
                //1. Catch store
                request.setAttribute("CREATE_ERRORS", errors);
                //2. Show error
                
            }else{
            //2. insert to DB--> call DB
                password = HashFunction.sha256(password);//hash password sha256
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO dto = new RegistrationDTO(
                        username, password, fullName, false);
                boolean result = dao.createNewAccount(dto);
                if(result){
                    url= siteMap.getProperty(MyApplicationConstants.SignUpFeatures.LOGIN_PAGE);
                }
            //3. check system error
            }
            //4. Transfer specified page
        }catch (SQLException ex){
            String errMsg = ex.getMessage();
            log("Create Account SQL "+ex.getMessage());
            if(errMsg.contains("duplicate")){
                errors.setUsernameIsExisted("is Existed");
                request.setAttribute("CREATE_ERRORS", errors);
            }
        }catch (NamingException ex){
            log("Create Account Naming "+ex.getMessage());
        }catch (NoSuchAlgorithmException ex){
            log("Create Account Hash-NoAlgorithm: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex){
            log("Create Account Hash-encoding: " + ex.getMessage());
        }
        finally{
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
