///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package khanhlh.controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Properties;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import khanhlh.utils.MyApplicationConstants;
//
///**
// *
// * @author ADMIN
// */
//@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
//public class DispatchController extends HttpServlet {
////    private final String LOGIN_PAGE = "";
////    private final String LOGIN_CONTROLLER= "loginController";
////    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastNameServlet";
////    private final String DELETE_CONTROLLER = "DeleteServlet";
////    private final String FIRST_REQUEST_CONTROLLER = "FirstRequestServlet";
////    private final String UPDATE_CONTROLLER = "UpdatePassRoleServlet";
////    private final String ADD_ITEM_TO_CART_CONTROLLER = "AddItemToCartServlet";
////    private final String VIEW_YOUR_CART_PAGE = "viewCart.jsp";
////    private final String SHOPPING_CONTROLLER = "GoShoppingServlet";
////    private final String REMOVE_ITEM_CONTROLLER = "RemoveItemServlet";
////    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
////    private final String CREATE_ACCOUNT_CONTROLLER = "CreateNewAccountServlet";
////    private final String LOG_OUT_CONTROLLER = "LogOutServlet";
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        ServletContext context = this.getServletContext();
//        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
//        String url = siteMap.getProperty("loginPage");
//        //user click button 
//        String button = request.getParameter("btAction");
//        
//        try  {
//            if (button == null){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.FIRST_REQUEST_CONTROLLER);;
//            } else if (button.equals("Login")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.LOGIN_CONTROLLER);
//            } else if (button.equals("Search")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER);
//            } else if (button.equals("delete")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.DELETE_CONTROLLER);
//            } else if (button.equals("Update")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.UPDATE_CONTROLLER);
//            } else if (button.equals("Add Book To Your Cart")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.ADD_ITEM_TO_CART_CONTROLLER);
//            } else if (button.equals("View Your Cart")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.VIEW_YOUR_CART_PAGE);
//            } else if (button.equals("Go to buy Book")) {
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.SHOPPING_CONTROLLER);
//            } else if (button.equals("Remove Selected Item")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.REMOVE_ITEM_CONTROLLER);
//            } else if (button.equals("Check out")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.CHECK_OUT_CONTROLLER);
//            } else if (button.equals("Create New Account")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.CREATE_ACCOUNT_CONTROLLER);
//            } else if (button.equals("Log Out")){
//                url = siteMap.getProperty(MyApplicationConstants.DispatchFeatures.LOG_OUT_CONTROLLER);
//            }
//        }
//        finally{
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
//            out.close();
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
