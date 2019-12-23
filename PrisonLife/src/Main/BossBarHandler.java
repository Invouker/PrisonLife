// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarHandler
{
    private BossBar bs;
    
    public void createBossBar(final String title) {
        this.bs = Bukkit.getServer().createBossBar(title, BarColor.YELLOW, BarStyle.SEGMENTED_20, new BarFlag[0]);
    }
    
    public void addPlayer(final Player p) {
        this.bs.addPlayer(p);
    }
    
    public void removePlayer(final Player p) {
        this.bs.removePlayer(p);
    }
    
    public void addFlag(final BarFlag flag) {
        this.bs.addFlag(flag);
    }
    
    public void setProgress(final double progress) {
        this.bs.setProgress(progress);
    }
    
    public void setColor(final BarColor color) {
        this.bs.setColor(color);
    }
    
    public void setStyle(final BarStyle style) {
        this.bs.setStyle(style);
    }
    
    public BossBar getBossBar() {
        return this.bs;
    }
    
    public BossBar setTime(final long update, final int time, final boolean progress) {
        new BukkitRunnable() {
            int until = time;
            
            public void run() {
                if (this.until > 0) {
                    if (progress) {
                        BossBarHandler.this.bs.setProgress((double)(this.until / time));
                    }
                    --this.until;
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, update);
        return this.bs;
    }
}
