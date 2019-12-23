// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import Main.Chat;
import Main.Main;

public class Elevator implements Listener
{
    Main plugin;
    Material eMat;
    int range;
    public List<Location> elevators;
    
    public Elevator() {
        this.plugin = (Main)Main.getPlugin((Class)Main.class);
        this.eMat = Material.LAPIS_BLOCK;
        this.range = 40;
        this.elevators = new ArrayList<Location>();
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
    }
    
    @EventHandler
    public void createElevator(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (e.getBlockPlaced().getType() == this.eMat) {
            this.elevators.add(e.getBlock().getLocation());
            Chat.info(p, "V\u00fdtah bol vytvoren\u00fd !");
        }
    }
    
    @EventHandler
    public void onElevator(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final Location l = p.getLocation();
        final Location d = new Location(l.getWorld(), l.getX(), l.getY() - 1.0, l.getZ());
        final Location block = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        if (d.getBlock().getType() == this.eMat && (block.getBlock().getType() != Material.LADDER || block.getBlock().getType() != Material.VINE || block.getBlock().getType() != Material.WATER || (block.getBlock().getType() != Material.AIR && !p.isOnGround())) && p.isOnGround() && e.getFrom().getY() != e.getTo().getY() && !p.isFlying()) {
            int i;
            Location newlocation;
            for (i = 0, i = 0; i < this.range; ++i) {
                newlocation = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + i, p.getLocation().getZ());
                if (newlocation.getBlock().getType() == this.eMat) {
                    break;
                }
            }
            newlocation = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + i, p.getLocation().getZ());
            if (newlocation.getBlock().getType() == this.eMat) {
                final Location diff = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + i + 1.0, p.getLocation().getZ());
                if (newlocation.getBlock().getType() != diff.getBlock().getType()) {
                    float pitch = 0.0f;
                    float yaw = 0.0f;
                    pitch = p.getLocation().getPitch();
                    yaw = p.getLocation().getYaw();
                    final double ploc = newlocation.getBlock().getLocation().getY() + 1.0;
                    p.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getBlockX() + 0.5, ploc, p.getLocation().getBlockZ() + 0.5, yaw, pitch));
                }
            }
        }
    }
    
    @EventHandler
    public void onToggleSneakEvent(final PlayerToggleSneakEvent e) {
        final Player p = e.getPlayer();
        final Location l = p.getLocation();
        final Location d = new Location(l.getWorld(), l.getX(), l.getY() - 1.0, l.getZ());
        final Location block = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        if (p.isSneaking() && d.getBlock().getType() == this.eMat && (block.getBlock().getType() != Material.LADDER || block.getBlock().getType() != Material.VINE || block.getBlock().getType() != Material.WATER || (block.getBlock().getType() != Material.AIR && !p.isOnGround()))) {
            int i;
            Location newlocation;
            for (i = 0, i = 0; i < this.range; ++i) {
                newlocation = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 2.0 - i, p.getLocation().getZ());
                if (newlocation.getBlock().getType() == this.eMat && d.getBlock().getY() != newlocation.getBlock().getY()) {
                    break;
                }
            }
            newlocation = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 2.0 - i, p.getLocation().getZ());
            if (newlocation.getBlock().getType() == this.eMat && d.getBlock().getY() != newlocation.getBlock().getY() && d.getBlockY() - newlocation.getBlockY() > 2) {
                float pitch = 0.0f;
                float yaw = 0.0f;
                pitch = p.getLocation().getPitch();
                yaw = p.getLocation().getYaw();
                final double ploc = newlocation.getBlock().getLocation().getY() + 1.0;
                p.teleport(new Location(p.getWorld(), p.getLocation().getBlockX() + 0.5, ploc, p.getLocation().getBlockZ() + 0.5, yaw, pitch));
            }
        }
    }
}
