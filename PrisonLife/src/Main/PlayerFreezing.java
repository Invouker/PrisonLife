// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreezing implements Listener
{
    public static HashMap<Player, Boolean> move;
    
    static {
        PlayerFreezing.move = new HashMap<Player, Boolean>();
    }
    
    public static void addPlayer(final Player p, final boolean movement) {
        if (!PlayerFreezing.move.containsKey(p)) {
            PlayerFreezing.move.put(p, movement);
        }
        else {
            PlayerFreezing.move.remove(p);
            PlayerFreezing.move.put(p, movement);
        }
    }
    
    public static void removePlayer(final Player p) {
        if (PlayerFreezing.move.containsKey(p)) {
            PlayerFreezing.move.remove(p);
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (PlayerFreezing.move.containsKey(p) && !PlayerFreezing.move.get(p)) {
            final Location from = e.getFrom();
            final Location to = e.getTo();
            double x = Math.floor(from.getX());
            double z = Math.floor(from.getZ());
            if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
                x += 0.5;
                z += 0.5;
                e.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
            }
        }
    }
}
