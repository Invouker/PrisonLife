// 
// Decompiled by Procyon v0.5.36
// 

package ScoreBoard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import Main.Chat;
import Main.Main;
import Player.Data.PlayerDataHandler;

public class Prisoner
{
    private static Scoreboard board;
    private Objective obj;
    private static List<Player> players;
    BukkitTask updater;
    PlayerDataHandler pdh;
    
    static {
        Prisoner.players = new ArrayList<Player>();
    }
    
    public Prisoner() {
        this.pdh = new PlayerDataHandler();
    }
    
    public void addPrisonScoreBoardToPlayer(final Player p) {
        Prisoner.board = Bukkit.getScoreboardManager().getNewScoreboard();
        if (Prisoner.board.getObjective("prisonlife") == null) {
            this.obj = Prisoner.board.registerNewObjective("prisonlife", "dummy", "sidebar");
        }
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.obj.setDisplayName("  §c§lPRISON §e§lLIFE  ");
        this.spacer(15, ChatColor.GRAY, ChatColor.BLACK);
        final int wc = this.pdh.getData(p).getWC();
        final int hyg = this.pdh.getData(p).getHygiene();
        final int sleep = this.pdh.getData(p).getSleep();
        final int thirst = this.pdh.getData(p).getThirst();
        final int respect = this.pdh.getData(p).getRespect();
        final int strength = this.pdh.getData(p).getStrength();
        final int stamina = this.pdh.getData(p).getStamina();
        this.createTag("Wc", ChatColor.BLACK, ChatColor.AQUA, "§7» §e§lWc ", new StringBuilder().append(this.color(wc)).append(wc).append("%").toString(), 14);
        this.createTag("Hygiene", ChatColor.BLACK, ChatColor.BLACK, "§7» §b§lHygiene ", new StringBuilder().append(this.color(hyg)).append(hyg).append("%").toString(), 11);
        this.createTag("Sleep", ChatColor.BLACK, ChatColor.BLUE, "§7» §6§lSleep ", new StringBuilder().append(this.color(sleep)).append(sleep).append("%").toString(), 11);
        this.createTag("Thirst", ChatColor.BLACK, ChatColor.LIGHT_PURPLE, "§7» §9§lThirst ", new StringBuilder().append(this.color(thirst)).append(thirst).append("%").toString(), 11);
        this.spacer(10, ChatColor.AQUA, ChatColor.BLACK);
        this.createTag("Crafter", ChatColor.DARK_RED, ChatColor.AQUA, "§7» §6§lCrafter", " §f0", 9);
        this.createTag("Respect", ChatColor.DARK_RED, ChatColor.BLACK, "§7» §2§lRespect", " §f" + respect, 8);
        this.createTag("Stamina", ChatColor.DARK_RED, ChatColor.BLUE, "§7» §a§lStamina", " §f" + stamina, 7);
        this.createTag("Strength", ChatColor.DARK_RED, ChatColor.BOLD, "§7» §c§lStrength", " §f" + strength, 6);
        this.createTag("Intellect", ChatColor.DARK_RED, ChatColor.GRAY, "§7» §5§lIntell", "§5§lect §f0", 5);
        this.spacer(4, ChatColor.AQUA, ChatColor.BLUE);
        this.createTag("coin", ChatColor.DARK_RED, ChatColor.UNDERLINE, "§6§lCoins", "§f0", 3);
        this.createTag("cellID", ChatColor.DARK_RED, ChatColor.DARK_AQUA, "§aYour cell ID: ", "§f0", 2);
        this.createTag("Agressive", ChatColor.DARK_RED, ChatColor.DARK_BLUE, "§cAggressi", "§cveness: §fNo", 1);
        this.spacer(1, ChatColor.AQUA, ChatColor.LIGHT_PURPLE);
        addPlayer(p);
        Team team = Prisoner.board.getTeam("yellow_pr");
        if (team != null) {
            team.unregister();
        }
        team = Prisoner.board.registerNewTeam("yellow_pr");
        team.setColor(ChatColor.YELLOW);
        for (final Player P : Bukkit.getOnlinePlayers()) {
            team.addEntry(P.getName());
        }
        p.setScoreboard(Prisoner.board);
    }
    
