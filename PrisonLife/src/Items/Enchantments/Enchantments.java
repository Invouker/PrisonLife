// 
// Decompiled by Procyon v0.5.36
// 

package Items.Enchantments;

public enum Enchantments
{
    BOXING_GLOVES("BOXING_GLOVES", 0, 85), 
    RANDOM("RANDOM", 1, 2);
    
    private int chance;
    
    public int getChance() {
        return this.chance;
    }
    
    private Enchantments(final String name, final int ordinal, final int i) {
        this.chance = i;
    }
}
