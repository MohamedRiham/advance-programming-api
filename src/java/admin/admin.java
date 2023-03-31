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
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
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

@WebMethod(operationName = "getCustomers")
public String getCustomers() {
    String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuilder result = new StringBuilder();

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(url);
        String query = "SELECT * FROM customer ";
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            // Append each row of data to the result string
            result.append("username: ").append(rs.getString("username")).append(", ")
                  .append("email: ").append(rs.getString("email")).append(", ")
                  .append("age: ").append(rs.getString("age")).append(", ")

                  .append("password: ").append(rs.getString("password")).append("\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    
    return result.toString();
}

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AddProduct")
    public String AddProduct(@WebParam(name = "ProductName") String ProductName, @WebParam(name = "ProductPrice") int ProductPrice, @WebParam(name = "Quantity") int Quantity) {
String result = "";
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
        Connection conn = DriverManager.getConnection(url);
                 PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO product (productname, productprice, quantity) VALUES (?, ?, ?)");
                 insertStmt.setString(1, ProductName);
                 insertStmt.setInt(2, ProductPrice);
                 insertStmt.setInt(3, Quantity);
                 int rows = insertStmt.executeUpdate();
                 if (rows > 0) {
                     result = "inserting product successful!";
                 } else {
                     result = "inserting product  failed!";
                 }
                 insertStmt.close();
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

@WebMethod(operationName = "getProducts")
public String getProducts() {
    String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuilder result = new StringBuilder();

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(url);
        String query = "SELECT * FROM product ";
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            // Append each row of data to the result string
            result.append("productname: ").append(rs.getString("productname")).append(", ")
                  .append("productprice: ").append(rs.getString("productprice")).append(", ")

                  .append("quantity: ").append(rs.getString("quantity")).append("\n");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    
    return result.toString();
}

    /**
     * Web service operation
     */
    @WebMethod(operationName = "EditProduct")
    public String EditProduct(@WebParam(name = "pname") String pname, @WebParam(name = "pprice") int pprice, @WebParam(name = "quantity") int quantity) {
        String url = "jdbc:sqlserver://DESKTOP-37MVMA7\\SQLEXPRESS:1433; databaseName=details;  encrypt=true; trustServerCertificate=true; integratedSecurity=true";
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String result = ""; 
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(url);
        String query = "SELECT * FROM product WHERE productname = ?";
      pst = con.prepareStatement(query);
        pst.setString(1, pname); // Set the username parameter
        rs = pst.executeQuery();
if (rs.next()) {
    String productname = rs.getString("productname");

    // Update the product's price and quantity using an SQL UPDATE statement
    String updateQuery = "UPDATE product SET productprice = ?, quantity = ? WHERE productname = ?";
    pst = con.prepareStatement(updateQuery);
    pst.setInt(1, pprice); // Set the new price parameter
    pst.setInt(2, quantity); // Set the new quantity parameter
    pst.setString(3, productname); // Set the product ID parameter
    int rowsUpdated = pst.executeUpdate();

    // Check if any rows were updated successfully
    if (rowsUpdated > 0) {
        result = "Product updated successfully";
    } else {
        result = "Failed to update product";
    }
}else {
result = "Invalid product name";
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

    

}//end class
