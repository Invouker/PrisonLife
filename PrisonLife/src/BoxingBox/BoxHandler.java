// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import Main.Chat;
import Utils.Functions;

public class BoxHandler implements Listener
{
    @EventHandler
    public void signChange(final SignChangeEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("prison.setup.cell.sign")) {
            if (e.getLine(0).equalsIgnoreCase("%box%")) {
                final String id = e.getLine(1);
                if (Functions.isNum(id)) {
                    final int box = Integer.parseInt(id);
                    final Location loc = e.getBlock().getLocation();
                    BoxData.getBox(box).setSignLoc(loc);
                    e.setLine(0, "§cBoxing§8 Ring");
                    e.setLine(1, "- NONE -");
                    e.setLine(2, "§8versus");
                    e.setLine(3, "- NONE -");
                    e.getBlock().getState().update();
                    BoxData.saveConfigurationOfBox(box);
                }
                else {
                    Chat.send(p, "box.no-create", new String[0]);
                }
            }
        }
        else {
            Chat.noPerm(p);
        }
    }
}
