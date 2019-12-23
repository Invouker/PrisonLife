// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import java.sql.DriverManager;
import java.sql.Connection;

public class MySQL
{
    private Connection connection;
    private final String host = "localhost";
    private final String database = "minecraft";
    private final String username = "root";
    private final String password = "";
    private String table;
    private int port;
    
    public MySQL(final String table) {
        this.port = 3306;
        this.table = table;
    }
    
    public void connect() {
        try {
            synchronized (this) {
                Class.forName("com.mysql.jdbc.Driver");
                final String connect = "jdbc:mysql://" + "localhost" + ":" + this.port + "/" + "minecraft" + "?&useSSL=false";
                this.connection = DriverManager.getConnection(connect, "root", "");
            }
        }
        catch (MySQLNonTransientConnectionException ex) {
            Chat.print("§cThere are too many connections on MySQL server, Catched and reporting to console...");
        }
        catch (SQLException e) {
            Chat.print("§cCannot connect to MySQL Database! Please repair your data for connect!");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }
    
    public String getTable() {
        return this.table;
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public void disconnect() {
        try {
            if (!this.getConnection().isClosed()) {
                this.getConnection().close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int QuaryUpdate(final PreparedStatement statement) {
        int result = 0;
        try {
            result = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        this.disconnect();
        return result;
    }
    
    public ResultSet Quary(final PreparedStatement statement) {
        ResultSet rSet = null;
        try {
            rSet = statement.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rSet;
    }
}
