/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.tblProduct;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import khanhlh.orderDetail.OrderDetailDAO;
import khanhlh.utils.DBhelper;

/**
 *
 * @author ADMIN
 */
public class TblProductDAO implements Serializable {

    private Map<String, TblProductDTO> bookInf;

    public Map<String, TblProductDTO> getBookInf() {
        return bookInf;
    }

    public void getStorage()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //connectDB
            con = DBhelper.makeConnection();
            if (con != null) {
                //1. Create SQL String
                String sql = "Select sku, Name, Description, Quantity, Price, Status "
                        + "From tblProduct";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    //get field
                    String sku = rs.getString("sku");
                    String name = rs.getString("Name");
                    String descript = rs.getNString("Description");
                    int quantity = rs.getInt("Quantity");
                    float price = rs.getFloat("Price");
                    boolean status = rs.getBoolean("Status");
                    TblProductDTO dto
                            = new TblProductDTO(sku, name, descript, quantity, price, status);
                    //add book to show
                    if (this.bookInf == null) {
                        this.bookInf = new HashMap<>();
                    }//book list is available
                    this.bookInf.put(sku, dto);
                }//has result set
            }//connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public TblProductDTO getProductBySku(String sku)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Select sku, Name, Description, Quantity, Price, Status "
                        + "From tblProduct "
                        + "Where sku = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, sku);
                rs = stm.executeQuery();
                if (rs.next()) {
                    //get field
                    String name = rs.getString("Name");
                    String descript = rs.getNString("Description");
                    int quantity = rs.getInt("Quantity");
                    float price = rs.getFloat("Price");
                    boolean status = rs.getBoolean("Status");
                    TblProductDTO dto = new TblProductDTO(sku, name, descript, quantity, price, status);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean updateStatus(String sku)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        int rs = 0;
        boolean result = false;
        try {
            //1. Connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Update tblProduct "
                        + "Set Status = 0 "
                        + "Where sku = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, sku);
                rs = stm.executeUpdate();
                if (rs > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public void calcQuantityAvailable(Map<String, TblProductDTO> storage)
            throws SQLException, NamingException {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        Map<String, Integer> quantitySold = orderDetailDAO.getQuantitySold();
        for (String sku : quantitySold.keySet()) {
            if (storage.containsKey(sku)) {
                int available = storage.get(sku).getQuantity();
                int sold = quantitySold.get(sku);
                storage.get(sku).setQuantity(available - sold);
                if (storage.get(sku).getQuantity() <= 0) {
                    storage.get(sku).setQuantity(0);
                }
            }//if book is used to sold
        }//end for calc available quantity
    }
}