    public void updateScoreBoard() {
        this.updater = new BukkitRunnable() {
            public void run() {
                if (Prisoner.players != null && !Prisoner.players.isEmpty()) {
                    for (final Player p : Prisoner.players) {
                        if (p == null) {
                            Prisoner.removePlayer(p);
                        }
                        final int wc = Prisoner.this.pdh.getData(p).getWC();
                        final int hyg = Prisoner.this.pdh.getData(p).getHygiene();
                        final int sleep = Prisoner.this.pdh.getData(p).getSleep();
                        final int thirst = Prisoner.this.pdh.getData(p).getThirst();
                        final int respect = Prisoner.this.pdh.getData(p).getRespect();
                        final int strength = Prisoner.this.pdh.getData(p).getStrength();
                        final int stamina = Prisoner.this.pdh.getData(p).getStamina();
                        final int intellect = Prisoner.this.pdh.getData(p).getIntellect();
                        final int coins = Prisoner.this.pdh.getData(p).getCoins();
                        final int cell = Prisoner.this.pdh.getData(p).getCell();
                        final boolean aggresive = Prisoner.this.pdh.getData(p).getAggresive();
                        final Scoreboard boar = p.getScoreboard();
                        try {
                            boar.getTeam("Wc").setSuffix(new StringBuilder().append(Prisoner.this.color(wc)).append(wc).append("%").toString());
                            boar.getTeam("Hygiene").setSuffix(new StringBuilder().append(Prisoner.this.color(hyg)).append(hyg).append("%").toString());
                            boar.getTeam("Sleep").setSuffix(new StringBuilder().append(Prisoner.this.color(sleep)).append(sleep).append("%").toString());
                            boar.getTeam("Thirst").setSuffix(new StringBuilder().append(Prisoner.this.color(thirst)).append(thirst).append("%").toString());
                            boar.getTeam("Respect").setSuffix("§f " + respect);
                            boar.getTeam("Stamina").setSuffix("§f " + stamina);
                            boar.getTeam("Strength").setSuffix("§f " + strength);
                            boar.getTeam("Intellect").setSuffix("§5§lect §f" + intellect);
                            boar.getTeam("coin").setSuffix("§f  " + coins);
                            boar.getTeam("cellID").setSuffix("§f " + cell);
                            boar.getTeam("Agressive").setSuffix("§cveness: §f" + Prisoner.this.yesno(aggresive));
                        }
                        catch (NullPointerException ex) {
                            ex.printStackTrace();
                            Chat.print("Cannot show ScoreBoard for a plyer: " + p.getName());
                        }
                        p.setScoreboard(boar);
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 40L);
    }
    
    public ChatColor color(final int number) {
        final ChatColor col = ChatColor.GOLD;
        if (number >= 0 && number <= 15) {
            return ChatColor.DARK_RED;
        }
        if (number >= 16 && number <= 40) {
            return ChatColor.RED;
        }
        if (number >= 41 && number <= 65) {
            return ChatColor.DARK_GREEN;
        }
        if (number >= 66 && number <= 100) {
            return ChatColor.GREEN;
        }
        return col;
    }
    
    public void spacer(final int pos, final ChatColor c1, final ChatColor c2) {
        this.obj.getScore(new StringBuilder().append(c1).append(c2).toString()).setScore(pos);
    }
    
    public Team createTag(final String registerTeam, final ChatColor c1, final ChatColor c2, final String prefix, final String suffix, final int pos) {
        final Team objekt = Prisoner.board.registerNewTeam(registerTeam);
        objekt.addEntry(new StringBuilder().append(c1).append(c2).toString());
        objekt.setPrefix(prefix);
        objekt.setSuffix(suffix);
        this.obj.getScore(new StringBuilder().append(c1).append(c2).toString()).setScore(pos);
        return objekt;
    }
    
    public Team createItem(final String registerTeam, final String name, final int pos) {
        final Team objekt = Prisoner.board.registerNewTeam(registerTeam);
        objekt.addEntry(name);
        this.obj.getScore(name).setScore(pos);
        return objekt;
    }
    
    public String yesno(final boolean data) {
        return data ? "Yes" : "No";
    }
    
    public String getFString(final Player p, final String source) {
        final String lang = Chat.getT("scoreboard." + source, p, new String[0]);
        final String s = substring(0, lang.length() / 2, lang);
        return s;
    }
    
    public String getSString(final Player p, final String source, final String replacement) {
        final String lang = Chat.getT("scoreboard." + source, p, new String[0]);
        String s = substring(lang.length() / 2, lang.length(), lang);
        s = s.replaceFirst("%vault%", replacement);
        return s;
    }
    
    public static String substring(final int a, final int b, final String temp) {
        String c = "";
        for (int i = a; i < b; ++i) {
            final char ch1 = temp.charAt(i);
            c = String.valueOf(c) + ch1;
        }
        return c;
    }
    
    public static void addPlayer(final Player p) {
        if (!Prisoner.players.contains(p)) {
            Prisoner.players.add(p);
        }
    }
    
    public static void removePlayer(final Player p) {
        if (Prisoner.players.contains(p)) {
            Prisoner.players.remove(p);
        }
    }
    
    public Scoreboard getScoreBoard() {
        return Prisoner.board;
    }
}
