// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class Gate
{
    private ArrayList<Block> blocks;
    private ArrayList<Block> buttons;
    private String id;
    private boolean isOpened;
    
    public Gate(final String id, final ArrayList<Block> blocks, final ArrayList<Block> buttons) {
        this.blocks = new ArrayList<Block>();
        this.buttons = new ArrayList<Block>();
        this.id = id;
        this.blocks = blocks;
        for (final Block b : buttons) {
            if (b.getType().toString().contains("_BUTTON")) {
                this.buttons.add(b);
            }
        }
    }
    
    public boolean canButtonInteract(final Player p) {
        final PlayerDataHandler pdh = new PlayerDataHandler();
        return pdh.getData(p).getType() == PrisonType.WARDEN || pdh.getData(p).getType() == PrisonType.ADMIN;
    }
    
    public ArrayList<Block> getBlocks() {
        return this.blocks;
    }
    
    public ArrayList<Block> getButtons() {
        return this.buttons;
    }
    
    public boolean isOpened() {
        return this.isOpened;
    }
    
    public void setOpened(final boolean isOpened) {
        this.isOpened = isOpened;
    }
    
    public String getId() {
        return this.id;
    }
}
