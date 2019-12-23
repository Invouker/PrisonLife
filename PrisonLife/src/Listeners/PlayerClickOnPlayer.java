// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import Gui.PlayerGUI;

public class PlayerClickOnPlayer implements Listener
{
    @EventHandler
    public void on(final PlayerInteractEntityEvent e) {
        final Entity ent = e.getRightClicked();
        if (ent instanceof Player) {
            final Player target = (Player)ent;
            final Player player = e.getPlayer();
            final PlayerGUI pgui = new PlayerGUI();
            if (Bukkit.getPlayer(target.getName()) != null) {
                pgui.getInventory(target).open(player);
            }
        }
    }
}
