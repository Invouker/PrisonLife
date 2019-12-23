// 
// Decompiled by Procyon v0.5.36
// 

package Gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Iterator;
import Player.Data.PlayerData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Statistic;
import Utils.Functions;
import Items.ItemBuilder;
import org.bukkit.Material;
import fr.minuskube.inv.ClickableItem;
import Items.ITEMS;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.inventory.InventoryType;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import fr.minuskube.inv.SmartInvsPlugin;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;
import fr.minuskube.inv.opener.InventoryOpener;

public class PlayerGUI implements InventoryOpener
{
    public SmartInventory getInventory(final Player target) {
        return SmartInventory.builder().id("playerGui").provider((InventoryProvider)new playerGui(target)).size(6, 9).title(ChatColor.DARK_GRAY + target.getName() + "'s statistic").closeable(true).build();
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
    
    public String getState(final boolean state) {
        return state ? "Yes" : "No";
    }
    
    private class playerGui implements InventoryProvider
    {
        PlayerDataHandler pdh;
        private final Player target;
        
        public playerGui(final Player target) {
            this.pdh = new PlayerDataHandler();
            this.target = target;
        }
        
        public void init(final Player p, final InventoryContents contents) {
            contents.fillRow(1, ClickableItem.empty(ITEMS.BORDER_MENU.getItem()));
            contents.set(0, 4, ClickableItem.empty(ITEMS.BORDER_MENU.getItem()));
            contents.set(1, 4, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§eInformation").setLoreLine("", 0).setLoreLine("Prv\u00fd riadok v \u013eavo sa nach\u00e1dzaju inform\u00e1cie o hr\u00e1\u010dovy.", 1).setLoreLine("Prv\u00fd riadok v pravo je hr\u00e1\u010dov armor.", 2).setLoreLine("Pod t\u00fdmto, prv\u00fd riadok je hr\u00e1\u010dov hotbar.", 3).setLoreLine("A \u010fal\u0161ie riadky je hr\u00e1\u010dov invent\u00e1r.", 4).setLoreLine("", 5).setLoreLine("§8§oAk klikne\u0161 na toto, zatvor\u00ed\u0161 t\u00fdm invent\u00e1r!", 6).toItemStack(), e -> p.closeInventory()));
        }
        
        public void update(final Player p, final InventoryContents contents) {
            final PlayerData pd = this.pdh.getData(this.target);
            if (p.getLocation().distance(this.target.getLocation()) >= 5.0) {
                p.closeInventory();
            }
            contents.set(0, 0, ClickableItem.empty(new ItemBuilder(Material.ENCHANTED_BOOK).setName("§eNeeds").setLoreLine("", 0).setLoreLine("§7WC§f " + pd.getWC(), 1).setLoreLine("§7Hygiene§f " + pd.getHygiene(), 2).setLoreLine("§7Sleep§f " + pd.getSleep(), 3).setLoreLine("§7Thirst§f " + pd.getThirst(), 4).setLoreLine("§7Food§f " + this.target.getFoodLevel(), 5).setLoreLine("§7Health§f " + this.target.getHealthScale(), 6).setLoreLine("", 7).toItemStack()));
            contents.set(0, 1, ClickableItem.empty(new ItemBuilder(Material.ENCHANTED_BOOK).setName("§eStatistics").setLoreLine("", 0).setLoreLine("§7Respect§f " + pd.getRespect(), 1).setLoreLine("§7Stamina§f " + pd.getStamina(), 2).setLoreLine("§7Strength§f " + pd.getStrength(), 3).setLoreLine("§7Strength xp§f " + pd.getStrengthExp(), 4).setLoreLine("§7Intellect§f " + pd.getIntellect(), 5).setLoreLine("", 6).toItemStack()));
            contents.set(0, 2, ClickableItem.empty(new ItemBuilder(Material.ENCHANTED_BOOK).setName("§eInformation").setLoreLine("", 0).setLoreLine("§7Points§f " + pd.getPoints(), 1).setLoreLine("§7Coins§f " + pd.getCoins(), 2).setLoreLine("§7OP§f " + PlayerGUI.this.getState(this.target.isOp()), 3).setLoreLine("§7Type§f " + pd.getType().getName(), 4).setLoreLine("§7Gamemode§f " + Functions.upperCaseWords(this.target.getGameMode().toString()), 5).setLoreLine("§7Premium§f " + pd.getPremium().getType(), 6).setLoreLine("§7Premium ends in§f " + pd.getHowLongPremium(), 7).setLoreLine("§7ChatColor §" + pd.getChatColor() + "COLOR", 8).setLoreLine("§7Money §f" + pd.getMoney(), 9).setLoreLine("§7Level §f" + pd.getLVL(), 10).setLoreLine("§7XP §f" + pd.getXP(), 11).setLoreLine("§7Aggresive §f" + PlayerGUI.this.getState(pd.getAggresive()), 12).setLoreLine("§7Luck §f" + pd.getLuck(), 13).setLoreLine("§7Language §f" + pd.getLang().getEnName(), 14).setLoreLine("", 15).toItemStack()));
            contents.set(0, 3, ClickableItem.empty(new ItemBuilder(Material.ENCHANTED_BOOK).setName("§eInformation").setLoreLine("", 0).setLoreLine("§7Health§f " + this.target.getHealth(), 1).setLoreLine("§7Food Level§f " + this.target.getFoodLevel(), 2).setLoreLine("§7Time since death§f " + Functions.getTime(this.target.getStatistic(Statistic.TIME_SINCE_DEATH) / 20), 3).setLoreLine("§7Jumps§f " + this.target.getStatistic(Statistic.JUMP), 4).setLoreLine("§7Player kills§f " + this.target.getStatistic(Statistic.PLAYER_KILLS), 5).setLoreLine("§7Deaths§f " + this.target.getStatistic(Statistic.DEATHS), 6).setLoreLine("§7Damage dealt§f " + this.target.getStatistic(Statistic.DAMAGE_DEALT), 7).setLoreLine("§7First join§f " + Functions.timestampToDate(this.target.getFirstPlayed()), 8).setLoreLine("§7Played time§f " + Functions.getTime(this.target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20), 9).setLoreLine("", 10).toItemStack()));
            ItemStack[] armorContents;
            for (int length = (armorContents = this.target.getInventory().getArmorContents()).length, i = 0; i < length; ++i) {
                final ItemStack is = armorContents[i];
                contents.add(ClickableItem.empty(is));
            }
            final Iterator iterator = this.target.getInventory().iterator();
            while (iterator.hasNext()) {
                ItemStack is = (ItemStack) iterator.next();
                contents.add(ClickableItem.empty(is));
            }
        }
    }
}
