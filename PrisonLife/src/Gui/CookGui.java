// 
// Decompiled by Procyon v0.5.36
// 

package Gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import Items.ITEMS;
import Items.ItemBuilder;
import Main.Chat;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.SmartInvsPlugin;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.opener.InventoryOpener;

public class CookGui implements InventoryOpener
{
    public SmartInventory getInventory(final Player player) {
        return SmartInventory.builder().id("cookGui").provider((InventoryProvider)new cookGui()).size(5, 9).title(ChatColor.DARK_GRAY + "Greg's food shop").closeable(true).build();
    }
    
    public Inventory open(final SmartInventory inv, final Player p) {
        final InventoryManager manager = SmartInvsPlugin.manager();
        final Inventory handle = Bukkit.createInventory((InventoryHolder)p, inv.getRows() * inv.getColumns(), inv.getTitle());
        this.fill(handle, (InventoryContents)manager.getContents(p).get());
        p.openInventory(handle);
        return handle;
    }
    
    public boolean supports(final InventoryType type) {
        return type == InventoryType.CHEST;
    }
    
    public ClickableItem getShopItem(final Player p, final ITEMS item, final int cost, final int give) {
       
        return ClickableItem.of(new ItemBuilder(item.getItem()).setLoreLine("§7Cost §f" + cost + " coins", 2).setLoreLine("§7You will get§f " + give, 3).toItemStack(), e -> {
        	ItemStack is;
            if (this.removeItemFromPlayer(p, ITEMS.COIN.getItem(), cost)) {
                Chat.send(p, "shop.buy", item.getName());
                is = new ItemBuilder(item.getItem()).removeLore().setRare(item.getItemRarity(), 1).setAmount(give).toItemStack();
                p.getInventory().addItem(new ItemStack[] { is });
            }
            else {
                Chat.send(p, "shop.no-coins", new String[0]);
            }
        });
    }
    
    public boolean removeItemFromPlayer(final Player p, final ItemStack is, final int amount) {
        for (final ItemStack item : p.getInventory()) {
            if (item == null) {
                continue;
            }
            if (item.isSimilar(is) && item.getAmount() >= amount) {
                item.setAmount(item.getAmount() - amount);
                return true;
            }
        }
        return false;
    }
    
    private class cookGui implements InventoryProvider
    {
        public void init(final Player p, final InventoryContents contents) {
            contents.add(CookGui.this.getShopItem(p, ITEMS.BEETROOT, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKIE, 2, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.MELON_SLICE, 3, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.CARROT, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.APPLE, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.BAKED_POTATO, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.BREAD, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_COD, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_RABBIT, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.BEETROOT_SOUP, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_CHICKEN, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_MUTTON, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_SALMON, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.MUSHROOM_STEW, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.COOKED_PORKCHOP, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.PUMPKIN_PIE, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.STEAK, 1, 2));
            contents.add(CookGui.this.getShopItem(p, ITEMS.RABBIT_STEW, 1, 2));
            contents.set(3, 0, CookGui.this.getShopItem(p, ITEMS.BOTTLE_01L, 5, 1));
            contents.set(3, 1, CookGui.this.getShopItem(p, ITEMS.BOTTLE_03L, 14, 1));
            contents.set(3, 2, CookGui.this.getShopItem(p, ITEMS.BOTTLE_05L, 22, 1));
            contents.set(4, 4, ClickableItem.of(ITEMS.CLOSE_MENU.getItem(), e -> p.closeInventory()));
        }
        
        public void update(final Player p, final InventoryContents contents) {
        }
    }
}
