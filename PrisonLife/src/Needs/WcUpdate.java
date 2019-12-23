// 
// Decompiled by Procyon v0.5.36
// 

package Needs;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Cells.CellDataProvider;
import Main.BossBarHandler;
import Main.Chat;
import Main.Main;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class WcUpdate implements Listener
{
    private ArmorStand as;
    private BukkitTask task;
    private Location beforeWc;
    private static boolean canMove;
    CellDataProvider cd;
    PlayerDataHandler pdh;
    
    static {
        WcUpdate.canMove = true;
    }
    
    public WcUpdate() {
        this.beforeWc = null;
        this.cd = new CellDataProvider();
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        if (a != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        final Block b = e.getClickedBlock();
        if (b.getType() != Material.HOPPER) {
            return;
        }
        if (this.pdh.getData(p).getType() != PrisonType.PRISONER) {
            Chat.send(p, "must-be.Prisoner", new String[0]);
            return;
        }
        final BossBarHandler bb = new BossBarHandler();
        bb.createBossBar("WC Cooldown");
        bb.setColor(BarColor.YELLOW);
        bb.setStyle(BarStyle.SOLID);
        bb.setProgress(this.pdh.getData(p).getWC() / 100);
        if (this.pdh.getData(p).getWC() >= 100) {
            bb.removePlayer(p);
            Chat.send(p, "wc.recently-wc", new String[0]);
            e.setCancelled(true);
            return;
        }
        this.beforeWc = p.getLocation();
        final List<Entity> en = (List<Entity>)p.getNearbyEntities(3.0, 3.0, 3.0);
        for (final Entity ent : en) {
            if (ent instanceof ArmorStand) {
                ent.addPassenger((Entity)p);
                this.as = (ArmorStand)ent;
            }
        }
        if (this.as == null) {
            Chat.print("Error: " + this.getWcLoc(p) + " ArmorStand k cele sa nena\u0161iel !");
            return;
        }
        bb.addPlayer(p);
        WcUpdate.canMove = false;
        e.setCancelled(true);
        this.task = new BukkitRunnable() {
        int wc = WcUpdate.this.pdh.getData(p).getWC();
            
            public void run() {
                bb.setProgress(this.wc / 100.0);
                ++this.wc;
                if (this.wc >= 100) {
                    bb.removePlayer(p);
                    WcUpdate.this.task.cancel();
                    WcUpdate.access$1(true);
                    WcUpdate.this.as.removePassenger((Entity)p);
                    if (WcUpdate.this.beforeWc != null) {
                        p.teleport(WcUpdate.this.beforeWc);
                    }
                }
                WcUpdate.this.pdh.getData(p).setWC(this.wc);
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1L);
    }
    
    private int getWcLoc(final Player p) {
        for (int cell = 1; cell < 100; ++cell) {
            final int x = this.cd.getICell(cell, "Wc.X");
            final int y = this.cd.getICell(cell, "Wc.Y");
            final int z = this.cd.getICell(cell, "Wc.Z");
            final String name = this.cd.getSCell(cell, "Sign.Owner");
            final String world = this.cd.getSCell(cell, "Wc.World");
            final World w = Bukkit.getServer().getWorld(world);
            final Location loc = new Location(w, (double)x, (double)y, (double)z);
            if (p.getLocation().distance(loc) <= 3.0 && name.equals(p.getName())) {
                return cell;
            }
        }
        return -1;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void move(final PlayerMoveEvent move) {
        if (!WcUpdate.canMove) {
            final Location from = move.getFrom();
            final Location to = move.getTo();
            double x = Math.floor(from.getX());
            double z = Math.floor(from.getZ());
            if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
                x += 0.5;
                z += 0.5;
                move.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
            }
        }
    }
    
    static /* synthetic */ void access$1(final boolean canMove) {
        WcUpdate.canMove = canMove;
    }
}
