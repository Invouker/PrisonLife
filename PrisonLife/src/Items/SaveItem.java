// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Main.Chat;
import Main.ConfigListener;
import de.tr7zw.itemnbtapi.NBTItem;

public class SaveItem
{
    String name;
    int durability;
    int amount;
    List<String> lore;
    Material type;
    Map<Enchantment, Integer> ench;
    ArrayList<String> enchantments;
    ConfigListener cfg;
    String pathTo;
    Inventory inventory;
    
    public SaveItem(final ConfigListener cl, final String pathTo, final Inventory inv) {
        this.name = null;
        this.durability = 0;
        this.amount = 1;
        this.lore = new ArrayList<String>();
        this.type = Material.AIR;
        this.ench = new HashMap<Enchantment, Integer>();
        this.enchantments = new ArrayList<String>();
        this.pathTo = null;
        this.inventory = inv;
        this.pathTo = pathTo;
        this.cfg = cl;
        for (int i = 0; i < inv.getSize(); ++i) {
            final String path = String.valueOf(pathTo) + "." + i + ".";
            final ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                cl.set(String.valueOf(path) + "type", (Object)Material.AIR.toString());
                cl.set(String.valueOf(path) + "amount", (Object)1);
            }
            else {
                this.type = item.getType();
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName()) {
                        this.name = item.getItemMeta().getDisplayName();
                    }
                    if (item.getItemMeta().hasLore()) {
                        this.lore = (List<String>)item.getItemMeta().getLore();
                    }
                    if (item.getItemMeta().hasEnchants()) {
                        this.ench = (Map<Enchantment, Integer>)item.getEnchantments();
                    }
                }
                if (item.getItemMeta().hasEnchants()) {
                    for (final Map.Entry<Enchantment, Integer> entry : this.ench.entrySet()) {
                        final Enchantment ench = entry.getKey();
                        final int level = entry.getValue();
                        final String sEnch = String.valueOf(ench.getName()) + ":" + String.valueOf(level);
                        this.enchantments.add(sEnch);
                    }
                }
                this.amount = item.getAmount();
                if (item.getItemMeta().hasDisplayName()) {
                    cl.set(String.valueOf(path) + "name", (Object)this.name);
                }
                else {
                    cl.set(String.valueOf(path) + "name", (Object)null);
                }
                if (this.durability >= 1) {
                    cl.set(String.valueOf(path) + "durability", (Object)this.durability);
                }
                else {
                    cl.set(String.valueOf(path) + "durability", (Object)null);
                }
                cl.set(String.valueOf(path) + "amount", (Object)this.amount);
                if (item.getItemMeta().hasLore()) {
                    cl.set(String.valueOf(path) + "lore", (Object)this.lore);
                }
                else {
                    cl.set(String.valueOf(path) + "lore", (Object)null);
                }
                cl.set(String.valueOf(path) + "type", (Object)this.type.toString());
                if (item.getItemMeta().hasEnchants()) {
                    cl.set(String.valueOf(path) + "enchantments", (Object)this.enchantments);
                }
                else {
                    cl.set(String.valueOf(path) + "enchantments", (Object)null);
                }
                final NBTItem nbt = new NBTItem(item);
                final ArrayList<String> nbts = new ArrayList<String>();
                for (final String s : nbt.getKeys()) {
                    if (nbt.getType(s).toString().toLowerCase().contains("string")) {
                        nbts.add("STRING:" + s + ":" + nbt.getString(s));
                    }
                    if (nbt.getType(s).toString().toLowerCase().contains("int")) {
                        nbts.add("INTEGER:" + s + ":" + nbt.getInteger(s));
                    }
                    if (nbt.getType(s).toString().toLowerCase().contains("float")) {
                        nbts.add("FLOAT:" + s + ":" + nbt.getInteger(s));
                    }
                }
                this.cfg.set(String.valueOf(path) + "NBT", (Object)nbts);
            }
        }
    }
    
    public void save() {
        try {
            this.cfg.save();
            Chat.print("§C DATA ULO\u017dEN\u00c9");
        }
        catch (NullPointerException ex) {
            Chat.print("§cNotChange(s) - WARN");
            Chat.print(Chat.getException(1, 87, this.getClass()));
            this.setAllAir(this.cfg, this.pathTo, this.inventory);
        }
        this.clear();
    }
    
    public void setAllAir(final ConfigListener cl, final String pathTo, final Inventory inv) {
        for (int i = 0; i < inv.getSize(); ++i) {
            final String path = String.valueOf(pathTo) + "." + i + ".";
            this.cfg.set(String.valueOf(path) + "name", (Object)null);
            this.cfg.set(String.valueOf(path) + "durability", (Object)null);
            this.cfg.set(String.valueOf(path) + "amount", (Object)1);
            this.cfg.set(String.valueOf(path) + "lore", (Object)null);
            this.cfg.set(String.valueOf(path) + "type", (Object)Material.AIR.toString());
            this.cfg.set(String.valueOf(path) + "enchantments", (Object)null);
        }
    }
    
    public void clear() {
        this.name = null;
        this.durability = 0;
        this.amount = 0;
        this.lore = null;
        this.type = null;
        this.ench = null;
        this.enchantments = null;
    }
}
