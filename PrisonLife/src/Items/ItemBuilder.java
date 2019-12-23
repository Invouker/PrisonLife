// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder
{
    private ItemStack is;
    
    public ItemBuilder(final Material m) {
        this(m, 1);
    }
    
    public ItemBuilder(final ItemStack is) {
        this.is = is;
    }
    
    public ItemBuilder setAmount(final int amount) {
        this.is.setAmount(amount);
        return this;
    }
    
    public ItemBuilder(final Material m, final int amount) {
        this.is = new ItemStack(m, amount);
    }
    
    public ItemBuilder clone() {
        return new ItemBuilder(this.is);
    }
    
    public ItemBuilder setName(final String name) {
        final ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name.replaceAll("&", "§"));
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addUnsafeEnchantment(final Enchantment ench, final int level) {
        this.is.addUnsafeEnchantment(ench, level);
        return this;
    }
    
    public ItemBuilder removeEnchantment(final Enchantment ench) {
        this.is.removeEnchantment(ench);
        return this;
    }
    
    public ItemBuilder setSkullOwner(final String owner) {
        try {
            final SkullMeta im = (SkullMeta)this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta((ItemMeta)im);
        }
        catch (ClassCastException ex) {}
        return this;
    }
    
    public ItemBuilder addEnchant(final Enchantment ench, final int level) {
        final ItemMeta im = this.is.getItemMeta();
        im.addEnchant(ench, level, true);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments) {
        this.is.addEnchantments((Map)enchantments);
        return this;
    }
    
    public ItemBuilder setUnbreakable(final boolean unbreak) {
        final ItemMeta im = this.is.getItemMeta();
        im.setUnbreakable(unbreak);
        return this;
    }
    
    public ItemBuilder setLore(final String... lore) {
        final ItemMeta im = this.is.getItemMeta();
        im.setLore((List)Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta im = this.is.getItemMeta();
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addFlag(final ItemFlag flag) {
        final ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(new ItemFlag[] { flag });
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder removeLoreLine(final String line) {
        final ItemMeta im = this.is.getItemMeta();
        final List<String> lore = new ArrayList<String>(im.getLore());
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder removeLoreLine(final int index) {
        final ItemMeta im = this.is.getItemMeta();
        final List<String> lore = new ArrayList<String>(im.getLore());
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index - 1);
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder addLoreLine(final String line) {
        final ItemMeta im = this.is.getItemMeta();
        List<String> lore = new ArrayList<String>();
        if (im.hasLore()) {
            lore = new ArrayList<String>(im.getLore());
        }
        lore.add(line);
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setLoreLine(final String text, final int pos) {
        final ItemMeta im = this.is.getItemMeta();
        if (im.getLore() == null) {
            im.setLore((List)Arrays.asList(""));
        }
        final List<String> lore = new ArrayList<String>(im.getLore());
        for (int i = 0; i <= pos + 1 && lore.size() <= pos; ++i) {
            lore.add("");
        }
        lore.set(pos, "§f" + text.replaceAll("&", "§"));
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setDyeColor(final DyeColor color) {
        this.is.setDurability((short)color.getDyeData());
        return this;
    }
    
    public ItemBuilder setRare(final ItemRarity rare) {
        final NBTBuilder nbtb = new NBTBuilder(this.is);
        final int rareNumber = rare.getOrder(rare);
        nbtb.setInteger("rare", rareNumber);
        this.setLoreLine("§8Rarity " + ItemRarity.BASIC.getColor(rare) + "§l" + rare.toString(), 5);
        this.addFlag(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }
    
    public ItemBuilder setRare(final ItemRarity rare, final int pos) {
        final NBTBuilder nbtb = new NBTBuilder(this.is);
        final int rareNumber = rare.getOrder(rare);
        nbtb.setInteger("rare", rareNumber);
        this.setLoreLine("§8Rarity " + ItemRarity.BASIC.getColor(rare) + "§l" + rare.toString(), pos);
        this.addFlag(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }
    
    public ItemBuilder removeLore() {
        final ItemMeta im = this.is.getItemMeta();
        if (im != null) {
            im.setLore((List)null);
        }
        this.is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setDamage(final float minDamage, final float maxDamage) {
        final NBTBuilder nbtb = new NBTBuilder(this.is);
        nbtb.setFloat("minDamage", minDamage);
        nbtb.setFloat("maxDamage", maxDamage);
        this.setLoreLine("§8Damage §f§l" + String.valueOf(minDamage) + " §7- §f§l" + String.valueOf(maxDamage), 6);
        return this;
    }
    
    public ItemBuilder setLeatherArmorColor(final Color color) {
        try {
            final LeatherArmorMeta im = (LeatherArmorMeta)this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta((ItemMeta)im);
        }
        catch (ClassCastException ex) {}
        return this;
    }
    
    public ItemStack toItemStack() {
        return this.is;
    }
}
