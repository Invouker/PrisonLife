// 
// Decompiled by Procyon v0.5.36
// 

package ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import Main.Main;

public class GuardScoreBoard
{
    private static Scoreboard board;
    private static Objective obj;
    
    public void setScoreBoard(final Player p) {
        GuardScoreBoard.board = Bukkit.getScoreboardManager().getNewScoreboard();
        GuardScoreBoard.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        GuardScoreBoard.obj.setDisplayName("  §c§lPRISON §e§lLIFE  ");
        this.spacer(15, ChatColor.GRAY, ChatColor.BLACK);
        this.createTag("setRespect", ChatColor.BLACK, ChatColor.BOLD, "", "§f§l", 14);
        this.spacer(11, ChatColor.AQUA, ChatColor.BLACK);
        this.createItem("Respect", "§7» §2§lRespect ", 10);
        this.createTag("setRespect", ChatColor.BLACK, ChatColor.BOLD, "", "§f§l", 9);
        this.spacer(2, ChatColor.AQUA, ChatColor.LIGHT_PURPLE);
        this.updateScoreBoard(p);
        p.setScoreboard(GuardScoreBoard.board);
    }
    
    public void updateScoreBoard(final Player p) {
        new BukkitRunnable() {
            public void run() {
                GuardScoreBoard.board.getTeam("Wc").setSuffix("");
                GuardScoreBoard.board.getTeam("Hygiene").setSuffix("");
                GuardScoreBoard.board.getTeam("Sleep").setSuffix("");
                GuardScoreBoard.board.getTeam("setRespect").setSuffix("");
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 40L);
    }
    
    public void spacer(final int pos, final ChatColor c1, final ChatColor c2) {
        GuardScoreBoard.obj.getScore(new StringBuilder().append(c1).append(c2).toString()).setScore(pos);
    }
    
    public Team createTag(final String registerTeam, final ChatColor c1, final ChatColor c2, final String prefix, final String suffix, final int pos) {
        final Team objekt = GuardScoreBoard.board.registerNewTeam(registerTeam);
        objekt.addEntry(new StringBuilder().append(c1).append(c2).toString());
        objekt.setPrefix(prefix);
        objekt.setSuffix(suffix);
        GuardScoreBoard.obj.getScore(new StringBuilder().append(c1).append(c2).toString()).setScore(pos);
        return objekt;
    }
    
    public Team createItem(final String registerTeam, final String name, final int pos) {
        final Team objekt = GuardScoreBoard.board.registerNewTeam(registerTeam);
        objekt.addEntry(name);
        GuardScoreBoard.obj.getScore(name).setScore(pos);
        return objekt;
    }
}
