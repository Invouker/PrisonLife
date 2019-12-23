// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import Cells.CellDataProvider;
import Main.Main;

public class OpenCell
{
    long time;
    static CellDataProvider cd;
    
    static {
        OpenCell.cd = new CellDataProvider();
    }
    
    public OpenCell() {
        this.time = DayLight.getTime();
    }
    
    public static void controlDoor() {
        new BukkitRunnable() {
            World w = Bukkit.getServer().getWorld(Main.mainWorld);
            
            public void run() {
                for (int i = 1; i < Bukkit.getServer().getMaxPlayers(); ++i) {
                    final int doorX = OpenCell.cd.getICell(i, "Door.X");
                    final int doorY = OpenCell.cd.getICell(i, "Door.Y");
                    final int doorZ = OpenCell.cd.getICell(i, "Door.Z");
                    final Location doorLoc = new Location(this.w, (double)doorX, (double)doorY, (double)doorZ);
                    final Block b = this.w.getBlockAt(doorLoc);
                    if (b.getType() == Material.IRON_DOOR) {
                        OpenCell.setDoorOpen(b, true);
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1000L);
    }
    
    public static void setDoorOpen(final Block block, final boolean open) {
        if (block.getType() == Material.IRON_DOOR) {
            if (block.getData() < 4) {
                if (open) {
                    block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                }
            }
            else if (!open) {
                block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
            }
            block.getState().update();
        }
    }
}
