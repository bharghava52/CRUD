package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee implements DBModel{
    
    UUID uniqueKey;
    ConnectionPooling cp;
    Scanner sc=new Scanner(System.in);
    
    public Employee() throws ClassNotFoundException, SQLException {
        this.cp = ConnectionPooling.getInstance("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test1", "root","root");
    }
    
    public int create(String name,String mail){
        uniqueKey = UUID.randomUUID();
           try { 
               Connection con=cp.getConnection();
               PreparedStatement prepStmt = con.prepareStatement("insert into employee values(?,?,?)");
               prepStmt.setString(1,uniqueKey.toString());
               prepStmt.setString(2,name);
               prepStmt.setString(3, mail);
               int status=prepStmt.executeUpdate();
               cp.free(con);
               return status;       
           } catch (SQLException ex) {
               Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
           }
        return 0;
    }
    
    public int update(String id){
        System.out.println("Enter the name and mail to be updated:");
        String name=sc.next();
        String mail=sc.next();
        try { 
               Connection con=cp.getConnection();
               PreparedStatement prepStmt = con.prepareStatement("update employee set name=?,mail=? where id=?");
               prepStmt.setString(1,id);
               prepStmt.setString(2,name);
               prepStmt.setString(3, mail);
               int status=prepStmt.executeUpdate();
               cp.free(con);
               return status;
           } catch (SQLException ex) {
               Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
           }
        return 0;
    }
    
    public int delete(String id){
        try { 
               Connection con=cp.getConnection();
               PreparedStatement prepStmt = con.prepareStatement("delete from employee where id=?");
               prepStmt.setString(1,id);
               int status=prepStmt.executeUpdate();
               cp.free(con);
               return status;
           } catch (SQLException ex) {
               Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
           }
        return 0;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int status;
        Employee e=new Employee();
        status=e.create("bharghava", "bhargavaraja12@gmail.com");
        if(status!=0){
         System.out.println("employee created");
        }else{
         System.out.println("unable to create employee");
        }
    }    
}
