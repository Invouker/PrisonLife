// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerDataListener
{
    Main plugin;
    MySQL sql;
    
    public PlayerDataListener() {
        this.plugin = (Main)Main.getPlugin((Class)Main.class);
        this.sql = new MySQL("player_data");
    }
    
    public void setIData(final Player p, final String path, final int data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE UUID=?");
            statement.setInt(1, data);
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + path + " §cDATA:§7 " + data);
        }
    }
    
    public int getIData(final Player p, final String Path) {
        int returnData = 0;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            returnData = this.sql.Quary(statement).getInt(Path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    
    public void setBData(final Player p, final String Path, final boolean data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + Path + "=? WHERE UUID=?");
            statement.setBoolean(1, data);
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + Path + " §cDATA:§7 " + data);
        }
    }
    
    public boolean getBData(final Player p, final String path) {
        boolean returnData = false;
        try {
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            returnData = this.sql.Quary(statement).getBoolean(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    
    public void setDData(final Player p, final String path, final double data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE UUID=?");
            statement.setDouble(1, data);
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + path + " §cDATA:§7 " + data);
        }
    }
    
    public double getDData(final Player p, final String path) {
        double returnData = 0.0;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            returnData = this.sql.Quary(statement).getDouble(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    
    public void setLData(final Player p, final String path, final long data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE UUID=?");
            statement.setLong(1, data);
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + path + " §cDATA:§7 " + data);
        }
    }
    
    public long getLData(final Player p, final String path) {
        long returnData = 0L;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            returnData = this.sql.Quary(statement).getLong(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    
    public String getSData(final Player p, final String path) {
        String returnData = null;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            returnData = this.sql.Quary(statement).getString(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return returnData;
    }
    
    public void setSData(final Player p, final String path, final String data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE UUID=?");
            statement.setString(1, data);
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + path + " §cDATA:§7 " + data);
        }
    }
    
    public boolean playerExists(final UUID uuid) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            final ResultSet results = this.sql.Quary(statement);
            if (results.next()) {
                return true;
            }
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void createPlayer(final Player p) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            if (!this.playerExists(p.getPlayer().getUniqueId())) {
                if (this.sql.getConnection().isClosed()) {
                    this.sql.connect();
                }
                final PreparedStatement insert = this.sql.getConnection().prepareStatement("INSERT INTO " + this.sql.getTable() + " (UUID,NAME) VALUES (?,?)");
                insert.setString(1, p.getPlayer().getUniqueId().toString());
                insert.setString(2, p.getName());
                insert.executeUpdate();
                this.sql.QuaryUpdate(insert);
            }
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
