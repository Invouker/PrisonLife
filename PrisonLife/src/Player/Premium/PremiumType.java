// 
// Decompiled by Procyon v0.5.36
// 

package Player.Premium;

public enum PremiumType
{
    NOVIP("NOVIP", 0, 0), 
    MASTER("MASTER", 1, 1), 
    LEGION("LEGION", 2, 2);
    
    private int type;
    
    public int getType() {
        return this.type;
    }
    
    private PremiumType(final String name, final int ordinal, final int i) {
        this.type = i;
    }
}
