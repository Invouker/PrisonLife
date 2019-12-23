// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.ConfigListener;
import de.tr7zw.itemnbtapi.NBTItem;

public class LoadItem
{
    private ArrayList<ItemStack> item;
    
    public LoadItem(final ConfigListener f, final String pathTo) {
        this.item = new ArrayList<ItemStack>();
        if (f.getConfigurationSection(pathTo) == null) {
            return;
        }
        for (final String s : f.getConfigurationSection(pathTo).getKeys(false)) {
            final String path = String.valueOf(pathTo) + "." + s + ".";
            final String material = f.getString(String.valueOf(path) + "type");
            if (material == null) {
                continue;
            }
            final Material m = Material.getMaterial(material.toUpperCase());
            if (m == null) {
                continue;
            }
            if (m.equals((Object)Material.AIR)) {
                this.item.add(new ItemStack(Material.AIR, 1));
            }
            else {
                final int amount = f.getInt(String.valueOf(path) + "amount");
                final String name = f.getString(String.valueOf(path) + "name");
                final List<String> lore = (List<String>)f.getStringList(String.valueOf(path) + "lore");
                final List<String> ench = (List<String>)f.getStringList(String.valueOf(path) + "enchantments");
                final ItemStack is = new ItemStack(m, amount);
                final ItemMeta im = is.getItemMeta();
                if (name != null) {
                    im.setDisplayName(name);
                }
                if (!lore.isEmpty()) {
                    im.setLore((List)lore);
                }
                if (!ench.isEmpty()) {
                    for (final String e : ench) {
                        if (e != null) {
                            final String[] enchant = e.split(":");
                            final Enchantment enchantmentID = Enchantment.getByName(enchant[0]);
                            if (enchantmentID == null) {
                                continue;
                            }
                            im.addEnchant(enchantmentID, Integer.parseInt(enchant[1]), false);
                        }
                    }
                }
                is.setItemMeta(im);
                final NBTItem nbt = new NBTItem(is);
                final List<String> nbts = (List<String>)f.getStringList(String.valueOf(path) + "NBT");
                for (final String np : nbts) {
                    if (np.toUpperCase().contains("STRING")) {
                        final String[] c = np.split(":");
                        nbt.setString(c[1], c[2]);
                    }
                    if (np.toUpperCase().contains("INTEGER")) {
                        final String[] c = np.split(":");
                        nbt.setInteger(c[1], Integer.valueOf(Integer.parseInt(c[2])));
                    }
                    if (np.toUpperCase().contains("FLOAT")) {
                        final String[] c = np.split(":");
                        nbt.setFloat(c[1], Float.valueOf(Float.parseFloat(c[2])));
                    }
                }
                this.item.add(nbt.getItem());
            }
        }
    }
    
    public ArrayList<ItemStack> getItems() {
        return this.item;
    }
    
    public ItemStack[] getItemsPole() {
        final ItemStack[] itemy = new ItemStack[41];
        for (int i = 0; i < this.item.size(); ++i) {
            itemy[i] = this.item.get(i);
        }
        return itemy;
    }
    
    public void clearCache() {
        this.item.clear();
    }
}
