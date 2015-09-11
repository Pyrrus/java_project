/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ejb;

import java.sql.*;
import org.json.simple.JSONObject;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author Adam
 */
@Stateless
@LocalBean
public class customerDAO {
    
    private DataSource ds;
    
    public void retrieveData() {
        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/customers";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            
            Statement st = conn.createStatement();
            
            ResultSet rs;
            rs = conn.createStatement().executeQuery("SELECT * FROM list");
            
            JSONObject obj = new JSONObject();
            
            while(rs.next()) {
               obj.put("id", rs.getInt("id"));
               obj.put("firstName", rs.getString("firstName"));
               obj.put("lastName", rs.getString("lastName"));
               obj.put("email", rs.getString("email"));
               obj.put("phone", rs.getString("phone"));
               
               
                
              
            }
            
            System.out.print(obj);
            
            st.close();
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
