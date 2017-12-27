package connectionhandling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class ConnectionHandling {

    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int n;
        System.out.println("enter the number of connections to pool");
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        ConnectionPooling cp=new ConnectionPooling("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test1", "root","root",n);
        Connection con=cp.getConnection();
        PreparedStatement prepStmt = con.prepareStatement("select * from users");
         
        ResultSet rs = prepStmt.executeQuery();
        while (rs.next()) {
               System.out.print(rs.getString("name") + " ");
               System.out.println(rs.getString("email"));
        }
        cp.free(con);
        
        
           
    }
    
}
