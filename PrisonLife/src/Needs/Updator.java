// 
// Decompiled by Procyon v0.5.36
// 

package Needs;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import java.util.Random;
import org.bukkit.plugin.Plugin;
import Main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import Main.Chat;
import Main.ZoneVector;
import Main.PrisonType;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class Updator implements Listener
{
    PlayerDataHandler pdh;
    ArrayList<Player> pl;
    ArrayList<Player> showers;
    BukkitTask updater;
    
    public Updator() {
        this.pdh = new PlayerDataHandler();
        this.pl = new ArrayList<Player>();
        this.showers = new ArrayList<Player>();
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (this.pl.contains(p)) {
            this.pl.remove(p);
        }
        else if (this.showers.contains(p)) {
            this.showers.remove(p);
        }
    }
    
    @EventHandler
    public void enterShower(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.getGameMode().equals((Object)GameMode.SURVIVAL) && this.pdh.getData(p).getType() == PrisonType.PRISONER) {
            if (ZoneVector.contains(p.getLocation(), -414.0, 84.0, 259.0, -431.0, 76.0, 281.0)) {
                if (!this.showers.contains(p)) {
                    this.showers.add(p);
                    Chat.send(p, "hygiene.enter-shower", new String[0]);
                }
            }
            else if (this.showers.contains(p)) {
                this.showers.remove(p);
            }
            if (ZoneVector.contains(p.getLocation(), -428.0, 80.0, 275.0, -428.0, 76.0, 279.0) || ZoneVector.contains(p.getLocation(), -428.0, 80.0, 261.0, -428.0, 76.0, 265.0) || ZoneVector.contains(p.getLocation(), -426.0, 80.0, 265.0, -437.0, 85.0, 251.0) || ZoneVector.contains(p.getLocation(), -426.0, 80.0, 279.0, -426.0, 76.0, 275.0) || ZoneVector.contains(p.getLocation(), -419.0, 80.0, 261.0, -419.0, 76.0, 265.0) || ZoneVector.contains(p.getLocation(), -417.0, 80.0, 265.0, -417.0, 76.0, 261.0) || ZoneVector.contains(p.getLocation(), -417.0, 80.0, 275.0, -417.0, 76.0, 279.0)) {
                if (!this.pl.contains(p)) {
                    this.pl.add(p);
                    if (this.pdh.getData(p).getHygiene() < 100) {
                        this.updateHygiene(p);
                    }
                }
            }
            else if (this.pl.contains(p)) {
                this.pl.remove(p);
                this.updater.cancel();
            }
        }
    }
    
    public void updateHygiene(final Player p) {
        this.updater = new BukkitRunnable() {
            public void run() {
                if (Updator.this.pdh.getData(p).getHygiene() < 100) {
                    Updator.this.pdh.getData(p).giveHygiene(1);
                }
                else {
                    this.cancel();
                    Updator.this.pdh.getData(p).setHygiene(100);
                    Chat.send(p, "hygiene.full-showered", new String[0]);
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 15L);
    }
    
    public int loseHygiene(final Player p) {
        int hyg = this.pdh.getData(p).getHygiene();
        if (hyg > 0) {
            final Random rand = new Random();
            hyg -= rand.nextInt(3);
            this.pdh.getData(p).setHygiene(hyg);
        }
        else {
            this.pdh.getData(p).giveRespect(-1);
        }
        return hyg;
    }
    
    @EventHandler
    public void onBedEnter(final PlayerBedLeaveEvent e) {
        final Player p = e.getPlayer();
        if (this.pdh.getData(e.getPlayer()).getType() == PrisonType.PRISONER) {
            this.pdh.getData(p).setSleep(100);
            Chat.send(p, "sleep.out-from-bed", new String[0]);
        }
    }
    
    public int loseSleep(final Player p) {
        int sleep = this.pdh.getData(p).getSleep();
        if (sleep > 0) {
            --sleep;
            this.pdh.getData(p).setSleep(sleep);
        }
        else {
            this.sleepy(p);
        }
        return sleep;
    }
    
    public void sleepy(final Player p) {
        final Random rand = new Random();
        final int random = rand.nextInt(6);
        if (random == 2) {
            new BukkitRunnable() {
                public void run() {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 320, 1));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 760, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 740, 3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 1));
                }
            }.runTaskLater((Plugin)Main.getInstance(), 20L);
        }
    }
    
    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (this.pdh.getData(p).getSleep() != 0) {
                if (this.pdh.getData(p).getSleep() / 2 <= 5) {
                    final double damage = this.pdh.getData(p).getSleep() / e.getDamage();
                    p.sendMessage("SET DAMAGE IS: §c" + damage);
                    e.setDamage(damage);
                }
            }
            else {
                e.setDamage(0.0);
            }
        }
    }
    
    @EventHandler
    public void onPlayerItemConsumeEvent(final PlayerItemConsumeEvent e) {
        final Player p = e.getPlayer();
        if (e.getItem() != null && e.getItem().getItemMeta() != null) {
            final PotionType potionType = ((PotionMeta)e.getItem().getItemMeta()).getBasePotionData().getType();
            if (potionType == PotionType.WATER) {
                final ItemStack is = e.getItem();
                if (is.getItemMeta().getDisplayName().equals("§bBotte Of Water §f§l0.1L")) {
                    if (this.pdh.getData(p).getThirst() + 15 > 100) {
                        e.setCancelled(true);
                        return;
                    }
                    this.pdh.getData(p).giveThirst(15);
                    Chat.send(p, "thirst.drink-water", "0.1L", "15%");
                }
                else if (is.getItemMeta().getDisplayName().equals("§bBotte Of Water §f§l0.3L")) {
                    if (this.pdh.getData(p).getThirst() + 50 > 100) {
                        e.setCancelled(true);
                        return;
                    }
                    this.pdh.getData(p).giveThirst(50);
                    Chat.send(p, "thirst.drink-water", "0.3L", "50%");
                }
                else if (is.getItemMeta().getDisplayName().equals("§bBotte Of Water §f§l0.5L")) {
                    if (this.pdh.getData(p).getThirst() + 75 > 100) {
                        e.setCancelled(true);
                        return;
                    }
                    this.pdh.getData(p).giveThirst(75);
                    Chat.send(p, "thirst.drink-water", "0.5L", "75%");
                }
            }
        }
    }
    
    public int loseThirst(final Player p) {
        int thirst = this.pdh.getData(p).getThirst();
        if (thirst > 0) {
            --thirst;
            this.pdh.getData(p).setThirst(thirst);
        }
        else {
            final double health = p.getHealth();
            if (health >= 1.0) {
                p.setHealth(health - 0.5);
            }
        }
        return thirst;
    }
    
    public void wet(final Player p) {
    }
    
    public void createHelix(final Player player) {
        final Location loc = player.getLocation();
        final int radius = 2;
        for (double y = 0.0; y <= 50.0; y += 0.05) {
            final double x = radius * Math.cos(y);
            final double z = radius * Math.sin(y);
            for (Player player2 : Bukkit.getOnlinePlayers()) {}
        }
    }
    
    public int loseWC(final Player p) {
        int wc = this.pdh.getData(p).getWC();
        if (wc >= 1) {
            final Random rand = new Random();
            wc -= rand.nextInt(3);
            this.pdh.getData(p).setWC(wc);
        }
        else {
            this.wet(p);
            if (this.pdh.getData(p).getRespect() >= 1) {
                this.pdh.getData(p).giveRespect(-1);
            }
        }
        return wc;
    }
}
