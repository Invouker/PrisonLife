// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Farmer;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import Items.ItemBuilder;
import Main.Chat;
import Main.Main;

public class PlaceCrops implements Listener
{
    @EventHandler
    public void on(final BlockBreakEvent e) {
        Chat.print(e.getBlock().getType().toString());
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            final BlockData data = b.getState().getBlockData();
            if (data instanceof Ageable) {
                final Ageable ag = (Ageable)data;
                if (ag.getAge() == ag.getMaximumAge()) {
                    ag.setAge(0);
                    b.setBlockData((BlockData)ag);
                    final Random rand = new Random();
                    Material mat = Material.WHEAT;
                    if (b.getType().toString().equalsIgnoreCase("potatoes")) {
                        mat = Material.POTATO;
                    }
                    else if (b.getType().toString().equalsIgnoreCase("carrots")) {
                        mat = Material.CARROT;
                    }
                    else if (b.getType().toString().equalsIgnoreCase("beetroots")) {
                        mat = Material.BEETROOT;
                    }
                    else if (b.getType().toString().equalsIgnoreCase("sweet_berry_bush")) {
                        mat = Material.SWEET_BERRIES;
                    }
                    if (Main.debug) {
                        Chat.print("Crops right click event " + b.getType());
                    }
                    b.getLocation().getWorld().dropItem(b.getLocation().add(0.5, 0.0, 0.5), new ItemBuilder(mat).setAmount(rand.nextInt(2) + 1).toItemStack());
                    b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 1.0f, 1.0f);
                }
            }
        }
    }
}
