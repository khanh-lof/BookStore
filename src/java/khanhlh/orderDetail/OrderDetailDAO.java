/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.orderDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import khanhlh.utils.DBhelper;

/**
 *
 * @author ADMIN
 */
public class OrderDetailDAO implements Serializable {

    public boolean CreateNewOrderDetail(OrderDetailDTO dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        int rs = 0;
        boolean result = false;
        try {
            //1. Make connection
            con = DBhelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Insert into OrderDetail(sku, OrderID, Quantity, Price, Total) "
                        + "Values(?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                //3. Exec Query
                stm.setString(1, dto.getSku());
                stm.setInt(2, dto.getOrderId());
                stm.setInt(3, dto.getQuantity());
                stm.setFloat(4, dto.getPrice());
                stm.setFloat(5, dto.getTotal());
                rs = stm.executeUpdate();
                if (rs > 0) {
                    result = true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return result;
    }

    public Map<String, Integer> getQuantitySold() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Map<String, Integer> result = new HashMap<>();
        try {
            //1. Make connection
            con = DBhelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Select sku, Quantity "
                        + "From OrderDetail";
                stm = con.prepareStatement(sql);
                //3. Exec Query
                rs = stm.executeQuery();
                while (rs.next()) {
                    String sku = rs.getString("sku");
                    int quantity = rs.getInt("Quantity");
                    if (result.containsKey(sku)) {
                        quantity += result.get(sku);
                    }
                    result.put(sku, quantity);
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return result;

    }
}
