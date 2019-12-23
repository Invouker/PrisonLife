// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import org.bukkit.Color;
import Items.ItemBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import Main.Chat;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.player.PlayerInteractEvent;
import Main.Main;
import org.bukkit.event.Listener;

public class JoinWashing implements Listener
{
    Main main;
    Washing wash;
    
    public JoinWashing() {
        this.main = Main.getInstance();
        this.wash = new Washing();
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (b != null && b.getType() == Material.OAK_WALL_SIGN) {
                final Sign s = (Sign)b.getState();
                final String[] lines = s.getLines();
                final World w = Bukkit.getWorld(Main.mainWorld);
                if (lines[0].contains("[Jobs]") && lines[1].contains("Washer")) {
                    if (lines[2].contains("Join")) {
                        Chat.info(p, "Join to washer");
                        this.wash.addPlayer(p);
                        final Location location = new Location(w, -455.5, 77.0, 179.5);
                        location.setYaw(-90.0f);
                        p.teleport(location);
                        this.setJobArmour(p);
                        p.getInventory().setArmorContents((ItemStack[])null);
                    }
                    else if (lines[2].contains("Leave")) {
                        final Location location = new Location(w, -459.5, 77.0, 179.5);
                        location.setYaw(90.0f);
                        Chat.info(p, "Leave a washer!");
                        p.teleport(location);
                        this.wash.removePlayer(p);
                    }
                }
            }
        }
    }
    
    public void setJobArmour(final Player p) {
        final ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET, 1).setName("§eJob Helmet Armour").setLeatherArmorColor(Color.WHITE).toItemStack();
        final ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setName("§eJob ChestPlate Armour").setLeatherArmorColor(Color.WHITE).toItemStack();
        final ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS, 1).setName("§eJob Leggings Armour").setLeatherArmorColor(Color.WHITE).toItemStack();
        final ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS, 1).setName("§eJob Boots Armour").setLeatherArmorColor(Color.WHITE).toItemStack();
        final ItemStack[] armour = { boots, leggings, chestplate, helmet };
        p.getInventory().setArmorContents(armour);
    }
}
