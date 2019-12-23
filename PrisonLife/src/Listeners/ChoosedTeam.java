// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import Cells.CellAsigner;
import Cells.CellDataProvider;
import Events.ChooseTeamEvent;
import Items.LoadItem;
import Main.Chat;
import Main.ConfigListener;
import Main.Main;
import Main.PrisonType;
import Needs.NeedsUpdater;
import Player.Data.PlayerDataHandler;
import ScoreBoard.Prisoner;
import Solitary.SolitaryControl;
import skinsrestorer.bukkit.SkinsRestorer;

public class ChoosedTeam implements Listener
{
    CellDataProvider cd;
    NeedsUpdater nu;
    PlayerDataHandler pdh;
    Main main;
    
    public ChoosedTeam() {
        this.cd = new CellDataProvider();
        this.nu = new NeedsUpdater();
        this.pdh = new PlayerDataHandler();
        this.main = Main.getInstance();
    }
    
    @EventHandler
    public void ChooseTeamEvent(final ChooseTeamEvent e) {
        final Player p = e.getPlayer();
        switch (e.getType()) {
            case PRISONER: {
                final SolitaryControl scon = new SolitaryControl();
                final long toDate = this.pdh.getData(p).getSolitary();
                if (toDate > 0L) {
                    final int firstEmpty = scon.firstEmpty();
                    if (firstEmpty == -1) {
                        Chat.send(p, "notEmpty-Solitary", new String[0]);
                        e.setCancelled(true);
                    }
                    scon.addPlayer(firstEmpty, p, toDate, "Quit");
                    this.pdh.getData(p).setType(PrisonType.PRISONER);
                    break;
                }
                this.pdh.getData(p).setType(PrisonType.PRISONER);
                final Prisoner psb = new Prisoner();
                psb.addPrisonScoreBoardToPlayer(p);
                CellAsigner.assignCell(p);
                this.nu.addPlayer(p);
                p.setPlayerListName("§e" + p.getName());
                break;
            }
            case WARDEN: {
                if (this.pdh.getData(p).getPoints() >= 6700) {
                    this.pdh.getData(p).setType(PrisonType.WARDEN);
                    final World w = Bukkit.getServer().getWorld(Main.mainWorld);
                    final Location wLoc = new Location(w, -476.0, 77.0, 294.0);
                    p.teleport(wLoc);
                    p.setBedSpawnLocation(wLoc, true);
                    p.setPlayerListName("§9" + p.getName());
                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("prison_guard").addPlayer((OfflinePlayer)p);
                    break;
                }
                final int have = this.pdh.getData(p).getPoints();
                Chat.send(p, "noPoints", String.valueOf(have), String.valueOf(6700));
                e.setCancelled(true);
                break;
            }
            case ADMIN: {
                this.pdh.getData(p).setType(PrisonType.ADMIN);
                final World w = Bukkit.getServer().getWorld(Main.mainWorld);
                final Location aLoc = new Location(w, -476.0, 82.0, 282.0);
                p.teleport(aLoc);
                p.setBedSpawnLocation(aLoc, true);
                p.setPlayerListName("§a§lAdmin §a" + p.getName());
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("prison_admin").addPlayer((OfflinePlayer)p);
                break;
            }
        }
        final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), this.pdh.getData(p).getType().getName());
        if (li != null) {
            final ItemStack[] is = li.getItemsPole();
            p.getInventory().setContents(is);
        }
        else {
            Chat.print("ERROR While loading player inventory contents");
        }
        Chat.print("Player " + p.getName() + " connected to " + e.getType().getName());
    }
    
    public void applySkin(final Player p, final PrisonType type) {
        SkinsRestorer sapi = null;
        if (Main.isSkinsRestorerLoaded) {
            sapi = new SkinsRestorer();
        }
        if (sapi == null) {
            return;
        }
        if (PrisonType.WARDEN == type) {
            final OfflinePlayer warden = Bukkit.getOfflinePlayer(this.getWardenName());
            sapi.getFactory().applySkin(p, (Object)warden);
        }
        if (PrisonType.ADMIN == type) {
            final OfflinePlayer warden = Bukkit.getOfflinePlayer(this.getWardenName());
            sapi.getFactory().applySkin(p, (Object)warden);
        }
    }
    
    public String getWardenName() {
        final Random rand = new Random();
        final int nahodne = rand.nextInt(3) + 1;
        if (nahodne == 1) {
            return "PoliceOfficer";
        }
        if (nahodne == 2) {
            return "TheFruitNinja";
        }
        if (nahodne == 3) {
            return "kizeko";
        }
        return "PoliceOfficer";
    }
}
