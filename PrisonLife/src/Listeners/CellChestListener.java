// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import Cells.CellDataProvider;
import Items.LoadItem;
import Items.SaveItem;
import Main.Chat;
import Main.ConfigListener;
import Main.Main;

public class CellChestListener implements Listener
{
    Main plugin;
    CellDataProvider cd;
    
    public CellChestListener() {
        this.plugin = (Main)Main.getPlugin((Class)Main.class);
        this.cd = new CellDataProvider();
    }
    
    @EventHandler
    public void inInteractEvent(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK) {
            final Block block = e.getClickedBlock();
            if (block.getState() instanceof Chest) {
                final Chest chest = (Chest)block.getState();
                final Location loc = chest.getLocation();
                for (int maxPlayer = Bukkit.getServer().getMaxPlayers(), i = 1; i < maxPlayer; ++i) {
                    final int x = this.cd.getICell(i, "Chest.X");
                    final int y = this.cd.getICell(i, "Chest.Y");
                    final int z = this.cd.getICell(i, "Chest.Z");
                    final String world = this.cd.getSCell(i, "Chest.World");
                    final String owner = this.cd.getSCell(i, "Sign.Owner");
                    if (loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z && loc.getWorld().getName().equals(world)) {
                        if (p.getName().equals(owner)) {
                            e.setCancelled(true);
                            p.openInventory(this.getChestInventory(p));
                            break;
                        }
                        Chat.send(p, "prison.cell.dont-own-chest", new String[0]);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void InventoryClose(final InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        final Inventory inv = e.getInventory();
        if (e.getView().getTitle().equals("§8Player chest - §7" + p.getName())) {
            new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), "Vezen.Chest", inv).save();
        }
    }
    
    public Inventory getChestInventory(final Player p) {
        final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 27, "§8Player chest - §7" + p.getName());
        try {
            final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), "Vezen.Chest");
            if (li != null) {
                final List<ItemStack> items = li.getItems();
                if (!items.isEmpty()) {
                    int i = 0;
                    for (final ItemStack is : items) {
                        inv.setItem(i, is);
                        ++i;
                    }
                }
            }
        }
        catch (NullPointerException ex) {}
        return inv;
    }
}
