/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.*;





public class create_connection {


  Statement stmt = null;
    //JDBC driver name and database URL
     final String driverName = "oracle.jdbc.OracleDriver";
     final String url = "jdbc:oracle:thin:@144yyy.217.163.57:1521:XE" ;
    
    // Database credentials
     final String username = "Proj3";
     final String password = "ject3pw";
private  Connection con;
  public  Connection getConnection() {
      System.out.println("Connecting to database...");
        System.out.println("Creating statement...");
         
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
               
             System.out.println("connection problem"); 
            }
        } catch (ClassNotFoundException ex) {
            
            System.out.println("Driver not found."); 
        }
        return con;
    }
   public  void closeConnection() throws SQLException {
      con.close();
  }
  
  public  void closeStmt() throws SQLException {
      stmt.close();
  }
     public  ResultSet grewal(String S) throws SQLException {
         
stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
return stmt.executeQuery(S);
}
}