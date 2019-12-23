// 
// Decompiled by Procyon v0.5.36
// 

package Cells;

import org.bukkit.World;
import Utils.Functions;
import Main.Main;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import Player.Data.PlayerDataHandler;

public class CellAsigner
{
    static CellDataProvider cd;
    static PlayerDataHandler pdh;
    
    static {
        CellAsigner.cd = new CellDataProvider();
        CellAsigner.pdh = new PlayerDataHandler();
    }
    
    public static void assignCell(final Player p) {
        final int cell = getEmptyCell();
        p.teleport(playerCellSpawnLocation(cell));
        CellAsigner.cd.setSCell(cell, "Sign.Owner", p.getName());
        CellAsigner.cd.setBCell(cell, "isOwned", true);
        CellAsigner.pdh.getData(p).setCell(cell);
        p.setBedSpawnLocation(playerCellSpawnLocation(cell), true);
        final SignListener sh = new SignListener();
        sh.updateCellSign(cell);
    }
    
    public static int getEmptyCell() {
        int empty = -1;
        for (int MaxSlots = Bukkit.getServer().getMaxPlayers(), i = 1; i < MaxSlots; ++i) {
            if (!CellAsigner.cd.getBCell(i, "isOwned")) {
                empty = i;
                break;
            }
        }
        return empty;
    }
    
    public static Location playerCellSpawnLocation(final int cell) {
        final double x = CellAsigner.cd.getDCell(cell, "Spawn.X");
        final double y = CellAsigner.cd.getDCell(cell, "Spawn.Y");
        final double z = CellAsigner.cd.getDCell(cell, "Spawn.Z");
        final float yaw = (float)CellAsigner.cd.getDCell(cell, "Spawn.Yaw");
        final World w = Bukkit.getServer().getWorld(Main.mainWorld);
        final Location loc = new Location(w, x, y, z);
        loc.setYaw((float)Functions.getYaw(yaw));
        return loc;
    }
    
    public static void unloadCellData(final int cell) {
        final SignListener sh = new SignListener();
        CellAsigner.cd.setSCell(cell, "Sign.Owner", "None");
        CellAsigner.cd.setBCell(cell, "isOwned", false);
        sh.updateCellSign(cell);
    }
    
    public static void unloadCellByPlayer(final Player p) {
        for (int MaxSlots = Bukkit.getServer().getMaxPlayers(), i = 1; i < MaxSlots; ++i) {
            if (CellAsigner.cd.getSCell(i, "Sign.Owner").equalsIgnoreCase(p.getName())) {
                unloadCellData(i);
                break;
            }
        }
    }
}
