// 
// Decompiled by Procyon v0.5.36
// 

package Items;

public enum ItemMaterial
{
    BOXING_GLOVES("BOXING_GLOVES", 0, "Boxing Gloves");
    
    private String name;
    
    @Override
    public String toString() {
        return this.name;
    }
    
    private ItemMaterial(final String name2, final int ordinal, final String name) {
        this.name = name;
    }
}
