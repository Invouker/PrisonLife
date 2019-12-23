// 
// Decompiled by Procyon v0.5.36
// 

package ScoreBoard;

import org.bukkit.scoreboard.Team;
import org.bukkit.Bukkit;

public class TeamHandler
{
    public static void addScoreboard() {
        final Team red = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_red");
        red.setPrefix("§c");
        red.setAllowFriendlyFire(false);
        final Team yellow = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_yellow");
        yellow.setPrefix("§e");
        yellow.setAllowFriendlyFire(false);
        final Team guard = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_guard");
        guard.setPrefix("§9");
        guard.setAllowFriendlyFire(false);
        final Team admin = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_admin");
        admin.setPrefix("§a§lAdmin §a");
        final Team box = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_box");
        box.setAllowFriendlyFire(true);
        final Team lobby = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("prison_lobby");
        lobby.setPrefix("§7§o");
        lobby.setAllowFriendlyFire(false);
    }
}
