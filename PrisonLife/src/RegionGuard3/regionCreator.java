// 
// Decompiled by Procyon v0.5.36
// 

package RegionGuard3;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import Main.Chat;
import Main.PlayerDataListener;

public class regionCreator implements Listener, CommandExecutor
{
    PlayerDataListener pd;
    private static Location rightClick;
    private static Location leftClick;
    
    public regionCreator() {
        this.pd = new PlayerDataListener();
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (p.getGameMode().equals((Object)GameMode.CREATIVE) || this.pd.getIData(p, "Class") == 3) {
            if (p.hasPermission("Prison.Setup")) {
                if (e.getItem() != null && e.getItem().getType().equals((Object)Material.WOODEN_SWORD) && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals("§c§l§nRegion Creator")) {
                    if (e.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK)) {
                        regionCreator.rightClick = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                        Chat.info(p, "§6Right Click (X: " + regionCreator.rightClick.getX() + ", Y: " + regionCreator.rightClick.getY() + " Z: " + regionCreator.rightClick.getZ() + ").");
                    }
                    else if (e.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) {
                        regionCreator.leftClick = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                        Chat.info(p, "§6Left Click (X: " + regionCreator.leftClick.getX() + ", Y: " + regionCreator.leftClick.getY() + " Z: " + regionCreator.leftClick.getZ() + ").");
                    }
                }
            }
            else {
                Chat.noPerm(p);
            }
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("region")) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                Chat.send(p, "§c/region §fcreate <name>", new String[0]);
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (args[1].length() != 0) {
                    if (regionCreator.leftClick.length() == 0.0 || regionCreator.rightClick.length() == 0.0) {
                        return false;
                    }
                    if (!regionCreator.leftClick.getWorld().equals(regionCreator.rightClick.getWorld())) {
                        return false;
                    }
                    final Insert_SQL sql = new Insert_SQL();
                    sql.createRegion(p, regionCreator.rightClick.getBlockX(), regionCreator.rightClick.getBlockY(), regionCreator.rightClick.getBlockZ(), regionCreator.leftClick.getBlockX(), regionCreator.leftClick.getBlockY(), regionCreator.leftClick.getBlockZ(), regionCreator.rightClick.getWorld());
                    Chat.info(p, "");
                    Chat.info(p, "Admin has been created new region !");
                    Chat.info(p, " " + p.getName() + " " + regionCreator.rightClick.getBlockX() + " " + regionCreator.rightClick.getBlockY() + " " + regionCreator.rightClick.getBlockZ() + " " + regionCreator.leftClick.getBlockX() + " " + regionCreator.leftClick.getBlockY() + " " + regionCreator.leftClick.getBlockZ());
                }
                else {
                    Chat.send(p, "§c/region §fcreate <name>", new String[0]);
                }
            }
        }
        return false;
    }
}
