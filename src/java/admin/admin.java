/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.sql.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author riham
 */
@WebService(serviceName = "admin")
public class admin {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "logging")
    public String logging(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=riham;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String result = "Invalid username or password"; // Initialize the result to an error message

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(url);
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
      pst = con.prepareStatement(query);
        pst.setString(1, username); // Set the username parameter
        pst.setString(2, password); // Set the password parameter
        rs = pst.executeQuery();
        if (rs.next()) {
            // If a row is returned, it means the username and password are valid
            result = "Login successful";
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
