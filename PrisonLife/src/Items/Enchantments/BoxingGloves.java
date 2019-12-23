// 
// Decompiled by Procyon v0.5.36
// 

package Items.Enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import Main.Chat;
import Listeners.CraftingListener;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.enchantments.Enchantment;

public class BoxingGloves extends Enchantment implements Listener
{
    public BoxingGloves(final NamespacedKey key) {
        super(key);
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if ((a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) && e.getItem() != null) {
            final ItemStack is = e.getItem();
            if (is.getType() == Material.PAPER && is.containsEnchantment((Enchantment)this)) {
                final CraftingListener cl = new CraftingListener();
                if (!cl.hasResearched(p, this.getName())) {
                    Chat.send(p, "succesfullyResearched", is.getItemMeta().getDisplayName());
                    Chat.print("Player succesfully researched a " + this.getName());
                    cl.setResearched(p, this.getName(), true);
                    is.setAmount(is.getAmount() - 1);
                }
                else {
                    Chat.send(p, "hadResearched", is.getItemMeta().getDisplayName());
                }
            }
        }
    }
    
    public int getCost() {
        return 27;
    }
    
    public boolean canEnchantItem(final ItemStack is) {
        return is.getType().equals((Object)Material.PAPER);
    }
    
    public boolean conflictsWith(final Enchantment ench) {
        return false;
    }
    
    public EnchantmentTarget getItemTarget() {
        return null;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public String getName() {
        return "Boxing Gloves";
    }
    
    public int getStartLevel() {
        return 1;
    }
    
    public boolean isCursed() {
        return false;
    }
    
    public boolean isTreasure() {
        return false;
    }
}
