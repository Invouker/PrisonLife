// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import Gangs.Gang;
import Items.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.SmartInvsPlugin;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.opener.InventoryOpener;

public class SettingsGUI implements InventoryOpener
{
    private Gang data;
    
    public SettingsGUI(final Gang data) {
        this.data = data;
    }
    
    public SmartInventory getInventory(final String name) {
        return SmartInventory.builder().provider((InventoryProvider)new Provider()).size(3, 9).title("§7Gang settings of " + name).build();
    }
    
    public Inventory open(final SmartInventory inv, final Player p) {
        final InventoryManager manager = SmartInvsPlugin.manager();
        final Inventory handle = Bukkit.createInventory((InventoryHolder)p, inv.getRows() * inv.getColumns(), inv.getTitle());
        this.fill(handle, (InventoryContents)manager.getContents(p).get());
        p.openInventory(handle);
        return handle;
    }
    
    public boolean supports(final InventoryType type) {
        return InventoryType.CHEST == type;
    }
    
    private class Provider implements InventoryProvider
    {
        public void init(final Player player, final InventoryContents contents) {
            int count = 0;
            if (SettingsGUI.this.data.getPlayers() != null) {
                count = SettingsGUI.this.data.getPlayers().size();
            }
            contents.set(0, 0, ClickableItem.empty(new ItemBuilder(Material.PAPER).setName("§eInform\u00e1cie").setLoreLine("", 0).setLoreLine("§7Majite\u013e gangu§f " + SettingsGUI.this.data.getFounder(), 1).setLoreLine("§7Po\u010det \u010dlenov§f " + count, 2).setLoreLine("", 3).toItemStack()));
            contents.set(0, 1, ClickableItem.of(new ItemBuilder(Material.BOOK).setName("§Vstup do interieru").toItemStack(), e -> {}));
        }
        
        public void update(final Player player, final InventoryContents contents) {
        }
    }
}
