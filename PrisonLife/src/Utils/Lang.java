// 
// Decompiled by Procyon v0.5.36
// 

package Utils;

public enum Lang
{
    SLOVAKIA("SLOVAKIA", 0, 1, "Slovensko", "Slovakia", "SK"), 
    CZECH("CZECH", 1, 2, "\u010cesko", "Czech", "CZ"), 
    ENGLISH("ENGLISH", 2, 3, "Anglicky", "English", "EN");
    
    private int id;
    private String name;
    private String enName;
    private String inShort;
    
    private Lang(final String name2, final int ordinal, final int id, final String name, final String enName, final String inShort) {
        this.id = id;
        this.name = name;
        this.enName = enName;
        this.inShort = inShort;
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getEnName() {
        return this.enName;
    }
    
    public String getInShort() {
        return this.inShort;
    }
}
