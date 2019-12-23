// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import Main.Chat;
import Main.Main;

public class DoorControler implements DoorControls
{
    private ArrayList<Gate> gates;
    public static boolean isClosed;
    private static boolean isDay;
    
    static {
        DoorControler.isClosed = true;
        DoorControler.isDay = false;
    }
    
    public ArrayList<Gate> getGates() {
        return this.gates;
    }
    
    public DoorControler(final boolean day) {
        this.gates = new ArrayList<Gate>();
        DoorControler.isDay = day;
        final World w = Bukkit.getWorld(Main.mainWorld);
        this.registerGate("A", w, new Location(w, -475.0, 77.0, 259.0), new Location(w, -477.0, 79.0, 259.0), new Location(w, -474.0, 77.0, 258.0), new Location(w, -478.0, 77.0, 261.0));
        this.registerGate("B", w, new Location(w, -481.0, 77.0, 250.0), new Location(w, -481.0, 79.0, 253.0), new Location(w, -480.0, 77.0, 253.0), new Location(w, -482.0, 77.0, 250.0));
        this.registerGate("C", w, new Location(w, -470.0, 77.0, 243.0), new Location(w, -470.0, 79.0, 246.0), new Location(w, -469.0, 77.0, 247.0), new Location(w, -471.0, 77.0, 242.0));
        this.registerGate("D", w, new Location(w, -470.0, 77.0, 227.0), new Location(w, -470.0, 79.0, 230.0), new Location(w, -471.0, 77.0, 226.0), new Location(w, -469.0, 77.0, 231.0));
        this.registerGate("E", w, new Location(w, -508.0, 77.0, 255.0), new Location(w, -508.0, 79.0, 257.0), new Location(w, -510.0, 77.0, 254.0), new Location(w, -506.0, 78.0, 259.0));
        this.registerGate("F", w, new Location(w, -475.0, 77.0, 224.0), new Location(w, -477.0, 79.0, 224.0), new Location(w, -477.0, 77.0, 225.0), new Location(w, -475.0, 77.0, 223.0));
        this.registerGate("G", w, new Location(w, -433.0, 77.0, 269.0), new Location(w, -433.0, 79.0, 271.0), new Location(w, -432.0, 77.0, 272.0), new Location(w, -434.0, 77.0, 268.0));
        this.registerGate("T", w, new Location(w, -400.0, 77.0, 203.0), new Location(w, -403.0, 80.0, 203.0), new Location(w, -398.0, 77.0, 201.0), new Location(w, -405.0, 77.0, 205.0));
        this.registerGate("S", w, new Location(w, -426.0, 77.0, 222.0), new Location(w, -426.0, 79.0, 225.0), new Location(w, -427.0, 77.0, 222.0), new Location(w, -425.0, 77.0, 225.0));
    }
    
    public void registerGate(final String id, final World w, final Location min, final Location max, final Location... buttonsLoc) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        final ArrayList<Block> buttons = new ArrayList<Block>();
        for (int x = Math.max(max.getBlockX(), min.getBlockX()); x >= Math.min(min.getBlockX(), max.getBlockX()); --x) {
            for (int y = Math.max(max.getBlockY(), min.getBlockY()); y >= Math.min(min.getBlockY(), max.getBlockY()); --y) {
                for (int z = Math.max(max.getBlockZ(), min.getBlockZ()); z >= Math.min(min.getBlockZ(), max.getBlockZ()); --z) {
                    final Block b = w.getBlockAt(x, y, z);
                    blocks.add(b);
                }
            }
        }
        if (buttons != null) {
            for (final Location loc : buttonsLoc) {
                final Block button = w.getBlockAt(loc);
                buttons.add(button);
            }
        }
        final Gate gate = new Gate(id, blocks, buttons);
        this.gates.add(gate);
    }
    
    public void fillBlock(final World world, final Material mat, final Location min, final Location max) {
        for (int x = Math.max(max.getBlockX(), min.getBlockX()); x >= Math.min(min.getBlockX(), max.getBlockX()); --x) {
            for (int y = Math.max(max.getBlockY(), min.getBlockY()); y >= Math.min(min.getBlockY(), max.getBlockY()); --y) {
                for (int z = Math.max(max.getBlockZ(), min.getBlockZ()); z >= Math.min(min.getBlockZ(), max.getBlockZ()); --z) {
                    final Block block = world.getBlockAt(x, y, z);
                    block.setType(mat);
                }
            }
        }
    }
    
    @Override
    public void updateDoorState() {
        if (DoorControler.isDay) {
            this.day();
            DoorControler.isDay = true;
        }
        else {
            DoorControler.isDay = false;
            this.night();
        }
    }
    
    public void controlGate(final String id, final boolean open) {
        for (final Gate gate : this.gates) {
            if (gate.getId().equals(id)) {
                if (!gate.isOpened()) {
                    final Material mat = Material.AIR;
                    for (final Block b : gate.getBlocks()) {
                        b.setType(mat);
                        b.getState().update();
                    }
                    gate.setOpened(true);
                    return;
                }
                if (!gate.isOpened()) {
                    continue;
                }
                final Material mat = Material.IRON_BARS;
                for (final Block b : gate.getBlocks()) {
                    b.setType(mat);
                    b.getState().update();
                }
                gate.setOpened(true);
            }
        }
    }
    
    public void controlAllGate(final boolean open) {
        for (final Gate gate : this.gates) {
            for (final Block b : gate.getBlocks()) {
                if (open) {
                    b.setType(Material.AIR);
                    gate.setOpened(true);
                    DoorControler.isClosed = true;
                }
                else {
                    b.setType(Material.IRON_BARS);
                    gate.setOpened(false);
                    DoorControler.isClosed = false;
                }
                b.getState().update();
            }
        }
    }
    
    private void day() {
        this.controlAllGate(true);
        Chat.print("For now is a morning!");
    }
    
    private void night() {
        this.controlAllGate(false);
        Chat.print("For now is a evening!");
    }
}
