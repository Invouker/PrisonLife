// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import Main.MySQL;

public class SolitaryData
{
    MySQL sql;
    
    public SolitaryData() {
        this.sql = new MySQL("solitary");
    }
    
    public void setLData(final int solitary, final String path, final long data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE id=?");
            statement.setLong(1, data);
            statement.setInt(2, solitary);
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public long getLData(final int solitary, final String path) {
        long result = 0L;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            result = results.getLong(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void setIData(final int solitary, final String path, final int data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE id=?");
            statement.setInt(1, data);
            statement.setInt(2, solitary);
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setSData(final int solitary, final String path, final String data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE id=?");
            statement.setString(1, data);
            statement.setInt(2, solitary);
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getIData(final int solitary, final String path) {
        int result = 0;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            result = results.getInt(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void setDData(final int solitary, final String path, final double data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + path + "=? WHERE id=?");
            statement.setDouble(1, data);
            statement.setInt(2, solitary);
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double getDData(final int solitary, final String path) {
        double result = 0.0;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            result = results.getDouble(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String getSData(final int solitary, final String path) {
        String result = null;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            result = results.getString(path);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void createSolitary(final int solitary, final int x, final int y, final int z, final String world, final String name, final String reason) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            if (!this.solitaryExists(solitary)) {
                final PreparedStatement insert = this.sql.getConnection().prepareStatement("INSERT INTO " + this.sql.getTable() + " (id,signX,signY,signZ,world,name,reason) VALUES (?,?,?,?,?,?,?)");
                insert.setInt(1, solitary);
                insert.setInt(2, x);
                insert.setInt(3, y);
                insert.setInt(4, z);
                insert.setString(5, world);
                insert.setString(6, name);
                insert.setString(7, reason);
                this.sql.QuaryUpdate(insert);
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Solitary has been created and Inserted to MySQL Table !");
                this.sql.disconnect();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean solitaryExists(final int solitary) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, solitary);
            final ResultSet results = this.sql.Quary(statement);
            if (results.next()) {
                this.sql.disconnect();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
