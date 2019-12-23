// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockLocation implements Listener
{
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) throws InterruptedException {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (p.hasPermission("prison.admin.blocklocator")) {
            final ItemStack is = e.getItem();
            if (e.getItem() != null && is.getType().equals((Object)Material.NAME_TAG) && e.getItem().hasItemMeta() && is.getItemMeta().getDisplayName().equalsIgnoreCase("§c§l§nBlockLocator") && (a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK)) {
                final Location loc = e.getClickedBlock().getLocation();
                final int x = loc.getBlockX();
                final int y = loc.getBlockY();
                final int z = loc.getBlockZ();
                Chat.info(p, "BlockLocator >> §cX§f§l " + x + " §cY§f§l " + y + " §cZ§f§l " + z);
                e.setCancelled(true);
            }
        }
        else {
            Chat.noPerm(p);
        }
    }
    
    public String timestampToDate(final long timestamp) {
        final Date date = new Date(timestamp);
        final DateFormat formatter = new SimpleDateFormat("mm:ss");
        final String dateFormatted = formatter.format(date);
        return dateFormatted;
    }
}
