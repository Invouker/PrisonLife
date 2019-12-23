// 
// Decompiled by Procyon v0.5.36
// 

package Gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Items.ItemBuilder;
import Items.Enchantments.BoxingGloves;
import Items.Enchantments.Enchantments;
import Main.Chat;
import Main.Main;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;
import Utils.Functions;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.SmartInvsPlugin;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.opener.InventoryOpener;

public class BlueprintGUI implements InventoryOpener
{
    Main main;
    public static int chanceBoost;
    PlayerDataHandler pdh;
    BoxingGloves bg;
    
    static {
        BlueprintGUI.chanceBoost = 0;
    }
    
    public BlueprintGUI() {
        this.main = Main.getInstance();
        this.pdh = new PlayerDataHandler();
        this.bg = new BoxingGloves(new NamespacedKey((Plugin)Main.getInstance(), "BoxingGloves"));
    }
    
    public SmartInventory getInventory(final Player p) {
        return SmartInventory.builder().id("blueprintGui").provider((InventoryProvider)new blueprintGui()).size(4, 9).title(ChatColor.DARK_GRAY + Chat.getT("blueprint.title", p, new String[0])).closeable(true).build();
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
        if (BlueprintGUI.chanceBoost > 1) {
            minChance += BlueprintGUI.chanceBoost;
        }
        return minChance;
    }
    
    public static boolean hasEnchant(final ItemStack item, final String name) {
        for (final Enchantment ench : item.getEnchantments().keySet()) {
            if (ench.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private class blueprintGui implements InventoryProvider
    {
        public void init(final Player p, final InventoryContents contents) {
            final int bg_chance = 100 - (Enchantments.BOXING_GLOVES.getChance() - BlueprintGUI.chanceBoost - BlueprintGUI.this.getMinimumChance(p));
            contents.add(ClickableItem.of(new ItemBuilder(Material.PAPER, 1).setName("&bBlueprint - Boxing Gloves").setLoreLine("§8Drop chance §f" + bg_chance + "§7%", 1).setLoreLine("§8Cost §f" + BlueprintGUI.this.bg.getCost() + "§7 levels", 2).addUnsafeEnchantment(BlueprintGUI.this.bg, 1).toItemStack(), e -> this.createItem(p, e.getCurrentItem(), BlueprintGUI.this.bg.getCost(), BlueprintGUI.this.bg)));
            contents.add(ClickableItem.of(new ItemBuilder(Material.OAK_SIGN, 1).setName(Chat.getT("blueprint.informationName", p, new String[0])).setLore(Chat.getTList("blueprint.information", p, new String[0])).toItemStack(), e -> {}));
        }
        
        public void update(final Player p, final InventoryContents contents) {
        }
        
        public void createItem(final Player p, final ItemStack is, final int cost, final Enchantment ench) {
            final int numb = Functions.random(1, 100);
            if (is.getItemMeta().hasEnchants() && BlueprintGUI.hasEnchant(is, ench.getName())) {
                if (p.getLevel() >= cost || BlueprintGUI.this.pdh.getData(p).getType() == PrisonType.ADMIN || p.getGameMode() == GameMode.CREATIVE) {
                    if (BlueprintGUI.this.pdh.getData(p).getType() != PrisonType.ADMIN || p.getGameMode() != GameMode.CREATIVE) {
                        p.setLevel(p.getLevel() - cost);
                    }
                    if (numb >= Enchantments.BOXING_GLOVES.getChance() - BlueprintGUI.chanceBoost - BlueprintGUI.this.getMinimumChance(p) || BlueprintGUI.this.pdh.getData(p).getType() == PrisonType.ADMIN || p.getGameMode() == GameMode.CREATIVE) {
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
    }
}
