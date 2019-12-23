// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener
{
    @EventHandler
    public void onPing(final ServerListPingEvent e) {
        e.setMotd("§r              §e§l\u25c4 §f§l\u2744 §e§l\u25ba§c§l Prison§e§l Life§7§l.EU §e§l\u25c4 §f§l\u2744 §e§l\u25ba\n§r  §e§l\u2606§c§l Life in State Prison §e§l\u2606 §d§lNews: Gangs§e§l §e§l\u2606");
    }
}
