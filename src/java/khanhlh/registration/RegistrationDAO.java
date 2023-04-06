/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.registration;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import khanhlh.utils.DBhelper;
import khanhlh.utils.HashFunction;

/**
 *
 * @author ADMIN
 */
public class RegistrationDAO implements Serializable {

//    public boolean checkLogin(String username, String password)
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        try {
            //connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //1.CRUD
                // 2.1 Create SQL String
                String sql = "Select username,lastname "
                        + "From Registration "
                        + "Where username = ? And [password] = ?";
                //2.2 Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //2.3 Execute Query
                rs = stm.executeQuery();
                //2.4 Process result Set
                if (rs.next()) {
                    String fullName = rs.getString("lastname");
                    result = new RegistrationDTO(username, password, fullName, false);
                }
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
        return result;
    }
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastName(String searchValue)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //1.CRUD
                // 2.1 Create SQL String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname like ?";
                //2.2 Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //2.3 Execute Query
                rs = stm.executeQuery();
                //2.4 Process result Set
                while (rs.next()) {
                    //get field/column
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto
                            = new RegistrationDTO(username, password, lastname, role);
                    //add to account list
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }
                    //accounts is available
                    this.accounts.add(dto);
                }//end rs hase more than one record
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

    public boolean deleteAccount(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //1.CRUD
                // 2.1 Create SQL String
                String sql = "Delete From Registration "
                        + "Where username=? ";
                //2.2 Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //2.3 Execute Query
                int effectedRow = stm.executeUpdate();
                //2.4 Process
                if (effectedRow > 0) {
                    result = true;
                }//end rs hase more than one record
            }//connection is available

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

    public boolean updateAccount(String username, String password, boolean Role)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        PreparedStatement stm = null;
        Connection con = null;
        boolean result = false;
        try {
            //connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                String sql;
                if (password.equals(HashFunction.sha256(""))) {//not update password
                    //1. Create SQL String 
                    sql = "Update Registration "
                            + "Set isAdmin = ? Where username = ?";
                    //2. Create statement Object
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, Role);
                    stm.setString(2, username);
                } else {
                    //1. Create SQL String 
                    sql = "Update Registration "
                            + "Set password = ?, isAdmin = ? Where username = ?";
                    //2. Create statement Object
                    stm = con.prepareStatement(sql);
                    stm.setString(1, password);
                    stm.setBoolean(2, Role);
                    stm.setString(3, username);
                }
                //3. Execute Query
                int row = stm.executeUpdate();
                //4. Process
                if (row > 0) {
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

    public boolean createNewAccount(RegistrationDTO dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //connect DB
            con = DBhelper.makeConnection();
            if (con != null) {
                //1.CRUD
                // 2.1 Create SQL String
                String sql = "Insert into Registration("
                        + "Username, Password, lastname, isAdmin) "
                        + "Values("
                        + "?, ?, ?, ?)";
                //2.2 Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getLastname());
                stm.setBoolean(4, dto.isRole());
                //2.3 Execute Query
                int effectedRow = stm.executeUpdate();
                //2.4 Process
                if (effectedRow > 0) {
                    result = true;
                }//end rs hase more than one record
            }//connection is available

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
}
