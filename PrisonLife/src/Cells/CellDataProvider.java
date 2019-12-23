// 
// Decompiled by Procyon v0.5.36
// 

package Cells;

import org.bukkit.inventory.ItemStack;
import Main.Main;

public class CellDataProvider
{
    public void setSCell(final int cell, final String path, final String data) {
        Main.getCell().set("Cells." + cell + "." + path, (Object)data);
        this.save();
    }
    
    public void setICell(final int cell, final String path, final int data) {
        Main.getCell().set("Cells." + cell + "." + path, (Object)data);
        this.save();
    }
    
    public void setBCell(final int cell, final String path, final boolean data) {
        Main.getCell().set("Cells." + cell + "." + path, (Object)data);
        this.save();
    }
    
    public void setDCell(final int cell, final String path, final double data) {
        Main.getCell().set("Cells." + cell + "." + path, (Object)data);
        this.save();
    }
    
    public void setISCell(final int cell, final String path, final ItemStack data) {
        Main.getCell().set("Cells." + cell + "." + path, (Object)data);
        this.save();
    }
    
    public String getSCell(final int cell, final String path) {
        return Main.getCell().getString("Cells." + cell + "." + path);
    }
    
    public int getICell(final int cell, final String path) {
        return Main.getCell().getInt("Cells." + cell + "." + path);
    }
    
    public boolean getBCell(final int cell, final String path) {
        return Main.getCell().getBoolean("Cells." + cell + "." + path);
    }
    
    public double getDCell(final int cell, final String path) {
        return Main.getCell().getDouble("Cells." + cell + "." + path);
    }
    
    public ItemStack getISCell(final int cell, final String path) {
        return Main.getCell().getItemStack("Cells." + cell + "." + path);
    }
    
    public void save() {
        Main.getCell().save();
    }
}
