// 
// Decompiled by Procyon v0.5.36
// 

package RegionGuard3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import Main.MySQL;
import Main.ZoneVector;

public class inLocation
{
    MySQL sql;
    
    public inLocation() {
        this.sql = new MySQL("region_guard");
    }
    
    public boolean inLocs(final Player p) {
        boolean isIn = false;
        for (int i = 0; i <= 100; ++i) {
            final Location l1 = this.regionLocation(i, true);
            final Location l2 = this.regionLocation(i, false);
            if (ZoneVector.contains(p.getLocation(), l1.getBlockX(), l1.getBlockY(), l1.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ())) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }
    
    public int inLocIDs(final Player p) {
        int id = -1;
        for (int i = 0; i <= 100; ++i) {
            final Location l1 = this.regionLocation(i, true);
            final Location l2 = this.regionLocation(i, false);
            if (ZoneVector.contains(p.getLocation(), l1.getBlockX(), l1.getBlockY(), l1.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ())) {
                id = i;
                break;
            }
        }
        return id;
    }
    
    public Location regionLocation(final int id, final boolean minMax) {
        this.sql.connect();
        Location loc = new Location((World)null, 0.0, 0.0, 0.0);
        try {
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, id);
            final ResultSet results = this.sql.Quary(statement);
            final Insert_SQL iSql = new Insert_SQL();
            if (iSql.regionExists(id)) {
                results.next();
                if (minMax) {
                    final int x = results.getInt("min_X");
                    final int y = results.getInt("min_Y");
                    final int z = results.getInt("min_Z");
                    final String world = results.getString("World");
                    loc = new Location(Bukkit.getServer().getWorld(world), (double)x, (double)y, (double)z);
                }
                else {
                    final int x = results.getInt("max_X");
                    final int y = results.getInt("max_Y");
                    final int z = results.getInt("max_Z");
                    final String world = results.getString("World");
                    loc = new Location(Bukkit.getServer().getWorld(world), (double)x, (double)y, (double)z);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }
}
