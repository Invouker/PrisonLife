// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle.Listener;

import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import Main.Chat;
import DayLightCycle.Gate;
import DayLightCycle.DoorControler;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.Listener;

public class GateListener implements Listener
{
    BukkitTask task;
    
    public GateListener() {
        this.task = null;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void on(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Action a = e.getAction();
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (b.getType().toString().contains("_BUTTON")) {
                final DoorControler dc = new DoorControler(false);
                for (final Gate g : dc.getGates()) {
                    if (g.isOpened()) {
                        Chat.print("Gate is already opened!");
                        return;
                    }
                    if (DoorControler.isClosed || !g.canButtonInteract(p)) {
                        continue;
                    }
                    if (g.getButtons() == null) {
                        return;
                    }
                    for (final Block buttons : g.getButtons()) {
                        if (buttons.getLocation().equals((Object)b.getLocation()) && this.task == null) {
                            dc.controlGate(g.getId(), true);
                            this.taskCloseDoor(g.getId(), dc);
                            Chat.print("OPENING!");
                        }
                    }
                }
            }
        }
    }
    
    private void taskCloseDoor(final String id, final DoorControler dc) {
        this.task = new BukkitRunnable() {
            public void run() {
                dc.controlGate(id, false);
                GateListener.this.task = null;
            }
        }.runTaskLater((Plugin)Main.getInstance(), 60L);
    }
}
