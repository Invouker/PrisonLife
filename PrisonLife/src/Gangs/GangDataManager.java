// 
// Decompiled by Procyon v0.5.36
// 

package Gangs;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import Main.Chat;
import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Iterator;
import Gangs.Ranks.GangRanks;
import java.util.ArrayList;
import Main.Configs;

public class GangDataManager
{
    Configs config;
    private static ArrayList<Gang> gData;
    
    static {
        GangDataManager.gData = new ArrayList<Gang>();
    }
    
    public GangDataManager() {
        this.config = new Configs("gangs");
    }
    
    public static ArrayList<Gang> getgData() {
        return GangDataManager.gData;
    }
    
    public void saveData() {
        if (GangDataManager.gData.size() <= 0) {
            return;
        }
        for (int i = 0; i < GangDataManager.gData.size(); ++i) {
            final Gang data = GangDataManager.gData.get(i);
            final String name = data.getName();
            this.config.getFile().set("Gangs." + name + ".founder", (Object)data.getFounder());
            this.config.getFile().set("Gangs." + name + ".respect", (Object)data.getRespects());
            this.config.getFile().set("Gangs." + name + ".kills", (Object)data.getKills());
            this.config.getFile().set("Gangs." + name + ".deaths", (Object)data.getDeaths());
            this.config.getFile().set("Gangs." + name + ".loc.X", (Object)data.getLoc().getX());
            this.config.getFile().set("Gangs." + name + ".loc.Y", (Object)data.getLoc().getY());
            this.config.getFile().set("Gangs." + name + ".loc.Z", (Object)data.getLoc().getZ());
            this.config.getFile().set("Gangs." + name + ".loc.World", (Object)data.getLoc().getWorld().getName());
            this.config.getFile().set("Gangs." + name + ".tag", (Object)data.getTag());
            this.config.getFile().set("Gangs." + name + ".players", (Object)data.getPlayers());
            this.config.getFile().set("Gangs." + name + ".playersRanks", (Object)data.getPlayersRanks());
            if (data.getPlayersRanks() != null && !data.getPlayersRanks().isEmpty()) {
                for (final String player : data.getPlayersRanks().keySet()) {
                    this.config.getFile().set("Gangs." + name + ".PlayersRanks." + player, (Object)data.getPlayersRanks().get(player).getName());
                }
            }
            this.config.save();
        }
    }
    
    public static boolean gangExist(final String name) {
        for (final Gang data : GangDataManager.gData) {
            if (data.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public FileConfiguration getGangConfig() {
        return this.config.getFile();
    }
    
    public void saveGangConfig() {
        this.config.save();
    }
    
    public void autoSave() {
        new BukkitRunnable() {
            public void run() {
                GangDataManager.this.saveData();
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 6000L);
    }
    
    public void loadData() {
        try {
            if (this.config.getFile().getConfigurationSection("Gangs").getKeys(false) == null) {
                return;
            }
        }
        catch (NullPointerException ex) {
            return;
        }
        for (final String s : this.config.getFile().getConfigurationSection("Gangs").getKeys(false)) {
            if (gangExist(s)) {
                Chat.print("§cTento gang u\u017e je na\u010d\u00edtan\u00fd!");
                return;
            }
            final Gang data = new Gang(s);
            data.setFounder(this.config.getFile().getString("Gangs." + s + ".founder"));
            data.setRespects(this.config.getFile().getInt("Gangs." + s + ".respect"));
            data.setKills(this.config.getFile().getInt("Gangs." + s + ".kills"));
            data.setDeaths(this.config.getFile().getInt("Gangs." + s + ".deaths"));
            final double x = this.config.getFile().getDouble("Gangs." + s + ".loc.X");
            final double y = this.config.getFile().getDouble("Gangs." + s + ".loc.Y");
            final double z = this.config.getFile().getDouble("Gangs." + s + ".loc.Z");
            final String world = this.config.getFile().getString("Gangs." + s + ".loc.World");
            final Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            data.setLoc(loc);
            data.setTag(this.config.getFile().getString("Gangs." + s + ".tag"));
            final GangManager gm = new GangManager();
            gm.createGangHologram(data);
            try {
                data.setPlayers(this.config.getFile().getStringList("Gangs." + s + ".players"));
                for (final String player : this.config.getFile().getConfigurationSection("Gangs." + s + ".PlayersRanks").getKeys(false)) {
                    data.getPlayersRanks().put(player, GangRanks.MEMBER.getRank(this.config.getFile().getString("Gangs." + s + ".PlayersRanks")));
                }
            }
            catch (NullPointerException ex2) {}
            GangDataManager.gData.add(data);
        }
    }
}
