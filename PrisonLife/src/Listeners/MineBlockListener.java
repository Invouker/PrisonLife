// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import Utils.Functions;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitTask;
import Player.Data.PlayerDataHandler;
import Abilities.LevelHandler;
import org.bukkit.event.Listener;

public class MineBlockListener implements Listener
{
    LevelHandler lh;
    PlayerDataHandler pdh;
    BukkitTask blockUpdater;
    
    public MineBlockListener() {
        this.lh = new LevelHandler();
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        final Block b = e.getBlock();
        switch (b.getType()) {
            case COAL_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(0, 2));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case IRON_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(0, 3));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case LAPIS_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(0, 4));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case REDSTONE_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(1, 5));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case GOLD_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(2, 6));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case DIAMOND_ORE: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(3, 7));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
            case QUARTZ: {
                e.setDropItems(false);
                this.pdh.getData(p).giveXP(Functions.random(4, 10));
                final Location loc = b.getLocation();
                this.breakingBlock(loc);
                loc.setY((double)loc.getBlockY());
                this.deleyPlace(loc, Material.COBBLESTONE);
                break;
            }
        }
    }
    
    public void deleyPlace(final Location loc, final Material m) {
        new BukkitRunnable() {
            public void run() {
                final Block b = loc.getWorld().getBlockAt(loc);
                b.setType(m);
            }
        }.runTaskLater((Plugin)Main.getInstance(), 20L);
    }
    
    public void breakingBlock(final Location loc) {
        this.blockUpdater = new BukkitRunnable() {
            public void run() {
                final Block b = loc.getWorld().getBlockAt(loc);
                if (b.getType().equals((Object)Material.MOSSY_COBBLESTONE)) {
                    MineBlockListener.this.setBlock(loc, false);
                    this.cancel();
                    return;
                }
                if (b.getType().equals((Object)Material.COBBLESTONE)) {
                    b.setType(Material.MOSSY_COBBLESTONE);
                    return;
                }
                if (b.getType().equals((Object)Material.AIR)) {
                    b.setType(Material.COBBLESTONE);
                    return;
                }
                if (b.getType().toString().contains("_ORE")) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 900L, 500L);
    }
    
    public void setBlock(final Location loc, final boolean disableMossy) {
        final Block b = loc.getWorld().getBlockAt(loc);
        final int random = Functions.random(1, 240);
        if (Functions.rangeOf(1, 8, random)) {
            b.setType(Material.QUARTZ);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(9, 20, random)) {
            b.setType(Material.DIAMOND_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(21, 39, random)) {
            b.setType(Material.GOLD_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(40, 65, random)) {
            b.setType(Material.REDSTONE_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(66, 105, random)) {
            b.setType(Material.LAPIS_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(106, 151, random)) {
            b.setType(Material.IRON_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(152, 210, random)) {
            b.setType(Material.COAL_ORE);
            this.blockUpdater.cancel();
        }
        if (Functions.rangeOf(211, 240, random)) {
            if (disableMossy) {
                b.setType(Material.COAL_ORE);
            }
            else {
                b.setType(Material.MOSSY_COBBLESTONE);
            }
        }
    }
}
