// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.block.data.Lightable;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import Main.PrisonType;
import org.bukkit.event.player.PlayerInteractEvent;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class PoweredRedstoneLamp implements Listener
{
    PlayerDataHandler pdh;
    
    public PoweredRedstoneLamp() {
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        if (this.pdh.getData(p).getType() != PrisonType.ADMIN) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (b != null && b.getType() == Material.REDSTONE_LAMP) {
                final BlockData data = b.getBlockData();
                if (data instanceof Lightable) {
                    ((Lightable)data).setLit(true);
                    b.setBlockData(data);
                    e.setCancelled(true);
                }
            }
        }
    }
}
