// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Main.Chat;
import Main.Main;
import Player.Data.PlayerDataHandler;

public class SolitaryControl
{
    BukkitTask updater;
    PlayerDataHandler pdh;
    public static boolean onDisable;
    
    static {
        SolitaryControl.onDisable = true;
    }
    
    public SolitaryControl() {
        this.pdh = new PlayerDataHandler();
    }
    
    public void addPlayer(final int solitary, final Player p, final long time, final String reason) {
        if (this.getPlayerSolitary(p) == -1) {
            final double x = solitaryCache.getD(solitary, solitaryCache.pos_X);
            final double y = solitaryCache.getD(solitary, solitaryCache.pos_Y);
            final double z = solitaryCache.getD(solitary, solitaryCache.pos_Z);
            final String world = solitaryCache.getS(solitary, solitaryCache.world);
            solitaryCache.setS(solitary, reason, solitaryCache.reason);
            solitaryCache.setL(solitary, time, solitaryCache.toDate);
            solitaryCache.setS(solitary, p.getName(), solitaryCache.name);
            this.timer(solitary, p, 20);
            final World w = Bukkit.getServer().getWorld(world);
            final Location loc = new Location(w, x, y, z).add(0.5, 0.0, 0.5);
            p.teleport(loc);
            solitaryCache.setS(solitary, p.getName(), solitaryCache.name);
            this.updateSign(solitary);
            solitaryCache.save(solitary);
        }
        else {
            Chat.send(p, "admin.playerIsInSoltary", p.getName());
        }
    }
    
    private void timer(final int solitary, final Player p, final int updateTime) {
        this.updater = new BukkitRunnable() {
            public void run() {
                if (solitaryCache.getL(solitary, solitaryCache.toDate) > 0L) {
                    final long getTime = solitaryCache.getL(solitary, solitaryCache.toDate) - 1L;
                    solitaryCache.setL(solitary, getTime, solitaryCache.toDate);
                    SolitaryControl.this.updateAllSigns();
                }
                else {
                    SolitaryControl.this.removePlayer(p, true);
                    this.cancel();
                    SolitaryControl.this.updateAllSigns();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, (long)updateTime);
    }
    
    public void removePlayer(final Player p, final boolean teleport) {
        for (int i = 1; i < 10; ++i) {
            final String pName = solitaryCache.getS(i, solitaryCache.name);
            if (pName.equals(p.getName())) {
                solitaryCache.setS(i, "None", solitaryCache.reason);
                solitaryCache.setL(i, 0L, solitaryCache.toDate);
                solitaryCache.setS(i, "None", solitaryCache.name);
                if (teleport) {
                    p.teleport(new Location(Bukkit.getWorld(Main.mainWorld), -486.0, 77.0, 252.0));
                    this.pdh.getData(p).setSolitary(0L);
                }
                solitaryCache.save(i);
                break;
            }
        }
        this.updateAllSigns();
    }
    
    public int getPlayerSolitary(final Player p) {
        for (int i = 1; i < 10; ++i) {
            final String pName = solitaryCache.getS(i, solitaryCache.name);
            if (pName != null && pName.equals(p.getName())) {
                return i;
            }
        }
        return -1;
    }
    
    public int firstEmpty() {
        int returned = -1;
        if (solitaryCache.name.isEmpty()) {
            return returned;
        }
        for (int i = 1; i < solitaryCache.name.size(); ++i) {
            if (solitaryCache.getS(i, solitaryCache.name).equalsIgnoreCase("NONE")) {
                returned = i;
                break;
            }
        }
        return returned;
    }
    
    public void updateSign(final int solitary) {
        final String name = solitaryCache.getS(solitary, solitaryCache.name);
        final String reason = solitaryCache.getS(solitary, solitaryCache.reason);
        final long toDate = solitaryCache.getL(solitary, solitaryCache.toDate);
        String cutReason = "";
        if (reason != null && reason.length() >= 15) {
            cutReason = reason.substring(0, 14);
            cutReason = String.valueOf(cutReason) + "...";
        }
        else {
            cutReason = reason;
        }
        final int x = solitaryCache.getI(solitary, solitaryCache.sign_X);
        final int y = solitaryCache.getI(solitary, solitaryCache.sign_Y);
        final int z = solitaryCache.getI(solitary, solitaryCache.sign_Z);
        final String world = solitaryCache.getS(solitary, solitaryCache.world);
        World w = Bukkit.getServer().getWorld(world);
        if (w == null || world == null) {
            Chat.print("Error with loading  WORLD!");
            w = Bukkit.getServer().getWorld(Main.mainWorld);
        }
        final Block block = w.getBlockAt(x, y, z);
        final BlockState state = block.getState();
        if (!(state instanceof Sign)) {
            return;
        }
        final Sign s = (Sign)state;
        if (name != null && !name.equalsIgnoreCase("NONE")) {
            s.setLine(0, "§8§lSolitary " + solitary);
            s.setLine(1, name);
            s.setLine(2, cutReason);
            s.setLine(3, "§8" + this.convertTimeToString(toDate));
            block.getState().update();
        }
        else {
            s.setLine(0, "§8§lSolitary " + solitary);
            s.setLine(1, "None");
            s.setLine(2, "No reason");
            s.setLine(3, "");
            block.getState().update();
        }
        this.update(s);
    }
    
    private String convertTimeToString(final long time) {
        final long hours = time / 3600L;
        final long minutes = time % 3600L / 60L;
        final long seconds = time % 60L;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    public void updateAllSigns() {
        for (int size = 10, i = 1; i < size; ++i) {
            this.updateSign(i);
        }
    }
    
    private void update(final Sign s) {
        if (SolitaryControl.onDisable) {
        	new BukkitRunnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					s.update();
				}
        		
        	}.runTask(Main.getInstance());
            
        }
    }
}
