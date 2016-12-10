/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minor;
/**
 *
 * @author Tripti
 */
import java.sql.*;
public class DBconnection {
    
    public Connection cn=null;
    public DBconnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","");
        } catch (Exception e) {
        }
    }
}


