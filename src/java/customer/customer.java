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
    public String register(@WebParam(name = "fullname") String fullname, @WebParam(name = "email") String email, @WebParam(name = "password") String password) {
String result = "";
    
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=riham;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
        try (Connection conn = DriverManager.getConnection(url);
        
PreparedStatement prep = conn.prepareStatement("INSERT INTO customer (fullname, email, password) VALUES(?, ?, ?)")) {
prep.setString(1, fullname);
prep.setString(2, email);
prep.setString(3, password);
 int  rows = prep.executeUpdate();

 if (rows > 0) {
    
                result = "registration successful!";
            } else {
                result = "registration failed!";
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("D'oh! Got an exception!");
        System.err.println(e.getMessage());
        result = "Something went wrong";
    }
    return result;


    }

}//end class
