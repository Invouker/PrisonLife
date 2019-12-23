// 
// Decompiled by Procyon v0.5.36
// 

package Abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import Main.Chat;
import Main.Main;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class Boxing_bag implements Listener
{
    HashMap<Integer, Location> box;
    StrengthLevel sl;
    PlayerDataHandler pdh;
    
    public Boxing_bag() {
        this.box = new HashMap<Integer, Location>();
        this.sl = new StrengthLevel();
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void onBox(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (this.pdh.getData(p).getType() == PrisonType.PRISONER && p.getGameMode().equals((Object)GameMode.SURVIVAL)) {
            final Location loc = e.getBlock().getLocation();
            final World w = Bukkit.getServer().getWorld(Main.mainWorld);
            this.box.put(0, new Location(w, -426.0, 79.0, 239.0));
            this.box.put(1, new Location(w, -426.0, 78.0, 239.0));
            this.box.put(2, new Location(w, -426.0, 79.0, 241.0));
            this.box.put(3, new Location(w, -426.0, 78.0, 241.0));
            this.box.put(4, new Location(w, -426.0, 79.0, 243.0));
            this.box.put(5, new Location(w, -426.0, 78.0, 243.0));
            this.box.put(6, new Location(w, -426.0, 79.0, 245.0));
            this.box.put(7, new Location(w, -426.0, 78.0, 245.0));
            this.box.put(8, new Location(w, -426.0, 79.0, 247.0));
            this.box.put(9, new Location(w, -426.0, 78.0, 247.0));
            final Material b = e.getBlock().getType();
            if (b.equals((Object)Material.RED_WOOL)) {
                for (int i = 0; i <= this.box.size(); ++i) {
                    if (loc != null) {
                        if (this.box.get(i) != null) {
                            if (this.box.get(i).getBlockX() == loc.getBlockX() && this.box.get(i).getBlockY() == loc.getBlockY() && this.box.get(i).getBlockZ() == loc.getBlockZ()) {
                                e.setCancelled(true);
                                this.pdh.getData(p).giveStrengthExp(17);
                                Chat.info(p, "GETEXP: " + this.pdh.getData(p).getStrengthExp() + " LEVEL " + this.pdh.getData(p).getStrength());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
