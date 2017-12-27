package connectionhandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPooling implements Runnable{
    private String driver, url, username, password;
    private int no_of_conn;
    public Vector<Connection> availableConnections, busyConnections;
    
    public ConnectionPooling(String driver,String url, String username, String password,int no_of_conn) throws ClassNotFoundException, SQLException {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.no_of_conn= no_of_conn;
        availableConnections = new Vector<Connection>();
        busyConnections = new Vector<Connection>();
        for(int i=0;i<no_of_conn;i++){
            availableConnections.addElement(makeNewConnection());
        }
    }

    private Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username,password);
        return (connection);
    }
    
    
    public synchronized Connection getConnection() throws SQLException {
           if (!availableConnections.isEmpty()) {
                Connection existingConnection = (Connection) availableConnections.lastElement();
                int lastIndex = availableConnections.size() - 1;
                availableConnections.removeElementAt(lastIndex);
                if (existingConnection.isClosed()) {
                  notifyAll();
                  return (getConnection());
                } else {
                  busyConnections.addElement(existingConnection);
                  return (existingConnection);
                }
           } else {
                try {
                    wait();
                } catch (InterruptedException ie) {
                }
                return (getConnection());
           }
    }
    
    public synchronized void free(Connection connection) {
        busyConnections.removeElement(connection);
        availableConnections.addElement(connection);
        notifyAll();
    }
    
    public void run() {
        try {
            Connection connection = makeNewConnection();
            synchronized (this) {
                availableConnections.addElement(connection);
                notifyAll();
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
    
}
