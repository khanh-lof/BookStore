/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanhlh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 *
 * @author ADMIN
 */
public class DBhelper {
    public static Connection makeConnection()
            throws /*ClassNotFoundException*/NamingException, SQLException{
        //1. get current system file
        Context context = new InitialContext();
        //2. get container context
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        //3. Get Datasource
        DataSource ds = (DataSource) tomcatContext.lookup("DSTest");
        //4 get Connection
        Connection con = ds.getConnection();
        return con;
//        //load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        // create Connection DB URL String
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=JavaWeb;instanceName=KHANHLHSE160242";//slide
//        // Open Connection
//                Connection con = DriverManager.getConnection(url,"sa","Klt23sqlserver17");
        
    }
    public static Properties getSiteMap(String siteMapFile, ServletContext context)
                throws IOException{
        if(siteMapFile == null){
            return null;
        }
        if(siteMapFile.trim().isEmpty()){
            return null;
        }
        Properties result = new Properties();
        InputStream rs = null;
        
        try{
            rs = context.getResourceAsStream(siteMapFile);
            result.load(rs);
            return result;
        }finally{
            if(rs != null){
                rs.close();
            }
        }
    }
}
