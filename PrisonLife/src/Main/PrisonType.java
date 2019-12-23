// 
// Decompiled by Procyon v0.5.36
// 

package Main;

public enum PrisonType
{
    LOBBY("LOBBY", 0, -1, "Lobby"), 
    PRISONER("PRISONER", 1, 1, "Vezen"), 
    WARDEN("WARDEN", 2, 2, "Dozorca"), 
    ADMIN("ADMIN", 3, 3, "Admin");
    
    private int id;
    private String name;
    
    public int getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    private PrisonType(final String name2, final int ordinal, final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
