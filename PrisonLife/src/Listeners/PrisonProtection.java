// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class PrisonProtection implements Listener
{
    PlayerDataHandler pdh;
    
    public PrisonProtection() {
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (this.pdh.getData(p).getType() != PrisonType.ADMIN) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void on(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (this.pdh.getData(p).getType() != PrisonType.ADMIN) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND) {
            e.setCancelled(true);
        }
        if (this.pdh.getData(p).getType() == PrisonType.LOBBY) {
            e.setCancelled(true);
            return;
        }
        if (this.pdh.getData(p).getType() == PrisonType.ADMIN) {
            return;
        }
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (e.getItem() != null) {
            if (e.getItem().getType().isEdible()) {
                return;
            }
            if (e.getItem().getType() == Material.POTION) {
                return;
            }
        }
        if (a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK || a == Action.PHYSICAL) {
            final Block b = e.getClickedBlock();
            if (b != null) {
                if (b.getType().toString().endsWith("_BED")) {
                    return;
                }
                if (b.getType() == Material.CHEST) {
                    return;
                }
            }
        }
        e.setCancelled(true);
    }
}
