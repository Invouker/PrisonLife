// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import Cells.CellAsigner;
import Main.Chat;
import org.bukkit.inventory.Inventory;
import Items.SaveItem;
import org.bukkit.plugin.java.JavaPlugin;
import Main.ConfigListener;
import Main.Main;
import Solitary.solitaryCache;
import Solitary.SolitaryControl;
import BoxingBox.JoinBoxListener;
import BoxingBox.BoxData;
import ScoreBoard.Prisoner;
import Needs.NeedsUpdater;
import Main.PrisonType;
import org.bukkit.event.player.PlayerQuitEvent;
import Player.Data.PlayerDataHandler;
import Cells.CellDataProvider;
import Cells.SignListener;
import org.bukkit.event.Listener;

public class Left implements Listener
{
    SignListener sh;
    CellDataProvider cd;
    PlayerDataHandler pdh;
    
    public Left() {
        this.sh = new SignListener();
        this.cd = new CellDataProvider();
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final PlayerQuitEvent e) {
        e.setQuitMessage((String)null);
        final Player p = e.getPlayer();
        if (this.pdh.getData(p).getType() == PrisonType.PRISONER) {
            final NeedsUpdater nu = new NeedsUpdater();
            nu.removePlayer(p);
            Prisoner.removePlayer(p);
        }
        for (int i = 0; i < 10; ++i) {
            if (BoxData.getBox(i).getName1().equalsIgnoreCase(p.getName())) {
                BoxData.getBox(i).setName1("- NONE -");
                JoinBoxListener.updateSign(i);
            }
            else if (BoxData.getBox(i).getName2().equalsIgnoreCase(p.getName())) {
                BoxData.getBox(i).setName2("- NONE -");
                JoinBoxListener.updateSign(i);
            }
        }
        final SolitaryControl scon = new SolitaryControl();
        final int solitary = scon.getPlayerSolitary(p);
        if (solitary != -1) {
            final long toDate = solitaryCache.getL(solitary, solitaryCache.toDate);
            this.pdh.getData(p).setSolitary(toDate);
            scon.removePlayer(p, false);
        }
        this.pdh.saveData(p);
        if (this.pdh.getData(p).getType() != PrisonType.LOBBY) {
            final SaveItem si = new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), this.pdh.getData(p).getType().getName(), (Inventory)p.getInventory());
            si.save();
            si.clear();
            Chat.print("INV FILE NAME: PlayerData/" + p.getName() + ".yml");
            Chat.print("INV SECTION NAME: " + this.pdh.getData(p).getType().getName());
        }
        p.getInventory().clear();
        this.pdh.removePlayer(p);
        CellAsigner.unloadCellByPlayer(p);
        p.getInventory().clear();
    }
}
