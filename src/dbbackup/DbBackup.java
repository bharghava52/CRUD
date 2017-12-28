package dbbackup;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbBackup implements backupDB,RestoreDB{
    ConnectionPooling cp;
    
    DbBackup() throws ClassNotFoundException, SQLException{
        cp = ConnectionPooling.getInstance("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test1", "root","root");
    }
    public int serlz(){ 
        ResultSet rs = null;
        FileOutputStream fout = null;
        ObjectOutputStream out = null;
        try {
            Connection con=cp.getConnection();
            PreparedStatement prepStmt = con.prepareStatement("select * from employee");
            rs = prepStmt.executeQuery();
            fout = new FileOutputStream("bd.txt");
            out = new ObjectOutputStream(fout);
            while(rs.next()){
                Employee e=new Employee();
                e.setId(rs.getString("id"));
                e.setName(rs.getString("name"));
                e.setMail(rs.getString("mail"));
                out.writeObject(e);
                out.flush();
            }
            cp.free(con);
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(DbBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int Dserlz(){
        ObjectInputStream in;
        try {
            in = new ObjectInputStream (new FileInputStream("bd.txt"));
            Connection con=cp.getConnection();      
            while(true){
                try{
                    Employee e =(Employee)in.readObject();
                    PreparedStatement prepStmt = con.prepareStatement("insert into employees values(?,?,?)");
                    prepStmt.setString(1,e.getId());
                    prepStmt.setString(2,e.getName());
                    prepStmt.setString(3,e.getMail());
                    int status=prepStmt.executeUpdate();
                }catch(EOFException e){
                 cp.free(con); 
                 return 1;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DbBackup.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return 0;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        DbBackup db=new DbBackup();
        int status=db.serlz();
        if(status!=0)
            System.out.println("serization of the database is completed");
        else
            System.out.println("serialization of the database was unable to be performed");
        status=db.Dserlz();
        if(status!=0)
            System.out.println("Deserization of the database is completed");
        else
            System.out.println("Deserialization of the database was unable to be performed");
    }
    
}
