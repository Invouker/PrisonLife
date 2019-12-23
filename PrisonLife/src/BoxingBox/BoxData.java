// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.World;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Location;
import Main.Main;
import org.bukkit.Bukkit;
import java.util.HashMap;

public class BoxData
{
    public static HashMap<Integer, Box> boxes;
    public static final int MAX_BOXES = 10;
    
    static {
        BoxData.boxes = new HashMap<Integer, Box>();
    }
    
    public static Box getBox(final int id) {
        if (BoxData.boxes.get(id) == null) {
            BoxData.boxes.put(id, new Box());
        }
        return BoxData.boxes.get(id);
    }
    
    public static void reloadConfiguration() {
        Bukkit.broadcastMessage("SIZE: " + BoxData.boxes.size() + "BOXES: " + BoxData.boxes);
        saveConfiguration();
        loadConfiguration();
        for (int id = 0; id < BoxData.boxes.size() - 1; ++id) {
            getBox(id).setName1("- NONE -");
            getBox(id).setName2("- NONE -");
        }
    }
    
    public static void loadConfiguration() {
        final Box box = new Box();
        final FileConfiguration cfg = (FileConfiguration)Main.getBoxes();
        if (cfg.getConfigurationSection("Boxes") == null) {
            return;
        }
        for (final String key : cfg.getConfigurationSection("Boxes").getKeys(false)) {
            double z = 0.0;
            String sWorld = null;
            World w = null;
            sWorld = cfg.getString("Boxes." + key + ".Sign.World");
            if (sWorld != null) {
                w = Bukkit.getWorld(sWorld);
                if (w == null) {
                    throw new NullPointerException("The world at Sign Box, cannot be null.");
                }
                double x = cfg.getDouble("Boxes." + key + ".Sign.X");
                double y = cfg.getDouble("Boxes." + key + ".Sign.Y");
                z = cfg.getDouble("Boxes." + key + ".Sign.Z");
                box.setSignLoc(new Location(w, x, y, z));
                sWorld = cfg.getString("Boxes." + key + ".Port_1.World");
                if (sWorld == null) {
                    continue;
                }
                w = Bukkit.getWorld(sWorld);
                if (w == null) {
                    throw new NullPointerException("The world at Port_1 Box, cannot be null.");
                }
                x = cfg.getDouble("Boxes." + key + ".Port_1.X");
                y = cfg.getDouble("Boxes." + key + ".Port_1.Y");
                z = cfg.getDouble("Boxes." + key + ".Port_1.Z");
                float yaw = (float)cfg.getDouble("Boxes." + key + ".Port_1.Yaw");
                final Location locPort1 = new Location(w, x, y, z);
                locPort1.setYaw(yaw);
                box.setPort_1(locPort1);
                sWorld = cfg.getString("Boxes." + key + ".Port_2.World");
                if (sWorld == null) {
                    continue;
                }
                w = Bukkit.getWorld(sWorld);
                if (w == null) {
                    throw new NullPointerException("The world at Port_2 Box, cannot be null.");
                }
                x = cfg.getDouble("Boxes." + key + ".Port_2.X");
                y = cfg.getDouble("Boxes." + key + ".Port_2.Y");
                z = cfg.getDouble("Boxes." + key + ".Port_2.Z");
                yaw = (float)cfg.getDouble("Boxes." + key + ".Port_2.Yaw");
                final Location locPort2 = new Location(w, x, y, z);
                locPort2.setYaw(yaw);
                box.setPort_2(locPort2);
                BoxData.boxes.put(Integer.valueOf(key), box);
            }
        }
    }
    
    public static void saveConfiguration() {
        final FileConfiguration cfg = (FileConfiguration)Main.getBoxes();
        for (int i = 0; i < BoxData.boxes.size() - 1; ++i) {
            final Box box = BoxData.boxes.get(i);
            if (box != null) {
                final Location signLoc = box.getSignLoc();
                final Location port_1 = box.getPort_1();
                final Location port_2 = box.getPort_2();
                if (signLoc != null) {
                    cfg.set("Boxes." + i + ".Sign.X", (Object)signLoc.getBlockX());
                    cfg.set("Boxes." + i + ".Sign.Y", (Object)signLoc.getBlockY());
                    cfg.set("Boxes." + i + ".Sign.Z", (Object)signLoc.getBlockZ());
                    cfg.set("Boxes." + i + ".Sign.World", (Object)signLoc.getWorld().getName());
                }
                if (port_1 != null) {
                    cfg.set("Boxes." + i + ".Port_1.X", (Object)port_1.getX());
                    cfg.set("Boxes." + i + ".Port_1.Y", (Object)port_1.getY());
                    cfg.set("Boxes." + i + ".Port_1.Z", (Object)port_1.getZ());
                    cfg.set("Boxes." + i + ".Port_1.Yaw", (Object)port_1.getYaw());
                    cfg.set("Boxes." + i + ".Port_1.World", (Object)port_1.getWorld().getName());
                }
                if (port_2 != null) {
                    cfg.set("Boxes." + i + ".Port_2.X", (Object)port_2.getX());
                    cfg.set("Boxes." + i + ".Port_2.Y", (Object)port_2.getY());
                    cfg.set("Boxes." + i + ".Port_2.Z", (Object)port_2.getZ());
                    cfg.set("Boxes." + i + ".Port_2.Yaw", (Object)port_2.getYaw());
                    cfg.set("Boxes." + i + ".Port_2.World", (Object)port_2.getWorld().getName());
                }
                Main.getBoxes().save();
            }
        }
    }
    
    public static void saveConfigurationOfBox(final int i) {
        final FileConfiguration cfg = (FileConfiguration)Main.getBoxes();
        final Box box = BoxData.boxes.get(i);
        if (box == null) {
            return;
        }
        final Location signLoc = box.getSignLoc();
        final Location port_1 = box.getPort_1();
        final Location port_2 = box.getPort_2();
        if (signLoc != null) {
            cfg.set("Boxes." + i + ".Sign.X", (Object)signLoc.getBlockX());
            cfg.set("Boxes." + i + ".Sign.Y", (Object)signLoc.getBlockY());
            cfg.set("Boxes." + i + ".Sign.Z", (Object)signLoc.getBlockZ());
            cfg.set("Boxes." + i + ".Sign.World", (Object)signLoc.getWorld().getName());
        }
        if (port_1 != null) {
            cfg.set("Boxes." + i + ".Port_1.X", (Object)port_1.getX());
            cfg.set("Boxes." + i + ".Port_1.Y", (Object)port_1.getY());
            cfg.set("Boxes." + i + ".Port_1.Z", (Object)port_1.getZ());
            cfg.set("Boxes." + i + ".Port_1.Yaw", (Object)port_1.getYaw());
            cfg.set("Boxes." + i + ".Port_1.World", (Object)port_1.getWorld().getName());
        }
        if (port_2 != null) {
            cfg.set("Boxes." + i + ".Port_2.X", (Object)port_2.getX());
            cfg.set("Boxes." + i + ".Port_2.Y", (Object)port_2.getY());
            cfg.set("Boxes." + i + ".Port_2.Z", (Object)port_2.getZ());
            cfg.set("Boxes." + i + ".Port_2.Yaw", (Object)port_2.getYaw());
            cfg.set("Boxes." + i + ".Port_2.World", (Object)port_2.getWorld().getName());
        }
        Main.getBoxes().save();
    }
}
