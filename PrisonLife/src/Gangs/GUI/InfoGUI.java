// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Gangs.Gang;
import Items.ItemBuilder;
import Main.Chat;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.opener.InventoryOpener;

public class InfoGUI
{
    private Gang data;
    
    public InfoGUI(final Gang data) {
        this.data = data;
    }
    
    public SmartInventory getInventory(String name) {
        return SmartInventory.builder().provider((InventoryProvider)new Provider()).size(3, 9).title("§7Gang settings of " + name).build();
    }

    private class Provider implements InventoryProvider
    {
        public void init(final Player player, final InventoryContents contents) {
            int count = 0;
            if (InfoGUI.this.data.getPlayers() != null) {
                count = InfoGUI.this.data.getPlayers().size();
            }
            contents.set(0, 0, ClickableItem.empty(new ItemBuilder(Material.PAPER).setName("§eInformácie").setLoreLine("", 0).setLoreLine("§7Majite¾ gangu§f " + InfoGUI.this.data.getFounder(), 1).setLoreLine("§7Po\u010det \u010dlenov§f " + count, 2).setLoreLine("", 3).toItemStack()));
            contents.set(0, 1, ClickableItem.of(new ItemBuilder(Material.BOOK).setName("§Vstup do interieru").toItemStack(), e -> {}));
            contents.set(0, 1, ClickableItem.of(new ItemBuilder(Material.BOOK).setName("§eZoznam \u010dlenov").toItemStack(), e -> {}));
            SettingsGUI sGui;
            contents.set(0, 1, ClickableItem.of(new ItemBuilder(Material.BOOK).setName("§eNastavenia gangu").toItemStack(), e -> {
                //sGui = new SettingsGUI();
              //  sGui.getInventory(data.getName()).open((Player)e.getWhoClicked());
                Chat.print("test");
                return;
            }));
            
            contents.set(1, 1, ClickableItem.of(new ItemStack(Material.COMPASS), e -> {
            	ItemStack is = e.getCurrentItem();
                Chat.print("iS:" + is);
                e.setCancelled(true);
            }));
        }
        
        public void update(final Player player, final InventoryContents contents) {
        }
    }
}
