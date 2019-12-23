// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WashingMachineSaved
{
    private ItemStack is;
    private Player p;
    private int time;
    
    public WashingMachineSaved(final Player p, final ItemStack is) {
        this.p = p;
        this.is = is;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    public ItemStack getItem() {
        return this.is;
    }
    
    public void setTime(final int time) {
        this.time = time;
    }
    
    public int getTime() {
        return this.time;
    }
}
