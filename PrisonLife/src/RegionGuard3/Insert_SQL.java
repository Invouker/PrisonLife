// 
// Decompiled by Procyon v0.5.36
// 

package RegionGuard3;

import org.bukkit.World;
import org.bukkit.entity.Player;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Main.MySQL;
import Main.Main;

public class Insert_SQL
{
    Main plugin;
    MySQL sql;
    
    public Insert_SQL() {
        this.plugin = (Main)Main.getPlugin((Class)Main.class);
        this.sql = new MySQL("region_guard");
    }
    
    public boolean regionExists(final int id) {
        try {
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE id=?");
            statement.setInt(1, id);
            final ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void createRegion(final Player p, final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ, final World w) {
        try {
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE ID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            final PreparedStatement insert = this.sql.getConnection().prepareStatement("INSERT INTO " + this.sql.getTable() + " (name,min_X,min_Y,min_Z,max_X,max_Y,max_Z,World) VALUES (?,?,?,?,?,?,?,?)");
            insert.setString(1, p.getName());
            insert.setInt(2, minX);
            insert.setInt(3, minY);
            insert.setInt(4, minZ);
            insert.setInt(5, maxX);
            insert.setInt(6, maxY);
            insert.setInt(7, maxZ);
            insert.setString(8, w.getName());
            this.sql.QuaryUpdate(insert);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
