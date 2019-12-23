// 
// Decompiled by Procyon v0.5.36
// 

package Commands;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import Utils.Lang;
import Main.Chat;
import org.bukkit.event.inventory.InventoryClickEvent;
import Items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import Player.Data.PlayerDataHandler;
import org.bukkit.command.CommandExecutor;

public class langSelector implements CommandExecutor
{
    PlayerDataHandler pdh;
    
    public langSelector() {
        this.pdh = new PlayerDataHandler();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("language")) {
            final Player p = (Player)sender;
            p.openInventory(this.langInventory());
            return true;
        }
        return false;
    }
    
    public Inventory langInventory() {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "§7§lLang Selector");
        inv.setItem(10, new ItemBuilder(Material.RED_BANNER, 1).setName("§fSlovakia").setLoreLine("§7» Klikni pre nastavenie Sloven\u010diny!", 2).toItemStack());
        inv.setItem(13, new ItemBuilder(Material.BLUE_BANNER, 1).setName("§eEnglish").setLoreLine("§7» Click for setting up a English! !", 2).toItemStack());
        inv.setItem(16, new ItemBuilder(Material.WHITE_BANNER, 1).setName("§cCzech").setLoreLine("§7» Klikni pro nastaveni \u010ce\u0161\u010diny!", 2).toItemStack());
        return inv;
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final Inventory inv = e.getClickedInventory();
        final ItemStack is = e.getCurrentItem();
        final Player p = (Player)e.getWhoClicked();
        if (inv != null && e.getView().getTitle().equalsIgnoreCase("§7§lLang Selector") && is != null && is.hasItemMeta()) {
            if (is.getItemMeta().getDisplayName().equalsIgnoreCase("§fSlovakia")) {
                Chat.info(p, "Vybral si si jazyk §fSloven\u010dina");
                this.pdh.getData(p).setLang(Lang.SLOVAKIA);
                e.setCancelled(true);
                p.closeInventory();
            }
            else if (is.getItemMeta().getDisplayName().equalsIgnoreCase("§eEnglish")) {
                Chat.info(p, "You selected a English language !");
                this.pdh.getData(p).setLang(Lang.ENGLISH);
                e.setCancelled(true);
                p.closeInventory();
            }
            else if (is.getItemMeta().getDisplayName().equalsIgnoreCase("§cCzech")) {
                p.sendMessage("Vybral sis \u010cesky jazyk");
                this.pdh.getData(p).setLang(Lang.CZECH);
                e.setCancelled(true);
                p.closeInventory();
            }
        }
    }
}
