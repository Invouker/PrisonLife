// 
// Decompiled by Procyon v0.5.36
// 

package Abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import Main.Chat;
import Main.ZoneVector;
import org.bukkit.event.player.PlayerMoveEvent;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class StaminaUpdate implements Listener
{
    PlayerDataHandler pdh;
    int track;
    
    public StaminaUpdate() {
        this.pdh = new PlayerDataHandler();
        this.track = 0;
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (ZoneVector.contains(p.getLocation(), -486.0, 77.0, 225.0, -489.0, 80.0, 225.0)) {
            if (this.track == 0) {
                this.track = 1;
                Chat.send(p, "stamina.start_run", new String[0]);
            }
        }
        else if (ZoneVector.contains(p.getLocation(), -486.0, 77.0, 207.0, -489.0, 80.0, 207.0)) {
            if (this.track == 1) {
                this.track = 2;
            }
        }
        else if (ZoneVector.contains(p.getLocation(), -495.0, 77.0, 185.0, -495.0, 80.0, 188.0)) {
            if (this.track == 2) {
                Chat.info(p, "TRACK: " + this.track);
                this.track = 3;
            }
        }
        else if (ZoneVector.contains(p.getLocation(), -503.0, 77.0, 212.0, -506.0, 80.0, 212.0)) {
            if (this.track == 3) {
                Chat.info(p, "TRACK: " + this.track);
                this.track = 4;
            }
        }
        else if (ZoneVector.contains(p.getLocation(), -494.0, 77.0, 236.0, -494.0, 80.0, 239.0) && this.track == 4) {
            Chat.info(p, "TRACK: " + this.track);
            this.track = 5;
        }
        if (ZoneVector.contains(p.getLocation(), -486.0, 77.0, 225.0, -489.0, 80.0, 225.0) && this.track == 5) {
            this.track = 0;
            Chat.send(p, "stop_run", new String[0]);
            this.pdh.getData(p).giveStaminaExp(1);
        }
    }
}
