// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.block.data.Openable;
import org.bukkit.Location;
import Main.Main;
import org.bukkit.Bukkit;
import Cells.CellDataProvider;

public class NightDoorControl implements DoorControls
{
    CellDataProvider cd;
    
    public NightDoorControl() {
        this.cd = new CellDataProvider();
    }
    
    @Override
    public void updateDoorState() {
        final World w = Bukkit.getServer().getWorld(Main.mainWorld);
        for (int i = 0; i < 144; ++i) {
            final int doorX = this.cd.getICell(i, "Door.X");
            final int doorY = this.cd.getICell(i, "Door.Y");
            final int doorZ = this.cd.getICell(i, "Door.Z");
            final Location doorLoc = new Location(w, (double)doorX, (double)doorY, (double)doorZ);
            if (doorX != 0 || doorY != 0 || doorZ != 0) {
                final Block b = w.getBlockAt(doorLoc);
                if (b != null) {
                    final BlockData data = b.getBlockData();
                    final Openable openable = (Openable)data;
                    openable.setOpen(false);
                    this.playDoorSound(b, true);
                    b.setBlockData(data);
                    b.getState().update();
                }
            }
        }
    }
    
    private void playDoorSound(final Block b, final boolean c) {
        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);
    }
}
