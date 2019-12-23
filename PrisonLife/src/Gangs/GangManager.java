// 
// Decompiled by Procyon v0.5.36
// 

package Gangs;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Main.Main;

public class GangManager
{
    private static HashMap<Player, Integer> cooldown;
    private static ArrayList<BukkitTask> updaters;
    private static HashMap<Player, String> gangInvites;
    
    static {
        GangManager.cooldown = new HashMap<Player, Integer>();
        GangManager.updaters = new ArrayList<BukkitTask>();
        GangManager.gangInvites = new HashMap<Player, String>();
    }
    
    public static void removeHolograms() {
        for (final BukkitTask tasks : GangManager.updaters) {
            tasks.cancel();
        }
    }
    
    public static boolean playerIsInGang(final Player p) {
        for (final Gang data : GangDataManager.getgData()) {
            for (final String s : data.getPlayers()) {
                if (s.equalsIgnoreCase(p.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static HashMap<Player, String> getGangInvites() {
        return GangManager.gangInvites;
    }
    
    public static boolean hasInvite(final Player p) {
        return GangManager.gangInvites.containsKey(p);
    }
    
    public static boolean hasCooldown(final Player p) {
        return GangManager.cooldown.containsKey(p);
    }
    
    public void createCooldown(final Player p, final int time) {
        if (!hasCooldown(p)) {
            GangManager.cooldown.put(p, time);
        }
    }
    
    public static boolean playerOwnGang(final Player p) {
        for (final Gang data : GangDataManager.getgData()) {
            if (p.getName().equals(data.getFounder())) {
                return true;
            }
        }
        return false;
    }
    
    public static Gang getGangFromName(final String name) {
        for (final Gang data : GangDataManager.getgData()) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }
    
    public static int getGangNumber(final Player p) {
        if (GangDataManager.getgData() != null) {
            for (int i = 0; i < GangDataManager.getgData().size(); ++i) {
                if (p.getName().equals(GangDataManager.getgData().get(i).getFounder())) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    public static void updateCooldown() {
        new BukkitRunnable() {
            public void run() {
                if (!GangManager.cooldown.isEmpty()) {
                    for (final Player p : GangManager.cooldown.keySet()) {
                        int time = GangManager.cooldown.get(p);
                        if (--time <= 0) {
                            GangManager.cooldown.remove(p);
                        }
                        else {
                            GangManager.cooldown.put(p, time);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    
    public void spawnGangHolograms() {
        for (final Gang data : GangDataManager.getgData()) {
            this.createGangHologram(data);
        }
    }
    
    public void createGangHologram(final Gang data) {
    }
}
