// 
// Decompiled by Procyon v0.5.36
// 

package Needs;

import org.bukkit.plugin.Plugin;
import Main.Main;
import java.util.Iterator;
import Main.Chat;
import Main.PrisonType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.List;
import Player.Data.PlayerDataHandler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.Listener;

public class NeedsUpdater implements Listener
{
    BukkitTask update;
    Updator updat;
    PlayerDataHandler pdh;
    private static List<Player> players;
    
    static {
        NeedsUpdater.players = new ArrayList<Player>();
    }
    
    public NeedsUpdater() {
        this.updat = new Updator();
        this.pdh = new PlayerDataHandler();
    }
    
    public void update() {
        this.update = new BukkitRunnable() {
            public void run() {
                for (final Player p : NeedsUpdater.players) {
                    if (p == null) {
                        NeedsUpdater.players.remove(p);
                    }
                    if (NeedsUpdater.this.pdh.getData(p).getType() == PrisonType.PRISONER) {
                        NeedsUpdater.this.updat.loseWC(p);
                        NeedsUpdater.this.updat.loseThirst(p);
                        NeedsUpdater.this.updat.loseSleep(p);
                        NeedsUpdater.this.updat.loseHygiene(p);
                    }
                    else {
                        this.cancel();
                        Chat.print(Chat.getException(0, 38, this.getClass()));
                    }
                }
            }
        }.runTaskTimerAsynchronously((Plugin)Main.getInstance(), 0L, 1800L);
    }
    
    public void addPlayer(final Player p) {
        if (!NeedsUpdater.players.contains(p)) {
            NeedsUpdater.players.add(p);
        }
    }
    
    public void removePlayer(final Player p) {
        if (NeedsUpdater.players.contains(p)) {
            NeedsUpdater.players.remove(p);
        }
    }
}
