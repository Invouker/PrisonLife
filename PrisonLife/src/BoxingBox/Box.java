// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.Location;

public class Box
{
    private Location signLoc;
    private Location port_1;
    private Location port_2;
    private String name1;
    private String name2;
    public static final String DEFAULT_NAME = "- NONE -";
    private String world;
    
    public Box() {
        this.name1 = "- NONE -";
        this.name2 = "- NONE -";
    }
    
    public Location getPort_1() {
        return this.port_1;
    }
    
    public void setPort_1(final Location port_1) {
        this.port_1 = port_1;
    }
    
    public Location getPort_2() {
        return this.port_2;
    }
    
    public void setPort_2(final Location port_2) {
        this.port_2 = port_2;
    }
    
    public String getName1() {
        return this.name1;
    }
    
    public void setName1(final String name1) {
        this.name1 = name1;
    }
    
    public String getName2() {
        return this.name2;
    }
    
    public void setName2(final String name2) {
        this.name2 = name2;
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public void setWorld(final String world) {
        this.world = world;
    }
    
    public Location getSignLoc() {
        return this.signLoc;
    }
    
    public void setSignLoc(final Location signLoc) {
        this.signLoc = signLoc;
    }
}
