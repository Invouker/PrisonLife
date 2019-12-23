// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Items.ItemBuilder;
import Items.ItemRarity;
import Items.RecipeBuilder;
import Items.Enchantments.BoxingGloves;
import Main.ConfigListener;
import Main.Main;

public class CraftingListener implements Listener
{
    BoxingGloves bg;
    public ItemStack bgItem;
    
    public CraftingListener() {
        this.bg = new BoxingGloves(new NamespacedKey((Plugin)Main.getInstance(), "BoxingGloves"));
        this.bgItem = new ItemBuilder(Material.SHEARS).setName("§b§l" + this.bg.getName()).setUnbreakable(true).setLoreLine("&fabxd", 1).setLoreLine("&fabcd1", 2).addUnsafeEnchantment(this.bg, 1).setRare(ItemRarity.BASIC).addFlag(ItemFlag.HIDE_ATTRIBUTES).setDamage(0.9f, 1.4f).toItemStack();
    }
    
    public void createRecipes() {
        Bukkit.clearRecipes();
        new RecipeBuilder(this.bgItem).setShape("LL ", "LWL", " SS").setIngredient('L', Material.LEATHER).setIngredient('W', Material.WHITE_WOOL).setIngredient('S', Material.LEAD).build();
        new RecipeBuilder(this.bgItem).setShape("SSS", "S S", "SSS").setIngredient('S', Material.LEAD).build();
    }
    
    @EventHandler
    public void on(final PrepareItemCraftEvent e) {
        Player p = null;
        if (e.getView().getPlayer() instanceof Player) {
            p = (Player)e.getView().getPlayer();
        }
        if (p == null) {
            return;
        }
        try {
            if (e.getRecipe().getResult() != null && e.getRecipe().getResult().equals((Object)this.bgItem) && !this.hasResearched(p, this.bg.getName())) {
                e.getInventory().setResult(new ItemBuilder(Material.AIR).toItemStack());
            }
        }
        catch (NullPointerException ex) {}
    }
    
    private String getPath(final Player p, final String name) {
        return "Researches." + name;
    }
    
    public boolean hasResearched(final Player p, final String name) {
        final ConfigListener c = new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml");
        return c.getBoolean(this.getPath(p, name));
    }
    
    public void setResearched(final Player p, final String name, final boolean data) {
        final ConfigListener c = new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml");
        c.set(this.getPath(p, name), (Object)data);
        c.save();
    }
}
