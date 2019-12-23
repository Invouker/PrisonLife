// 
// Decompiled by Procyon v0.5.36
// 

package ScoreBoard;

import java.util.ArrayList;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import lombok.Getter;


public class TeamHandler {
	
	@Getter 
	private Scoreboard sb;
	
	@Getter 
	private ArrayList<Team> teams;
	
	public TeamHandler (Scoreboard sb) {
		this.sb = sb;
		this.teams = new ArrayList<Team>();
	}
	
	
	public void registerTeams() {
		registerTeam("prisoner_red", "�c");
		registerTeam("prisoner_yellow", "�e");
		registerTeam("guard", "�9");
		registerTeam("admin", "�cAdmin ");
	}
	
	public void registerTeam(String teamName, String prefix) {
		Team team = sb.getTeam(teamName);
		if(team == null) {
			sb.registerNewTeam(teamName);
		}
		team.setPrefix(prefix);
		teams.add(team);
	}
	/*
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
    */
    
}
