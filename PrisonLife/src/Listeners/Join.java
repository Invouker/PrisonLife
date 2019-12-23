// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import Main.Maintance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import ScoreBoard.Prisoner;
import Cells.CellAsigner;
import Main.Chat;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import Version.sendPlayerTabList;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import Main.Main;
import Needs.NeedsUpdater;
import Main.PlayerDataListener;
import org.bukkit.event.Listener;

public class Join implements Listener
{
    PlayerDataListener pd;
    NeedsUpdater nu;
    Main plugin;
    
    public Join() {
        this.pd = new PlayerDataListener();
        this.nu = new NeedsUpdater();
        this.plugin = (Main)Main.getPlugin((Class)Main.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) {
        e.setJoinMessage((String)null);
        final Player p = e.getPlayer();
        final PlayerDataHandler pdh = new PlayerDataHandler();
        pdh.loadData(p);
        final String ver = Main.getInstance().getDescription().getVersion();
        sendPlayerTabList.sendPlayerListTab(p, "§c\n      §c§lPRISON §e§lLIFE      \n§7v" + ver + "\n§r", "§r\n§3§lPrison§f§lLife.§7eu\n§r");
        if (!this.pd.playerExists(p.getUniqueId())) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("prison_yellow").addPlayer((OfflinePlayer)p);
            pdh.createFirstTimeConnect(p);
            Chat.sendListAll("player-first-time", p.getName());
            CellAsigner.assignCell(p);
            this.pd.createPlayer(e.getPlayer());
            this.nu.addPlayer(p);
            final Prisoner psb = new Prisoner();
            psb.addPrisonScoreBoardToPlayer(p);
            pdh.getData(p).setPrisoner(1);
        }
        else {
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("prison_lobby");
            if (team == null) {
                team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_lobby");
            }
            team.addEntry(p.getName());
            final Location loc = new Location(Bukkit.getWorld(Main.mainWorld), -475.5, 93.0, 324.5);
            loc.setYaw(180.0f);
            e.getPlayer().teleport(loc);
            pdh.loadData(p);
        }
        if (this.checkPlayerMaintance(p)) {
            p.kickPlayer("§7§m-----]§r §cMaintance Mode §7§m[-----\n§r\n§cYou aren't on allowed player list\n§r\n§7Get more news about this server on\n§6... WEBPAGE IS IN PROGRESS ...");
        }
    }
    
    public String timestampToDate(final long timestamp) {
        final Date date = new Date(timestamp);
        final DateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        final String dateFormatted = formatter.format(date);
        return dateFormatted;
    }
    
    public boolean checkPlayerMaintance(final Player p) {
        return Maintance.statusWhitelist() && (!Maintance.isWhiteListed(p) || !p.getName().equals("XpresS"));
    }
    
    public String getNameClass(final int classID) {
        String name = "";
        switch (classID) {
            case 1: {
                name = "Prisoner";
                break;
            }
            case 2: {
                name = "Guard";
                break;
            }
            case 3: {
                name = "Admin";
                break;
            }
            default: {
                name = "ERROR";
                break;
            }
        }
        return name;
    }
}
