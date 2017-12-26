
package mysqlcrud;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class MySqlCRUD {

    public static int insert(Employee e){  
        int status=0;  
        try{  
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root","");  
            PreparedStatement ps=(PreparedStatement) con.prepareStatement("insert into users(username, password, fullname, email) values (?,?,?,?)");  
            ps.setString(1,e.getUsername());  
            ps.setString(2,e.getPassword());  
            ps.setString(3,e.getFullname());  
            ps.setString(4,e.getEmail());  
            status=ps.executeUpdate();  
              
            con.close();  
        }catch(Exception ex){ex.printStackTrace();}  
          
        return status;  
    }  
    
    public static int update(Employee e){  
        int status=0;  
        try{  
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root","");  
            PreparedStatement ps=(PreparedStatement) con.prepareStatement("update users set password=?,fullname=?,email=? where username=?");    
            ps.setString(1,e.getPassword());
            ps.setString(2,e.getFullname());
            ps.setString(3,e.getEmail());    
            ps.setString(4,e.getUsername()); 
            status=ps.executeUpdate();  
            con.close();  
        }catch(Exception ex){ex.printStackTrace();}        
        return status;  
    }  
    
    
    public static int delete(String username){  
        int status=0;  
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root","");
            PreparedStatement ps=(PreparedStatement) con.prepareStatement("delete from users where username=?");  
            ps.setString(1,username);  
            status=ps.executeUpdate();   
            con.close();  
        }catch(Exception e){e.printStackTrace();}  
          
        return status;  
    }  
    
    public static ResultSet select(String username){  
        ResultSet status = null;  
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root","");
            PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from users where username=?");  
            ps.setString(1,username);  
            status=(ResultSet) ps.executeQuery();   
        }catch(Exception e){e.printStackTrace();}  
        return status;  
    }  
    
    
    public static void main(String[] args) throws SQLException {
        Employee e=new Employee("bharghava","testpass","Venkata Sai Bharghava","bhargavaraja12@gmail.com");
        int check=insert(e);
        if(check==1)
            System.out.println("insertion successfull");
        else
            System.out.println("insertion failed");
        e.setFullname("B Venkata Sai Bharghava");
        check=update(e);
        if(check==1)
            System.out.println("updation successfull");
        else
            System.out.println("updation failed");
        ResultSet rs=select("bharghava");
        if(rs!=null){
            rs.absolute(1);
            System.out.println("name:"+rs.getString(1)+" Password:"+rs.getString(2)+" Fullname:"+rs.getString(3)+" Email:"+rs.getString(1));
        }   
        else
            System.out.println("not found");
        check=delete("bharghava");
        if(check==1)
            System.out.println("deleted successfull");
        else
            System.out.println("deleted failed");
        
    }
    
}
