// 
// Decompiled by Procyon v0.5.36
// 

package Needs;

import java.util.List;
import java.util.Random;
import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import Main.PrisonType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import Main.Chat;
import Main.ZoneVector;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class HungerUpdate implements Listener
{
    PlayerDataHandler pdh;
    ArrayList<Player> hunger;
    BukkitTask task;
    
    public HungerUpdate() {
        this.pdh = new PlayerDataHandler();
        this.hunger = new ArrayList<Player>();
    }
    
    @EventHandler
    public void enterZone(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (ZoneVector.contains(p.getLocation(), -444.0, 77.0, 215.0, -446.0, 80.0, 221.0) || ZoneVector.contains(p.getLocation(), -458.0, 77.0, 215.0, -456.0, 80.0, 221.0)) {
            if (!this.hunger.contains(p)) {
                this.hunger.add(p);
                Chat.info(p, this.randText(p));
                this.updateHunger(p);
            }
        }
        else if (this.hunger.contains(p)) {
            this.hunger.remove(p);
            this.task.cancel();
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        this.hunger.remove(e.getPlayer());
        if (this.pdh.getData(e.getPlayer()).getType() == PrisonType.PRISONER) {
            this.task.cancel();
        }
    }
    
    public void updateHunger(final Player p) {
        this.task = new BukkitRunnable() {
            public void run() {
                final int hungry = p.getFoodLevel();
                final double health = p.getHealth();
                if (hungry != 20.0) {
                    p.setFoodLevel(hungry + 1);
                }
                if (health != 20.0) {
                    p.setHealth(health + 0.5);
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    
    public String randText(final Player p) {
        final Random rand = new Random();
        List<String> list = new ArrayList<String>();
        list = Chat.getTList("hunger.enter-text", p, p.getName());
        if (list.isEmpty()) {
            list.add("ERROR");
        }
        return list.get(rand.nextInt(list.size()));
    }
}
