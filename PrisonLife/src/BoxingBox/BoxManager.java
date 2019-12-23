// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.plugin.Plugin;
import Main.Main;
import Main.PlayerFreezing;
import Main.Chat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

public class BoxManager
{
    BoxHandler bh;
    private int phase;
    
    public BoxManager() {
        this.bh = new BoxHandler();
        this.phase = 0;
    }
    
    public void startBox(final int box) {
    }
    
    public void counterBox(final int box) {
        if (this.isFull(box)) {
            final String name1 = BoxData.getBox(box).getName1();
            final String name2 = BoxData.getBox(box).getName2();
            final Player p1 = Bukkit.getServer().getPlayer(name1);
            final Player p2 = Bukkit.getServer().getPlayer(name2);
            new BukkitRunnable() {
                int counter = 5;
                
                public void run() {
                    if (p1 == null || p2 == null) {
                        this.cancel();
                        Chat.sendAll("box.go-out", new String[0]);
                        return;
                    }
                    --this.counter;
                    if (this.counter > 0) {
                        p1.sendTitle("§a" + this.counter, "", 20, 40, 20);
                        p2.sendTitle("§a" + this.counter, "", 20, 40, 20);
                    }
                    else if (this.counter == 0) {
                        p1.sendTitle("§a§lSTART", "", 20, 40, 20);
                        p2.sendTitle("§a§lSTART", "", 20, 40, 20);
                        PlayerFreezing.removePlayer(p1);
                        PlayerFreezing.removePlayer(p2);
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
        }
    }
    
    public int getPhase(final int box) {
        return this.phase;
    }
    
    private boolean isFull(final int box) {
        final String name1 = BoxData.getBox(box).getName1();
        final String name2 = BoxData.getBox(box).getName2();
        return !name1.equalsIgnoreCase("- NONE -") && !name2.equalsIgnoreCase("- NONE -");
    }
}
