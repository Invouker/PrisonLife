// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Gui.BlueprintGUI;
import Items.ItemBuilder;
import Items.Enchantments.BoxingGloves;
import Items.Enchantments.Enchantments;
import Main.Chat;
import Main.Main;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;
import Utils.Functions;

public class BlueprintInventory implements Listener
{
    Main main;
    public static int chanceBoost;
    PlayerDataHandler pdh;
    BoxingGloves bg;
    
    static {
        BlueprintInventory.chanceBoost = 0;
    }
    
    public BlueprintInventory() {
        this.main = Main.getInstance();
        this.pdh = new PlayerDataHandler();
        this.bg = new BoxingGloves(new NamespacedKey((Plugin)Main.getInstance(), "BoxingGloves"));
    }
    
    public Inventory createblueprintInventory(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 36, Chat.getT("blueprint.title", p, new String[0]));
        final int bg_chance = 100 - (Enchantments.BOXING_GLOVES.getChance() - BlueprintInventory.chanceBoost - this.getMinimumChance(p));
        inv.setItem(1, new ItemBuilder(Material.PAPER, 1).setName("&bBlueprint - Boxing Gloves").setLoreLine("§8Drop chance §f" + bg_chance + "§7%", 1).setLoreLine("§8Cost §f" + this.bg.getCost() + "§7 levels", 2).addUnsafeEnchantment(this.bg, 1).toItemStack());
        inv.setItem(22, new ItemBuilder(Material.OAK_SIGN, 1).setName(Chat.getT("blueprint.informationName", p, new String[0])).setLore(Chat.getTList("blueprint.information", p, new String[0])).toItemStack());
        return inv;
    }
    
    public void createItem(final Player p, final ItemStack is, final int cost, final Enchantment ench) {
        if (is == null || !is.hasItemMeta()) {
            return;
        }
        final int numb = Functions.random(1, 100);
        if (is.getItemMeta().hasEnchants() && is.containsEnchantment(ench)) {
            if (p.getLevel() >= cost || this.pdh.getData(p).getType() == PrisonType.ADMIN || p.getGameMode() == GameMode.CREATIVE) {
                if (this.pdh.getData(p).getType() != PrisonType.ADMIN || p.getGameMode() != GameMode.CREATIVE) {
                    p.setLevel(p.getLevel() - cost);
                }
                if (numb >= Enchantments.BOXING_GLOVES.getChance() - BlueprintInventory.chanceBoost - this.getMinimumChance(p) || this.pdh.getData(p).getType() == PrisonType.ADMIN || p.getGameMode() == GameMode.CREATIVE) {
                    p.getInventory().addItem(new ItemStack[] { is });
                    Chat.send(p, "blueprint.buyBlueprint", ench.getName(), String.valueOf(cost));
                    p.closeInventory();
                }
                else {
                    Chat.send(p, "blueprint.noChance", new String[0]);
                }
            }
            else {
                Chat.send(p, "blueprint.noLevel", new String[0]);
            }
        }
    }
    
    public int getMinimumChance(final Player p) {
        int intelligence = this.pdh.getData(p).getIntellect();
        if (intelligence == 0) {
            intelligence = 1;
        }
        int minChance = 1 * intelligence;
        if (minChance >= 100) {
            minChance = 99;
        }
        if (minChance <= 0) {
            minChance = 1;
        }
        if (BlueprintInventory.chanceBoost > 1) {
            minChance += BlueprintInventory.chanceBoost;
        }
        return minChance;
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR && e.getItem() != null && e.getItem().getType() == Material.CHEST) {
            final BlueprintGUI bGui = new BlueprintGUI();
            bGui.getInventory(e.getPlayer()).open(e.getPlayer());
        }
    }
}
