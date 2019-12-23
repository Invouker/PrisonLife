// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import Items.ItemBuilder;
import Main.Chat;
import Utils.Functions;

public class SolitarySignController implements Listener
{
    SolitaryData sd;
    SolitaryControl soli;
    
    public SolitarySignController() {
        this.sd = new SolitaryData();
        this.soli = new SolitaryControl();
    }
    
    @EventHandler
    public void onSign(final SignChangeEvent e) {
        final Player p = e.getPlayer();
        final Block sign = e.getBlock();
        final Sign s = (Sign)sign.getState();
        if ((s.getType().equals((Object)Material.OAK_SIGN) || s.getType().equals((Object)Material.OAK_WALL_SIGN)) && e.getLine(0).equalsIgnoreCase("%solitary%")) {
            final String strInt = e.getLine(1);
            if (Functions.isNum(strInt)) {
                final int solitary = Integer.parseInt(strInt);
                solitaryCache.setI(solitary, e.getBlock().getX(), solitaryCache.sign_X);
                solitaryCache.setI(solitary, e.getBlock().getY(), solitaryCache.sign_Y);
                solitaryCache.setI(solitary, e.getBlock().getZ(), solitaryCache.sign_Z);
                solitaryCache.setS(solitary, e.getBlock().getWorld().getName(), solitaryCache.world);
                Chat.info(p, "Creating a Solitary Room ! ID: " + solitary);
                this.sd.createSolitary(solitary, e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName(), "NONE", "NONE");
                this.soli.updateSign(solitary);
            }
            else {
                Chat.getT("noNum", p, new String[0]);
            }
        }
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            final BlockState state = b.getState();
            if (state instanceof Sign) {
                final int x = b.getX();
                final int y = b.getY();
                final int z = b.getZ();
                int i = 1;
                while (i < 10) {
                    final int signX = solitaryCache.getI(i, solitaryCache.sign_X);
                    final int signY = solitaryCache.getI(i, solitaryCache.sign_Y);
                    final int signZ = solitaryCache.getI(i, solitaryCache.sign_Z);
                    if (x == signX && y == signY && z == signZ) {
                        final int solitary = i;
                        final String name = solitaryCache.getS(solitary, solitaryCache.name);
                        if (name == null || name.equalsIgnoreCase("none")) {
                            Chat.send(p, "notConnected", new String[0]);
                            return;
                        }
                        final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9, "§8Solitary Info about " + name + "!");
                        inv.setItem(4, new ItemBuilder(Material.ANVIL).setName("Player Info").toItemStack());
                        inv.setItem(6, new ItemBuilder(Material.ARROW).setName("hehe").setLore("§ftest", "§flul").toItemStack());
                        p.openInventory(inv);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
    }
}
