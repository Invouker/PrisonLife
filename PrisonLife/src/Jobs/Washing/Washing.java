// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import java.util.HashMap;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Washing
{
    private static ArrayList<Player> washer;
    private static HashMap<Block, WashingMachineSaved> washingMachine;
    
    static {
        Washing.washer = new ArrayList<Player>();
        Washing.washingMachine = new HashMap<Block, WashingMachineSaved>();
    }
    
    public ArrayList<Player> getWashers() {
        return Washing.washer;
    }
    
    public void addPlayer(final Player p) {
        if (!this.isWasher(p)) {
            Washing.washer.add(p);
        }
    }
    
    public void removePlayer(final Player p) {
        if (this.isWasher(p)) {
            Washing.washer.remove(p);
        }
    }
    
    public boolean isWasher(final Player p) {
        return !Washing.washer.isEmpty() || Washing.washer.contains(p);
    }
    
    public static HashMap<Block, WashingMachineSaved> getWashingMachine() {
        return Washing.washingMachine;
    }
    
    public static void addWashingMachine(final Player p, final Block b, final ItemStack is) {
        if (!Washing.washingMachine.containsKey(b)) {
            Washing.washingMachine.put(b, new WashingMachineSaved(p, is));
        }
    }
    
    public static void setWashingMachineTime(final Player p, final Block b, final int time) {
        if (Washing.washingMachine.containsKey(b)) {
            final WashingMachineSaved wMachine = Washing.washingMachine.get(b);
            wMachine.setTime(time);
        }
    }
    
    public static int getWashingMachineTime(final Player p, final Block b) {
        if (Washing.washingMachine.containsKey(b)) {
            final WashingMachineSaved wMachine = Washing.washingMachine.get(b);
            return wMachine.getTime();
        }
        return -1;
    }
    
    public static void removeWashingMachine(final Block b) {
        if (Washing.washingMachine.containsKey(b)) {
            Washing.washingMachine.remove(b);
        }
    }
}
