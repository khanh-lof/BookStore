/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import khanhlh.utils.DBhelper;

/**
 *
 * @author ADMIN
 */
public class OrderDAO implements Serializable {

    public OrderDAO() {
    }

    public int createNewOrder(float total)
            throws SQLException, NamingException {
        int OrderID = -1;//default create false
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Insert into [Order] (Total) "
                        + "Output inserted.id "
                        + "Values(?)";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, total);
                rs = stm.executeQuery();
                if (rs.next()) {
                    OrderID = rs.getInt("Id");
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
        return OrderID;
    }
}
