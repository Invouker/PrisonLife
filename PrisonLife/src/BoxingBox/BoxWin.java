// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import Main.Chat;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class BoxWin implements Listener
{
    PlayerDataHandler pdh;
    
    public BoxWin() {
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER && e.getEntity().getType() == EntityType.PLAYER) {
            final Player damaged = (Player)e.getEntity();
            final Player damager = (Player)e.getDamager();
            final int box1 = getPlayerBox(damaged);
            final int box2 = getPlayerBox(damager);
            if ((box1 != -1 || box2 != -1) && box1 == box2) {
                final int box3 = box1;
                if (damaged.getHealth() - e.getDamage() <= 1.0) {
                    e.setCancelled(true);
                    final int x = (int)BoxData.getBox(box3).getSignLoc().getX();
                    final int y = (int)BoxData.getBox(box3).getSignLoc().getY() - 1;
                    final int z = (int)BoxData.getBox(box3).getSignLoc().getZ();
                    final String world = BoxData.getBox(box3).getSignLoc().getWorld().getName();
                    final Location loc = new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z);
                    damaged.teleport(loc);
                    damager.teleport(loc);
                    this.pdh.getData(damager).giveRespect(4);
                    if (this.pdh.getData(damaged).getRespect() >= 2) {
                        this.pdh.getData(damaged).giveRespect(-2);
                    }
                    damager.setHealth(20.0);
                    damaged.setHealth(20.0);
                    Chat.sendAll("box.win", damager.getName());
                    BoxData.getBox(box3).setName1("- NONE -");
                    BoxData.getBox(box3).setName2("- NONE -");
                    JoinBoxListener.updateSign(box3);
                }
            }
        }
    }
    
    public void onKill(final PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = e.getEntity();
        final int box = getPlayerBox(p);
        if (box != -1) {
            final String name1 = BoxData.getBox(box).getName1();
            final String name2 = BoxData.getBox(box).getName2();
            if (name1.equals(p.getName())) {
                e.setKeepInventory(true);
                Chat.sendAll("box.win", name2);
                final Player pWin = Bukkit.getServer().getPlayer(name2);
                this.pdh.getData(pWin).giveRespect(4);
                if (this.pdh.getData(p).getRespect() >= 2) {
                    this.pdh.getData(p).giveRespect(-2);
                    p.spigot().respawn();
                }
            }
            else if (name2.equals(p.getName())) {
                e.setKeepInventory(true);
                Chat.sendAll("box.win", name1);
                final Player pWin = Bukkit.getServer().getPlayer(name1);
                if (pWin != null) {
                    this.pdh.getData(pWin).giveRespect(4);
                }
                if (this.pdh.getData(p).getRespect() >= 2) {
                    this.pdh.getData(p).giveRespect(-2);
                    p.spigot().respawn();
                }
            }
            final int x = (int)BoxData.getBox(box).getSignLoc().getX();
            final int y = (int)BoxData.getBox(box).getSignLoc().getY() - 1;
            final int z = (int)BoxData.getBox(box).getSignLoc().getZ();
            final String world = BoxData.getBox(box).getSignLoc().getWorld().getName();
            final Location loc = new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z);
            Bukkit.getServer().getPlayer(name1).teleport(loc);
            Bukkit.getServer().getPlayer(name2).teleport(loc);
            BoxData.getBox(box).setName1("- NONE -");
            BoxData.getBox(box).setName2("- NONE -");
            JoinBoxListener.updateSign(box);
        }
    }
    
    public static int getPlayerBox(final Player p) {
        for (int i = 0; i <= 24; ++i) {
            final String name1 = BoxData.getBox(i).getName1();
            final String name2 = BoxData.getBox(i).getName2();
            if (p.getName().equalsIgnoreCase(name1) || p.getName().equalsIgnoreCase(name2)) {
                return i;
            }
        }
        return -1;
    }
}
