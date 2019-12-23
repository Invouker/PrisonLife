// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import Main.Chat;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;
import Solitary.SolitaryControl;
import Solitary.solitaryCache;

public class PlayerDeathListener implements Listener
{
    PlayerDataHandler pdh;
    
    public PlayerDeathListener() {
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        final SolitaryControl sc = new SolitaryControl();
        if (sc.getPlayerSolitary(p) != -1) {
            final int solitary = sc.getPlayerSolitary(p);
            final double x = solitaryCache.getD(solitary, solitaryCache.pos_X);
            final double y = solitaryCache.getD(solitary, solitaryCache.pos_Y);
            final double z = solitaryCache.getD(solitary, solitaryCache.pos_Z);
            final String world = solitaryCache.getS(solitary, solitaryCache.world);
            final World w = Bukkit.getWorld(world);
            if (w != null) {
                final Location loc = new Location(w, x, y, z);
                p.setHealth(20.0);
                p.teleport(loc);
            }
        }
    }
    
    @EventHandler
    public void on(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                e.setCancelled(true);
                return;
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                this.pdh.getData(p).setPoints(this.pdh.getData(p).getPoints() - 1);
                return;
            }
            if (e.getDamager() instanceof Player && this.pdh.getData(p).getType() == PrisonType.WARDEN && this.pdh.getData((Player)e.getDamager()).getType() == PrisonType.WARDEN) {
                final Player pAgr = (Player)e.getDamager();
                if (this.pdh.getData(pAgr).getPoints() - 2 < 6700) {
                    Bukkit.dispatchCommand((CommandSender)pAgr, "changeduty");
                }
                if (this.pdh.getData(pAgr).getWarden_attack_warning() >= 3) {
                    this.pdh.getData(pAgr).setWarden_attack_warning(0);
                    Bukkit.dispatchCommand((CommandSender)pAgr, "changeduty");
                    this.pdh.getData(pAgr).setPoints(this.pdh.getData(pAgr).getPoints() - 2);
                    Chat.info(pAgr, "Neuto\u010d na svoj\u00edch! Za tento \u010din ti odr\u00e1tam body! (" + this.pdh.getData(pAgr).getWarden_attack_warning() + "/3)");
                }
                else {
                    this.pdh.getData(pAgr).setWarden_attack_warning(this.pdh.getData(pAgr).getWarden_attack_warning() + 1);
                }
            }
            p.getHealth();
            e.getDamage();
        }
    }
}
