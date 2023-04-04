/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;
import java.sql.*;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author riham
 */
@WebService(serviceName = "customer")
public class customer {


    /**
     * Web service operation
     */
@WebMethod(operationName = "register")
public String register(@WebParam(name = "fullname") String fullname, @WebParam(name = "email") String email, @WebParam(name = "age") int age, @WebParam(name = "password") String password) {
    String result = "";

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
        Connection conn = DriverManager.getConnection(url);

             PreparedStatement prep = conn.prepareStatement("select * from customer where username = ?");
             prep.setString(1, fullname);
             ResultSet rs = prep.executeQuery();
             if (rs.next()) {
                 result = "User already exists, please use another username!";
             } else {
                 PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO customer (username, email, age, password) VALUES (?, ?, ?, ?)");
                 insertStmt.setString(1, fullname);
                 insertStmt.setString(2, email);
                 insertStmt.setInt(3, age);
                 insertStmt.setString(4, password);
                 int rows = insertStmt.executeUpdate();
                 if (rows > 0) {
                     result = "Registration successful!";
                 } else {
                     result = "Registration failed!";
                 }
                 insertStmt.close();
             }
             rs.close();
             prep.close();
        } catch (SQLException e) {
            System.err.println("D'oh! Got an exception!");
            System.err.println(e.getMessage());
            result = "Something went wrong";
        
    } catch (ClassNotFoundException e) {
        System.err.println("D'oh! Got an exception!");
        System.err.println(e.getMessage());
        result = "Something went wrong";
    }
    return result;
}

    /**
     * Web service operation
     */
@WebMethod(operationName = "orderProduct")
public String orderProduct(@WebParam(name = "customername") String customername, @WebParam(name = "productname") String productname, @WebParam(name = "qty") int qty) {
    String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;

    ResultSet rs = null;
   ResultSet rs1 = null;
   ResultSet rs2 = null;
 
    String result = "";
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(url);
        String query = "SELECT * FROM product WHERE productname = ?";
        pst1 = con.prepareStatement(query);
        pst1.setString(1, productname);
        rs = pst1.executeQuery();
        if (rs.next()) {
            String calculate = "SELECT productprice FROM product WHERE productname = ?";
            pst2 = con.prepareStatement(calculate);
            pst2.setString(1, productname);
            rs1 = pst2.executeQuery();
            if (rs1.next()) {
                int v1 = rs1.getInt(1);
                int fynal = v1*qty;
            String updateQty = "SELECT quantity FROM product WHERE productname = ?";

            pst3 = con.prepareStatement(updateQty);
            pst3.setString(1, productname);
           rs2 = pst3.executeQuery();
rs2.next();
                int v2 = rs2.getInt(1);

                int UpdateValue = v2-qty;
    String updateQuery = "UPDATE product SET  quantity = ? WHERE productname = ?";
    pst4 = con.prepareStatement(updateQuery);
    pst4.setInt(1, UpdateValue); // Set the new quantity parameter
    pst4.setString(2, productname); 
    pst4.executeUpdate();


                PreparedStatement insertStmt = con.prepareStatement("INSERT INTO reservations(cname, pname, pprice, pqty) VALUES (?, ?, ?, ?)");
                insertStmt.setString(1, customername);
                insertStmt.setString(2, productname);
                insertStmt.setInt(3, fynal);
                insertStmt.setInt(4, qty);
                int rows = insertStmt.executeUpdate();
if (rows > 0) {
                    result = "your order has been placed successfully!";
                } else {
                    result = "ordering failed!";
                }
            } else {
                result = "invalid product name!";
            }
        } else {
            result = "invalid product name!";
        
}
    } catch (SQLException e) {
        System.err.println("D'oh! Got an exception!");
        System.err.println(e.getMessage());
        result = "Something went wrong";
    } catch (ClassNotFoundException e) {
        System.err.println("D'oh! Got an exception!");
        System.err.println(e.getMessage());
        result = "Something went wrong";
}
return result;
}

    /**
     * Web service operation
     */
    @WebMethod(operationName = "logging")
    public String logging(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String result = "Invalid username or password"; // Initialize the result to an error message
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(url);
        String query = "SELECT * FROM customer WHERE username = ? AND password = ?";
      pst = con.prepareStatement(query);
        pst.setString(1, username); // Set the username parameter
        pst.setString(2, password); // Set the password parameter
        rs = pst.executeQuery();
        if (rs.next()) {
            // If a row is returned, it means the username and password are valid
            result = "Login successful!";
        }
else {
result = "invalid details";
}
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pst != null) pst.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    
    return result;
    }
}//end class
