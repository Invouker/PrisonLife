// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import Main.Chat;
import Main.PrisonType;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import Player.Data.PlayerDataHandler;
import org.bukkit.event.Listener;

public class DoorListener implements Listener
{
    PlayerDataHandler pdh;
    
    public DoorListener() {
        this.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        final Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block.getType() != Material.SPRUCE_DOOR) {
            return;
        }
        MaterialData data = block.getState().getData();
        if (!(data instanceof Door)) {
            return;
        }
        Door door = (Door)data;
        if (door.isTopHalf()) {
            final Block onTopDoor = block.getRelative(BlockFace.UP);
            if (onTopDoor.getType() != Material.BLUE_WOOL) {
                return;
            }
            block = block.getRelative(BlockFace.DOWN);
            data = block.getState().getData();
            if (!(data instanceof Door)) {
                return;
            }
            door = (Door)data;
            if (door.isTopHalf()) {
                return;
            }
        }
        else {
            final Block topHalf = block.getRelative(BlockFace.UP);
            final Block onTopDoor2 = topHalf.getRelative(BlockFace.UP);
            if (onTopDoor2.getType() != Material.BLUE_WOOL) {
                return;
            }
        }
        if (this.pdh.getData(p).getType() == PrisonType.PRISONER) {
            Chat.send(p, "only-warden-can-use", new String[0]);
            e.setCancelled(true);
            return;
        }
        final Location playerLocation = p.getLocation();
        final float yaw = playerLocation.getYaw();
        BlockFace destinationFace = null;
        switch (door.getFacing()) {
            case NORTH:
            case SOUTH: {
                destinationFace = ((yaw > 90.0f && yaw <= 270.0f) ? BlockFace.NORTH : BlockFace.SOUTH);
                break;
            }
            case EAST:
            case WEST: {
                destinationFace = ((yaw > 0.0f && yaw <= 180.0f) ? BlockFace.WEST : BlockFace.EAST);
                break;
            }
            default: {
                return;
            }
        }
        final Location destinationLoc = block.getRelative(destinationFace).getLocation().add(0.5, 0.0, 0.5);
        destinationLoc.setYaw(playerLocation.getYaw());
        destinationLoc.setPitch(playerLocation.getPitch());
        p.teleport(destinationLoc);
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }
}
