// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import org.bukkit.event.EventHandler;
import Main.Chat;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class WearJobArmour implements Listener
{
    Washing wash;
    
    public WearJobArmour() {
        this.wash = new Washing();
    }
    
    @EventHandler
    public void on(final InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            final Player p = (Player)e.getWhoClicked();
            if (e.getRawSlot() > 0 && e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER && e.getSlotType() == InventoryType.SlotType.ARMOR && this.wash.isWasher(p)) {
                e.setCancelled(true);
                Chat.info(p, "Na pracovisku mus\u00ed\u0161 ma\u0165 toto oble\u010denie!");
            }
        }
    }
}
