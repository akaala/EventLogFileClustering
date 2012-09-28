/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileclusteringversion3;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class sqlConnection {
    private Connection con;
    private Statement stmt;
    private ResultSet rs; 
    private int IsUpdate;   
//    private ResultSet rs;
    public sqlConnection() throws SQLException
    {
        
        String uname="root";
        String paswrd= "";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/myModule", uname,paswrd);
            stmt =  (Statement)con.createStatement();
        }catch(ClassNotFoundException ex)
        {
            System.out.println("CassNotFound error "+ ex.getMessage());
        }
        catch(SQLException ex)
        {
            System.out.println("SQLException is "+ ex.getMessage());
        }
    } 
    public ResultSet ExecuteQuery(String query)        // Executes the search statements of the query
    {
               
        try 
        {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("SQLException in ExecuteQuery is "+ ex.getMessage());
        }   
        return rs;
    }
    public void UpdateQuery(String query)              //Update the database ... 
    {
        try {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 2);
        }
    }
    
    public int UpdateQueryReturn(String query)              //Update the database ... 
    {
        try {
            IsUpdate =stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 2);
        }
        return IsUpdate;
    }
    
    public void sqlClose()
    {
        try {
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", 3);
        }
    }

   
    
}
