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

}//end class
