// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Abilities.Boxing_bag;
import Abilities.StaminaUpdate;
import Basketball.HitHandler;
import BoxingBox.BoxData;
import BoxingBox.BoxHandler;
import BoxingBox.BoxWin;
import BoxingBox.CmdBoxHandler;
import BoxingBox.JoinBoxListener;
import Cells.CellAsigner;
import Cells.SignListener;
import Commands.PrisonCommands;
import Commands.gamemodes;
import Commands.langSelector;
import Commands.playercmd;
import DayLightCycle.DayLight;
import DayLightCycle.Listener.GateListener;
import Gangs.GangCommands;
import Gangs.GangDataManager;
import Gangs.GangManager;
import Items.SaveItem;
import Items.Enchantments.BoxingGloves;
import Jobs.Farmer.PlaceCrops;
import Jobs.Washing.DeliverTake;
import Jobs.Washing.JoinWashing;
import Jobs.Washing.WashingMachine;
import Jobs.Washing.WearJobArmour;
import Listeners.BlueprintInventory;
import Listeners.CellChestListener;
import Listeners.ChatReaction;
import Listeners.ChoosedTeam;
import Listeners.CraftingListener;
import Listeners.DoorListener;
import Listeners.Elevator;
import Listeners.Join;
import Listeners.Left;
import Listeners.MineBlockListener;
import Listeners.NPCListener;
import Listeners.PlayerClickOnPlayer;
import Listeners.PlayerDeathListener;
import Listeners.PoweredRedstoneLamp;
import Listeners.PrisonProtection;
import Listeners.ServerListPing;
import Merchants.Robert;
import Needs.HungerUpdate;
import Needs.NeedsUpdater;
import Needs.Updator;
import Needs.WcUpdate;
import Player.Data.PlayerDataHandler;
import ScoreBoard.Prisoner;
import Solitary.SolitaryControl;
import Solitary.SolitarySignController;
import Solitary.solitaryCache;
import Solitary.solitaryCommands;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin implements Listener
{
    public static final int minimumPointsToWarden = 6700;
    public static final int maximumWConServer = 100;
    public static final int MAX_CELLS = 144;
    public static boolean isSkinsRestorerLoaded;
    public static String mainWorld;
    private static ConfigListener cell;
    private static ConfigListener lang;
    private static ConfigListener whitelist;
    private static ConfigListener boxes;
    public static HashMap<String, CommandExecutor> commandsExecutor;
    public static HashMap<String, String> commandsDescription;
    public static HashMap<String, ArrayList<String>> commandsAliases;
    public static boolean debug;
    private static Main main;
    public static Chat chat;
    BoxingGloves bg;
    PlayerDataHandler pdh;
    
    static {
        Main.isSkinsRestorerLoaded = false;
        Main.mainWorld = "PrisonLife";
        Main.commandsExecutor = new HashMap<String, CommandExecutor>();
        Main.commandsDescription = new HashMap<String, String>();
        Main.commandsAliases = new HashMap<String, ArrayList<String>>();
        Main.debug = true;
        Main.chat = null;
    }
    
    public Main() {
        this.bg = new BoxingGloves(new NamespacedKey((Plugin)this, "BoxingGloves"));
        this.pdh = new PlayerDataHandler();
    }
    
    public static ConfigListener getWhiteList() {
        return Main.whitelist;
    }
    
    public static ConfigListener getBoxes() {
        return Main.boxes;
    }
    
    public static ConfigListener getCell() {
        return Main.cell;
    }
    
    public static Main getInstance() {
        return Main.main;
    }
    
    public static ConfigListener getLang() {
        return Main.lang;
    }
    
    public void onEnable() {
        Main.Chat.print("§fPlugin has been §aenabled §f!");
        Main.main = this;
        Main.Chat.print("Loading Gangs Data to memory...");
        final GangDataManager gdm = new GangDataManager();
        gdm.loadData();
        Main.Chat.print("Registring all commands ...");
        this.registerCommands();
        Main.Chat.print("Registring configs...");
        Main.cell = new ConfigListener(this, "Cells.yml", "Cells.yml");
        Main.lang = new ConfigListener(this, "Lang.yml", "Lang.yml");
        Main.whitelist = new ConfigListener(this, "WhiteList.yml", "WhiteList.yml");
        Main.boxes = new ConfigListener(this, "Boxes.yml", "Boxes.yml");
        Main.Chat.print("Detectings Plugins:");
        if (this.getServer().getPluginManager().getPlugin("Citizens") == null) {
            Main.Chat.print("Server doesnt found a Citizens Plugin!");
            Bukkit.getServer().shutdown();
        }
        else {
            Main.Chat.print("Citizens plugin has been found and hooked on it!");
        }
        if (this.getServer().getPluginManager().getPlugin("SkinsRestorer") == null) {
            Main.Chat.print("Server doesnt found a SkinsRestorer Plugin!");
        }
        else {
            Main.isSkinsRestorerLoaded = true;
            Main.Chat.print("SkinRestorer plugin has been found and hooked on it!");
        }
        Main.Chat.print("Registring all events ...");
        this.registerEvents();
        if (this.getServer().getPluginManager().getPlugin("Vault") != null) {
            Main.Chat.print("Vault plugin has been found, and hooked on it!");
            Main.Chat.setupChat();
        }
        else {
            Main.Chat.print("§cServer doesnt found a Vault Plugin!");
            Bukkit.getServer().shutdown();
        }
        Main.Chat.print("Creating timer for DayLight cycle!");
        DayLight.timeTask();
        Main.Chat.print("Creating timer for player data synchronizing!");
        this.pdh.dataSynchronizing();
        Main.Chat.print("When server is reloaded, loading cache data ...");
        for (final Player p : Bukkit.getOnlinePlayers()) {
            this.pdh.loadData(p);
        }
        Main.Chat.print("Load boxes to cache...");
        BoxData.loadConfiguration();
        Main.Chat.print("Loadings ADs / TIPs");
        AutoMessage.setupAD();
        Main.Chat.print("Loading white-list data...");
        Maintance.loadWhiteList();
        Main.Chat.print("Creating timer for Needs-Updater!");
        final NeedsUpdater nu = new NeedsUpdater();
        nu.update();
        Main.Chat.print("Creating updater for ScoreBoards!");
        final Prisoner psb = new Prisoner();
        psb.updateScoreBoard();
        Main.Chat.print("Creating timer for ChatReaction");
        ChatReaction.runChatReaction();
        Main.Chat.print("Loading Custom Enchantments!");
        this.LoadEnchantments();
        Main.Chat.print("Initializing Custom Crafting Recipes!");
        final CraftingListener crafListener = new CraftingListener();
        crafListener.createRecipes();
        Main.Chat.print("Loading solitaries to cache!");
        solitaryCache.loader();
        solitaryCache.dataSynchronizing();
        Main.Chat.print("Creating cooldown system");
        GangManager.updateCooldown();
        final SolitaryControl scon = new SolitaryControl();
        scon.updateAllSigns();
        Main.Chat.print("Prison life is fully loaded !");
        Main.Chat.print("");
        Main.Chat.print("§cPrison Life by XpresS");
        Main.Chat.print("Date of start creating: 14.11.2017");
        Main.Chat.print("Planned date of beta: 12.9.2019");
        Main.Chat.print("Creating with somebreaks and other thinks!");
        Main.Chat.print("");
        Main.Chat.print("Love too much wardens!");
        Main.Chat.print("");
    }
    
    public void onDisable() {
        Main.Chat.print("§cStarting saving data, and restore to default!");
        for (final Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("prison_lobby").addEntry(p.getName());
            if (!Main.debug) {
                final Location loc = new Location(Bukkit.getWorld(Main.mainWorld), -475.5, 93.0, 324.5);
                loc.setYaw(180.0f);
                p.teleport(loc);
            }
            if (p.getOpenInventory().getTitle().contains("Player chest - ")) {
                new SaveItem(new ConfigListener(getInstance(), "PlayerData/" + p.getName() + ".yml"), "Vezen.Chest", p.getOpenInventory().getTopInventory()).save();
            }
            p.closeInventory();
            final SolitaryControl scon = new SolitaryControl();
            SolitaryControl.onDisable = false;
            final int solitary = scon.getPlayerSolitary(p);
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            if (solitary != -1) {
                final long toDate = solitaryCache.getL(solitary, solitaryCache.toDate);
                this.pdh.getData(p).setSolitary(toDate);
                scon.removePlayer(p, false);
            }
            Main.Chat.print("Saving & Unloading players data!");
            this.pdh.saveData(p);
            this.pdh.removePlayer(p);
        }
        GangManager.removeHolograms();
        Main.Chat.print("Saving Gangs Data...");
        final GangDataManager gdm = new GangDataManager();
        gdm.saveData();
        Main.Chat.print("Unloading enchantments!");
        this.UnloadEnchantments();
        this.unloadAll();
        for (int id = 0; id < BoxData.boxes.size() - 1; ++id) {
            if (BoxData.getBox(id) != null || BoxData.getBox(id).getSignLoc() != null) {
                final int x = BoxData.getBox(id).getSignLoc().getBlockX();
                final int y = BoxData.getBox(id).getSignLoc().getBlockY() - 1;
                final int z = BoxData.getBox(id).getSignLoc().getBlockZ();
                final String world = BoxData.getBox(id).getSignLoc().getWorld().getName();
                final Location loc2 = new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z);
                final String sP1 = BoxData.getBox(id).getName1();
                final String sP2 = BoxData.getBox(id).getName2();
                if (!sP1.equals("- NONE -")) {
                    final Player p2 = Bukkit.getPlayer(sP1);
                    if (p2 != null) {
                        p2.teleport(loc2);
                    }
                }
                if (!sP2.equals("- NONE -")) {
                    final Player p2 = Bukkit.getPlayer(sP2);
                    if (p2 != null) {
                        p2.teleport(loc2);
                    }
                }
                BoxData.getBox(id).setName1("- NONE -");
                BoxData.getBox(id).setName2("- NONE -");
                JoinBoxListener.updateSign(id);
            }
        }
        Main.Chat.print("§fPlugin has been §cdisabled§f !");
    }
    
    private void unloadAll() {
        for (int MaxSlots = Bukkit.getServer().getMaxPlayers(), i = 1; i < MaxSlots; ++i) {
            CellAsigner.unloadCellData(i);
        }
        Main.Chat.print("All has been disabled from PrisonLife Plugin");
    }
    
    private void registerCommands() {
        this.getCommand("prison").setExecutor((CommandExecutor)new PrisonCommands());
        this.getCommand("changeduty").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("box").setExecutor((CommandExecutor)new CmdBoxHandler());
        this.getCommand("credit").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("credits").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("by").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("created").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("news").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("help").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("support").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("channel").setExecutor((CommandExecutor)new playercmd());
        this.getCommand("gms").setExecutor((CommandExecutor)new gamemodes());
        this.getCommand("gmc").setExecutor((CommandExecutor)new gamemodes());
        this.getCommand("gmsp").setExecutor((CommandExecutor)new gamemodes());
        this.getCommand("gma").setExecutor((CommandExecutor)new gamemodes());
        this.getCommand("language").setExecutor((CommandExecutor)new langSelector());
        this.getCommand("solitary").setExecutor((CommandExecutor)new solitaryCommands());
        this.getCommand("gadmin").setExecutor((CommandExecutor)new GangCommands());
        this.getCommand("gangs").setExecutor((CommandExecutor)new GangCommands());
        this.getCommand("gang").setExecutor((CommandExecutor)new GangCommands());
    }
    
    private void registerEvents() {
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents((Listener)new PoweredRedstoneLamp(), (Plugin)this);
        pm.registerEvents((Listener)new ServerListPing(), (Plugin)this);
        pm.registerEvents((Listener)new Join(), (Plugin)this);
        pm.registerEvents((Listener)new Left(), (Plugin)this);
        pm.registerEvents((Listener)new Main.Chat(), (Plugin)this);
        pm.registerEvents((Listener)new SignListener(), (Plugin)this);
        pm.registerEvents((Listener)new NPCListener(), (Plugin)this);
        pm.registerEvents((Listener)new ChoosedTeam(), (Plugin)this);
        pm.registerEvents((Listener)new BlockLocation(), (Plugin)this);
        pm.registerEvents((Listener)new PrisonProtection(), (Plugin)this);
        pm.registerEvents((Listener)new Robert(), (Plugin)this);
        pm.registerEvents((Listener)new HitHandler(), (Plugin)this);
        pm.registerEvents((Listener)new WcUpdate(), (Plugin)this);
        pm.registerEvents((Listener)new Updator(), (Plugin)this);
        pm.registerEvents((Listener)new HungerUpdate(), (Plugin)this);
        pm.registerEvents((Listener)new StaminaUpdate(), (Plugin)this);
        pm.registerEvents((Listener)new Boxing_bag(), (Plugin)this);
        pm.registerEvents((Listener)new BoxHandler(), (Plugin)this);
        pm.registerEvents((Listener)new JoinBoxListener(), (Plugin)this);
        pm.registerEvents((Listener)new PlayerFreezing(), (Plugin)this);
        pm.registerEvents((Listener)new BoxWin(), (Plugin)this);
        pm.registerEvents((Listener)this, (Plugin)this);
        pm.registerEvents((Listener)new SolitarySignController(), (Plugin)this);
        pm.registerEvents((Listener)new Elevator(), (Plugin)this);
        pm.registerEvents((Listener)new CellChestListener(), (Plugin)this);
        pm.registerEvents((Listener)new DoorListener(), (Plugin)this);
        pm.registerEvents((Listener)new MineBlockListener(), (Plugin)this);
        pm.registerEvents((Listener)this.bg, (Plugin)this);
        pm.registerEvents((Listener)new BlueprintInventory(), (Plugin)this);
        pm.registerEvents((Listener)new CraftingListener(), (Plugin)this);
        pm.registerEvents((Listener)new JoinWashing(), (Plugin)this);
        pm.registerEvents((Listener)new WashingMachine(), (Plugin)this);
        pm.registerEvents((Listener)new DeliverTake(), (Plugin)this);
        pm.registerEvents((Listener)new WearJobArmour(), (Plugin)this);
        pm.registerEvents((Listener)new PlayerDeathListener(), (Plugin)this);
        pm.registerEvents((Listener)new ChatReaction(), (Plugin)this);
        pm.registerEvents((Listener)new PlayerClickOnPlayer(), (Plugin)this);
        pm.registerEvents((Listener)new GateListener(), (Plugin)this);
        pm.registerEvents((Listener)new PlaceCrops(), (Plugin)this);
    }
    
    private void LoadEnchantments() {
        try {
            try {
                final Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Enchantment.registerEnchantment((Enchantment)this.bg);
            }
            catch (IllegalArgumentException ex) {}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void UnloadEnchantments() {
        try {
            final Field byIdField = Enchantment.class.getDeclaredField("byId");
            final Field byNameField = Enchantment.class.getDeclaredField("byName");
            byIdField.setAccessible(true);
            byNameField.setAccessible(true);
            final HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>)byNameField.get(null);
            this.unloadEnchNAME(this.bg.getName(), byName);
        }
        catch (Exception ex) {}
    }
    
    private void unloadEnchNAME(final String name, final HashMap<Integer, Enchantment> map) {
        if (map.containsKey(name)) {
            map.remove(name);
        }
    }
    
    private void unloadEnchantment(final String by, final Enchantment ench) {
        try {
            final Field f = Enchantment.class.getDeclaredField(by);
            f.setAccessible(true);
            final HashMap<Integer, Enchantment> hField = (HashMap<Integer, Enchantment>)f.get(null);
            if (by.equals("byId")) {
                if (hField.containsKey(ench.getKey())) {
                    hField.remove(ench.getKey());
                }
            }
            else if (by.equals("byName") && hField.containsKey(ench.getName())) {
                hField.remove(ench.getName());
            }
        }
        catch (Exception ex) {}
    }
}
