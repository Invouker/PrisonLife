// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import Main.Chat;
import Main.PlayerFreezing;

public class JoinBoxListener implements Listener
{
    BoxHandler bh;
    
    public JoinBoxListener() {
        this.bh = new BoxHandler();
    }
    
    @EventHandler
    public void onInter(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK) || e.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) {
            final Block b = e.getClickedBlock();
            if (b.getType().equals((Object)Material.OAK_SIGN) || b.getType().equals((Object)Material.OAK_WALL_SIGN)) {
                final int x = e.getClickedBlock().getLocation().getBlockX();
                final int y = e.getClickedBlock().getLocation().getBlockY();
                final int z = e.getClickedBlock().getLocation().getBlockZ();
                final int box = this.getClickedBox(x, y, z);
                if (box > 0) {
                    if (BoxData.getBox(box).getName1().equals("- NONE -")) {
                        BoxData.getBox(box).setName1(p.getName());
                        final Location loc = BoxData.getBox(box).getPort_1();
                        if (loc == null) {
                            return;
                        }
                        e.getPlayer().teleport(loc);
                        PlayerFreezing.addPlayer(p, false);
                        p.setGameMode(GameMode.SURVIVAL);
                        updateSign(box);
                        final BoxManager bm = new BoxManager();
                        bm.counterBox(box);
                        Chat.info(p, "Pre opustenie boxu pou\u017ei pr\u00edkaz /box left");
                    }
                    else if (BoxData.getBox(box).getName2().equals("- NONE -")) {
                        BoxData.getBox(box).setName2(p.getName());
                        final Location loc = BoxData.getBox(box).getPort_2();
                        if (loc == null) {
                            return;
                        }
                        e.getPlayer().teleport(loc);
                        PlayerFreezing.addPlayer(p, false);
                        p.setGameMode(GameMode.SURVIVAL);
                        updateSign(box);
                        final BoxManager bm = new BoxManager();
                        bm.counterBox(box);
                        Chat.info(p, "Pre opustenie boxu pou\u017ei pr\u00edkaz /box left");
                    }
                }
            }
        }
    }
    
    public int getClickedBox(final int sX, final int sY, final int sZ) {
        int cell = -1;
        for (int id = 0; id <= BoxData.boxes.size() - 1; ++id) {
            if (BoxData.getBox(id) != null && BoxData.getBox(id).getSignLoc() != null) {
                final int x = BoxData.getBox(id).getSignLoc().getBlockX();
                final int y = BoxData.getBox(id).getSignLoc().getBlockY();
                final int z = BoxData.getBox(id).getSignLoc().getBlockZ();
                if (sX == x && sY == y && sZ == z) {
                    cell = id;
                    break;
                }
            }
        }
        return cell;
    }
    
    public static void updateSign(final int box) {
        if (BoxData.getBox(box) == null) {
            return;
        }
        final int x = BoxData.getBox(box).getSignLoc().getBlockX();
        final int y = BoxData.getBox(box).getSignLoc().getBlockY();
        final int z = BoxData.getBox(box).getSignLoc().getBlockZ();
        final World w = BoxData.getBox(box).getSignLoc().getWorld();
        final Block b = w.getBlockAt(x, y, z);
        final BlockState state = b.getState();
        if (!(state instanceof Sign)) {
            return;
        }
        final Sign s = (Sign)state;
        if (b.getType().equals((Object)Material.OAK_SIGN) || b.getType().equals((Object)Material.OAK_WALL_SIGN)) {
            s.setLine(1, BoxData.getBox(box).getName1());
            s.setLine(3, BoxData.getBox(box).getName2());
            s.update();
        }
    }
    
    public int anyEmpty(final int box) {
        int empty = 0;
        final String name1 = BoxData.getBox(box).getName1();
        final String name2 = BoxData.getBox(box).getName2();
        if (name1.equalsIgnoreCase("- NONE -")) {
            empty = 1;
        }
        else if (name2.equalsIgnoreCase("- NONE -")) {
            empty = 2;
        }
        return empty;
    }
}
