// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import java.util.Random;
import Items.ItemBuilder;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import Main.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.Listener;

public class DeliverTake implements Listener
{
    Washing wash;
    
    public DeliverTake() {
        this.wash = new Washing();
    }
    
    @EventHandler
    public void on(final PlayerDropItemEvent e) {
        final Item item = e.getItemDrop();
        final ItemStack is = item.getItemStack();
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && (is.getItemMeta().getDisplayName().contains("Dirty") || is.getItemMeta().getDisplayName().contains("Clean"))) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (!this.wash.isWasher(p)) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (b != null && b.getType() == Material.CHEST) {
                final World w = b.getLocation().getWorld();
                if (b.getLocation().equals((Object)new Location(w, -453.0, 84.0, 169.0))) {
                    final ItemStack is = p.getInventory().getItemInMainHand();
                    if (is == null) {
                        return;
                    }
                    if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().contains("Clean")) {
                        is.setAmount(is.getAmount() - 1);
                        this.succesfullyClean(p);
                    }
                    e.setCancelled(true);
                }
                if (b.getLocation().equals((Object)new Location(w, -449.0, 84.0, 169.0))) {
                    if (!this.hasDirtyClothe(p)) {
                        p.openInventory(this.createDirtyInventory());
                    }
                    else {
                        Chat.print("M\u00e1\u0161 \u0161pinav\u00e9 oble\u010denie u seba!");
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
    
    public void succesfullyClean(final Player p) {
        Chat.print("Hr\u00e1\u010d " + p.getName() + " uspe\u0161ne doru\u010dil vy\u010disten\u00fd item!");
    }
    
    public boolean hasDirtyClothe(final Player p) {
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
            final ItemStack is = contents[i];
            if (is != null) {
                if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().contains("Dirty")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Inventory createDirtyInventory() {
        final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 27, "§7Dirty Clothes");
        for (int i = 0; i < 7; ++i) {
            final ItemStack is = new ItemBuilder(this.getRandomLeather(), 1).toItemStack();
            final ItemStack fIs = new ItemBuilder(is).setName("§7Dirty " + is.getType().toString().replaceAll("_", " ")).toItemStack();
            inv.setItem(this.randomPosition(26), fIs);
        }
        return inv;
    }
    
    public Material getRandomLeather() {
        switch (this.randomPosition(4)) {
            case 0: {
                return Material.LEATHER_BOOTS;
            }
            case 1: {
                return Material.LEATHER_CHESTPLATE;
            }
            case 2: {
                return Material.LEATHER_LEGGINGS;
            }
            case 3: {
                return Material.LEATHER_HELMET;
            }
            default: {
                return Material.LEATHER_BOOTS;
            }
        }
    }
    
    public int randomPosition(final int bound) {
        final Random rand = new Random();
        return rand.nextInt(bound);
    }
}
