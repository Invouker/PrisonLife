// 
// Decompiled by Procyon v0.5.36
// 

package Cells;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.World;
import Main.Chat;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import Main.ConfigListener;

public class PrisonChestHandler
{
    static CellDataProvider cd;
    
    static {
        PrisonChestHandler.cd = new CellDataProvider();
    }
    
    public static void onLoad(final ConfigListener cfg, final int cell) {
        final int x = PrisonChestHandler.cd.getICell(cell, "Chest.X");
        final int y = PrisonChestHandler.cd.getICell(cell, "Chest.Y");
        final int z = PrisonChestHandler.cd.getICell(cell, "Chest.Z");
        final String world = PrisonChestHandler.cd.getSCell(cell, "Chest.World");
        final World w = Bukkit.getWorld(world);
        final Location loc = new Location(w, (double)x, (double)y, (double)z);
        final Block b = w.getBlockAt(loc);
        final Chest chest = (Chest)b.getState();
        if (b.getType().equals((Object)Material.CHEST) || b.getType().equals((Object)Material.TRAPPED_CHEST)) {
            for (int i = 0; i <= 26; ++i) {
                chest.getInventory().clear(i);
                final ItemStack is = cfg.getItemStack("Cells." + cell + ".Chest.Inventory." + i);
                chest.getInventory().setItem(i, is);
                Chat.print("loaded §f§l" + is);
            }
        }
    }
    
    public static void onSave(final ConfigListener cfg, final int cell) {
        final int x = PrisonChestHandler.cd.getICell(cell, "Chest.X");
        final int y = PrisonChestHandler.cd.getICell(cell, "Chest.Y");
        final int z = PrisonChestHandler.cd.getICell(cell, "Chest.Z");
        final String world = PrisonChestHandler.cd.getSCell(cell, "Chest.World");
        final World w = Bukkit.getWorld(world);
        final Location loc = new Location(w, (double)x, (double)y, (double)z);
        final Block b = w.getBlockAt(loc);
        if (b.getType().equals((Object)Material.CHEST) || b.getType().equals((Object)Material.TRAPPED_CHEST)) {
            Chat.print(" LOC: " + loc);
            final Chest chest = (Chest)b.getState();
            final Inventory inv = chest.getBlockInventory();
            for (int i = 0; i <= 26; ++i) {
                if (cfg.isConfigurationSection("Cells." + cell + ".Chest.Inventory." + i)) {
                    cfg.set("Cells." + cell + ".Chest.Inventory." + i, (Object)null);
                }
                if (inv.getItem(i) == null) {
                    ++i;
                }
                else if (!inv.getItem(i).getType().equals((Object)Material.AIR)) {
                    cfg.set("Cells." + cell + ".Chest.Inventory." + i, (Object)inv.getItem(i));
                    Chat.print("ITEM");
                }
                else {
                    cfg.set("Cells." + cell + ".Chest.Inventory." + i, (Object)new ItemStack(Material.AIR, 1));
                    Chat.print("Null");
                }
                cfg.save();
            }
        }
    }
}
