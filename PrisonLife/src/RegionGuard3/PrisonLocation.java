// 
// Decompiled by Procyon v0.5.36
// 

package RegionGuard3;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import Cells.CellDataProvider;
import Main.Chat;
import Main.PlayerDataListener;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class PrisonLocation implements Listener
{
    PlayerDataListener pd;
    CellDataProvider cd;
    public static int click_Right_X;
    public static int click_Right_Y;
    public static int click_Right_Z;
    public static int click_Left_X;
    public static int click_Left_Y;
    public static int click_Left_Z;
    public static int door_allocated_X;
    public static int door_allocated_Y;
    public static int door_allocated_Z;
    
    static {
        PrisonLocation.click_Right_X = 0;
        PrisonLocation.click_Right_Y = 0;
        PrisonLocation.click_Right_Z = 0;
        PrisonLocation.click_Left_X = 0;
        PrisonLocation.click_Left_Y = 0;
        PrisonLocation.click_Left_Z = 0;
        PrisonLocation.door_allocated_X = 0;
        PrisonLocation.door_allocated_Y = 0;
        PrisonLocation.door_allocated_Z = 0;
    }
    
    public PrisonLocation() {
        this.pd = new PlayerDataListener();
        this.cd = new CellDataProvider();
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final PlayerDataHandler pdh = new PlayerDataHandler();
        if (p.getGameMode().equals((Object)GameMode.CREATIVE) || pdh.getData(p).getType() == PrisonType.ADMIN) {
            if (p.hasPermission("Prison.Setup")) {
                if (e.getItem() != null && e.getItem().getType().equals((Object)Material.LEAD) && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals("§c§l§nPrison Area Selector")) {
                    if (e.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK)) {
                        final Location loc = e.getClickedBlock().getLocation();
                        PrisonLocation.click_Right_X = (int)loc.getX();
                        PrisonLocation.click_Right_Y = (int)loc.getY();
                        PrisonLocation.click_Right_Z = (int)loc.getZ();
                        e.setCancelled(true);
                        Chat.info(p, "§6Right Click (X: " + loc.getX() + ", Y: " + loc.getY() + " Z: " + loc.getZ() + ").");
                    }
                    else if (e.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) {
                        final Location loc = e.getClickedBlock().getLocation();
                        PrisonLocation.click_Left_X = (int)loc.getX();
                        PrisonLocation.click_Left_Y = (int)loc.getY();
                        PrisonLocation.click_Left_Z = (int)loc.getZ();
                        e.setCancelled(true);
                        Chat.info(p, "§6Left Click (X: " + loc.getX() + ", Y: " + loc.getY() + " Z: " + loc.getZ() + ").");
                    }
                }
            }
            else {
                Chat.noPerm(p);
            }
        }
    }
}
