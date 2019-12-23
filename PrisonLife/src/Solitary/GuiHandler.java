// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import Items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiHandler
{
    public Inventory punishments() {
        final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9, "§c§lPunishments Gui");
        inv.setItem(0, new ItemBuilder(Material.ANVIL, 1).setName("§cSpamming").setLoreLine("10 minutes", 3).toItemStack());
        return inv;
    }
}
