// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.itemnbtapi.NBTItem;

public class NBTBuilder
{
    private ItemStack is;
    
    public NBTBuilder(final ItemStack is) {
        this.is = is;
    }
    
    public NBTBuilder setString(final String key, final String value) {
        final NBTItem nbt = new NBTItem(this.is);
        nbt.setString(key, value);
        return this;
    }
    
    public NBTBuilder setInteger(final String key, final int value) {
        final NBTItem nbt = new NBTItem(this.is);
        nbt.setInteger(key, Integer.valueOf(value));
        return this;
    }
    
    public NBTBuilder setFloat(final String key, final float value) {
        final NBTItem nbt = new NBTItem(this.is);
        nbt.setFloat(key, Float.valueOf(value));
        return this;
    }
    
    public String getString(final String key) {
        final NBTItem nbt = new NBTItem(this.is);
        return nbt.getString(key);
    }
    
    public int getInteger(final String key) {
        final NBTItem nbt = new NBTItem(this.is);
        return nbt.getInteger(key);
    }
    
    public float getFloat(final String key) {
        final NBTItem nbt = new NBTItem(this.is);
        return nbt.getFloat(key);
    }
    
    public ItemStack build() {
        final NBTItem nbt = new NBTItem(this.is);
        return nbt.getItem();
    }
}
