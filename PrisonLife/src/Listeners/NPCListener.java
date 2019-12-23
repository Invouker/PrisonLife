// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import Gui.CookGui;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import Events.ChooseTeamEvent;
import Main.PrisonType;
import Main.Chat;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.Listener;

public class NPCListener implements Listener
{
    @EventHandler
    public void onNPCClick(final NPCRightClickEvent e) {
        final Player p = e.getClicker();
        if (e.getNPC().getName().equalsIgnoreCase("§e§lPrisoner")) {
            Chat.info(p, "Klikol si na vezna !");
            final ChooseTeamEvent cte = new ChooseTeamEvent(p, PrisonType.PRISONER.getID());
            Bukkit.getPluginManager().callEvent((Event)cte);
            return;
        }
        if (e.getNPC().getName().equalsIgnoreCase("§9§lWarden")) {
            Chat.info(p, "Klikol si na guarda !");
            final ChooseTeamEvent cte = new ChooseTeamEvent(p, PrisonType.WARDEN.getID());
            Bukkit.getPluginManager().callEvent((Event)cte);
            return;
        }
        if (e.getNPC().getName().contains("Greg")) {
            final CookGui cGui = new CookGui();
            cGui.getInventory(p).open(p);
        }
    }
    
    @EventHandler
    public void onLeftClick(final NPCLeftClickEvent e) {
        final Player p = e.getClicker();
        if (e.getNPC().getName().equalsIgnoreCase("§e§lPrisoner")) {
            Chat.sendList(p, "ClickNPC.left.prisoner.help", new String[0]);
        }
        if (e.getNPC().getName().equalsIgnoreCase("§9§lWarden")) {
            Chat.sendList(p, "ClickNPC.left.warden.help", new String[0]);
        }
    }
}
