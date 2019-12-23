// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import org.bukkit.Location;

public class ZoneVector
{
    protected double x;
    protected double y;
    protected double z;
    
    public ZoneVector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean isInZone(final ZoneVector min, final ZoneVector max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }
    
    public static boolean contains(final Location loc, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final ZoneVector curr = new ZoneVector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        final ZoneVector min = new ZoneVector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        final ZoneVector max = new ZoneVector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
        return curr.isInZone(min, max);
    }
}
