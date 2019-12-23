// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import Cells.CellDataProvider;
import Main.Chat;
import Main.Main;
import Main.PrisonType;
import Main.ZoneVector;
import Player.Data.PlayerDataHandler;

public class CloseCell
{
    long time;
    static CellDataProvider cd;
    static PlayerDataHandler pdh;
    
    static {
        CloseCell.cd = new CellDataProvider();
        CloseCell.pdh = new PlayerDataHandler();
    }
    
    public CloseCell() {
        this.time = DayLight.getTime();
    }
    
    public static void delayDoor() {
        new BukkitRunnable() {
            World w = Bukkit.getServer().getWorld(Main.mainWorld);
            
            public void run() {
                if (Bukkit.getServer().getOnlinePlayers().size() == 0 || Bukkit.getServer().getOnlinePlayers().isEmpty()) {
                    return;
                }
                for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                    for (int i = 1; i < Bukkit.getServer().getMaxPlayers(); ++i) {
                        final String name = CloseCell.cd.getSCell(i, "Sign.Owner");
                        if (name != null && name.equalsIgnoreCase(p.getName())) {
                            final int minX = CloseCell.cd.getICell(i, "Area.min.X");
                            final int minY = CloseCell.cd.getICell(i, "Area.min.Y");
                            final int minZ = CloseCell.cd.getICell(i, "Area.min.Z");
                            final int maxX = CloseCell.cd.getICell(i, "Area.max.X");
                            final int maxY = CloseCell.cd.getICell(i, "Area.max.Y");
                            final int maxZ = CloseCell.cd.getICell(i, "Area.max.Z");
                            if (ZoneVector.contains(p.getLocation(), minX, minY, minZ, maxX, maxY, maxZ)) {
                                Chat.send(p, "prison.cell.locked-in-cell", new String[0]);
                                CloseCell.pdh.getData(p).givePoints(5);
                                final int doorX = CloseCell.cd.getICell(i, "Door.X");
                                final int doorY = CloseCell.cd.getICell(i, "Door.Y");
                                final int doorZ = CloseCell.cd.getICell(i, "Door.Z");
                                final Location doorLoc = new Location(this.w, (double)doorX, (double)doorY, (double)doorZ);
                                final Block b = this.w.getBlockAt(doorLoc);
                                if (b.getType() != Material.IRON_DOOR) {
                                    return;
                                }
                                final BlockState bState = b.getState();
                                final Openable openable = (Openable)bState.getData();
                                if (openable.isOpen()) {
                                    openable.setOpen(false);
                                }
                                else {
                                    openable.setOpen(true);
                                }
                                bState.update();
                            }
                            else if (CloseCell.pdh.getData(p).getType() == PrisonType.PRISONER) {
                                Chat.send(p, "prison.cell.not-in-cell", new String[0]);
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("red").addEntry(p.getName());
                            }
                        }
                    }
                }
            }
        }.runTaskLater((Plugin)Main.getInstance(), 400L);
    }
    
    public void PlayCloseNoise(final Block doorBlock) {
        if (doorBlock != null) {
            final BlockData data = doorBlock.getBlockData();
            if (data instanceof Door) {
                doorBlock.getWorld().playSound(doorBlock.getLocation(), Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.0f, 1.0f);
            }
        }
    }
}
