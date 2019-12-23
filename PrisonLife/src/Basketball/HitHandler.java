// 
// Decompiled by Procyon v0.5.36
// 

package Basketball;

import java.util.HashSet;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import Main.ZoneVector;
import Main.Chat;
import org.bukkit.GameMode;
import Main.PrisonType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import Player.Data.PlayerDataHandler;
import Main.Main;
import org.bukkit.event.Listener;

public class HitHandler implements Listener
{
    Main plugin;
    PlayerDataHandler pdh;
    private static Player player;
    
    static {
        HitHandler.player = null;
    }
    
    public HitHandler() {
        this.plugin = Main.getInstance();
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void onBallHit(final EntityDamageByEntityEvent e) {
        final Entity ent = e.getEntity();
        final Entity damager = e.getDamager();
        if (damager instanceof Player) {
            Player p = (Player)damager;
            if (ent instanceof Slime) {
                final Slime s = (Slime)ent;
                s.setAI(true);
                final Location speedLoc = s.getLocation().subtract(p.getLocation());
                final Vector vector = new Vector(speedLoc.getX(), speedLoc.getY(), speedLoc.getZ()).normalize().multiply(0.48);
                vector.setY(0.85);
                new BukkitRunnable() {
                    public void run() {
                        s.setVelocity(vector);
                    }
                }.runTaskLater((Plugin)Main.getInstance(), 2L);
                p = HitHandler.player;
                if (this.pdh.getData(p).getType() != PrisonType.ADMIN || p.getGameMode() != GameMode.CREATIVE) {
                    e.setDamage(0.0);
                    e.setCancelled(true);
                }
                else {
                    Chat.info(p, "You killed a BALL ( Slime )!");
                    s.setHealth(0.0);
                }
                this.onBasketToWeb((Player)e.getDamager(), s);
                new BukkitRunnable() {
                    public void run() {
                        final Location sLoc = s.getLocation();
                        if (!ZoneVector.contains(sLoc, -399.0, 85.0, 154.0, -429.0, 77.0, 168.0)) {
                            final Vector zoneVec = vector;
                            zoneVec.normalize().setY(0.3);
                            s.setVelocity(vector.multiply(1.5).multiply(-1));
                        }
                        new BukkitRunnable() {
                            public void run() {
                                if (!ZoneVector.contains(s.getLocation(), -399.0, 85.0, 154.0, -429.0, 77.0, 168.0)) {
                                    s.setHealth(0.0);
                                    final EntitiyBall ball = new EntitiyBall();
                                    ball.createBall(-414, 77, 161);
                                    Chat.print("Vytv\u00e1ram nov\u00fa loptu!2");
                                }
                            }
                        }.runTaskLater((Plugin)Main.getInstance(), 10L);
                    }
                }.runTaskLater((Plugin)Main.getInstance(), 8L);
            }
        }
    }
    
    public void onBasketToWeb(final Player p, final Slime s) {
        new BukkitRunnable() {
            public void run() {
                if (s == null || p == null) {
                    this.cancel();
                }
                if (HitHandler.this.inWeb((Entity)s)) {
                    Entity[] nearbyEntities;
                    for (int length = (nearbyEntities = HitHandler.getNearbyEntities(s.getLocation(), 20)).length, i = 0; i < length; ++i) {
                        final Entity en = nearbyEntities[i];
                        if (en instanceof Player) {
                            Chat.info(p, "Hr\u00e1\u010d " + p.getName() + " dal k\u00f4\u0161! Sk\u00fas ho da\u0165 aj ty!");
                        }
                    }
                    final EntitiyBall ball = new EntitiyBall();
                    s.setHealth(0.0);
                    Chat.print("Vytv\u00e1ram nov\u00fa loptu!");
                    ball.createBall(-414, 77, 161);
                }
            }
        }.runTaskLater((Plugin)Main.getInstance(), 20L);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void NoTarget(final EntityTargetEvent e) {
        if (e.getEntityType() == EntityType.SLIME && e.getEntity().getName().equals("Ball")) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void fallDamage(final EntityDamageEvent e) {
        final Entity ball = e.getEntity();
        if (ball.getType() == EntityType.SLIME && ball.getName().equalsIgnoreCase("Ball")) {
            e.setDamage(0.0);
        }
    }
    
    public boolean inWeb(final Entity en) {
        return en.getLocation().getBlock() != null && (en.getLocation().getBlock().getType() == Material.COBWEB || (en.getLocation().getBlock().getRelative(BlockFace.UP).getType() != null && en.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.COBWEB));
    }
    
    public static Entity[] getNearbyEntities(final Location l, final int radius) {
        final int chunkRadius = (radius < 16) ? 1 : ((radius - radius % 16) / 16);
        final HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; ++chX) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; ++chZ) {
                final int x = (int)l.getX();
                final int y = (int)l.getY();
                final int z = (int)l.getZ();
                Entity[] entities;
                for (int length = (entities = new Location(l.getWorld(), (double)(x + chX * 16), (double)y, (double)(z + chZ * 16)).getChunk().getEntities()).length, i = 0; i < length; ++i) {
                    final Entity e = entities[i];
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
}
