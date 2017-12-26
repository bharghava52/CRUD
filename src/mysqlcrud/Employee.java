package mysqlcrud;

public class Employee {  
    private String username,password,fullname,email;
    
    
    public Employee(String username,String password,String fullname,String email){
        this.username = username;
        this.password = password;
        this.fullname=fullname;
        this.email=email;
    }
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String id) {  
        this.username = id;  
    }  

    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    } 
    
    public String getFullname() {  
        return fullname;  
    }  
    public void setFullname(String fullname) {  
        this.fullname = fullname;  
    }  
    
    public String getEmail() {  
        return email;  
    }  
    public void setEmail(String email) {  
        this.email = email;  
    }  
}
