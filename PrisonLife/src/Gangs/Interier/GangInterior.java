// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.Interier;

public enum GangInterior
{
    GARAGE("GARAGE", 0, "Gar\u00e1\u017e", "Gar\u00e1\u017e", "GARAGE", 0), 
    SMALL_INTERIOR("SMALL_INTERIOR", 1, "Mal\u00fd interi\u00e9r", "Mal\u00fd Interier", "SMALL INTERIOR", 10), 
    MEDIUM_INTERIOR("MEDIUM_INTERIOR", 2, "Mal\u00fd interi\u00e9r", "Mal\u00fd Interier", "SMALL INTERIOR", 10), 
    BIG_INTERIOR("BIG_INTERIOR", 3, "Mal\u00fd interi\u00e9r", "Mal\u00fd Interier", "SMALL INTERIOR", 10);
    
    private String cz;
    private String sk;
    private String en;
    private int cost;
    
    private GangInterior(final String name, final int ordinal, final String cz, final String sk, final String en, final int cost) {
        this.cz = cz;
        this.sk = sk;
        this.en = en;
        this.cost = cost;
    }
    
    public String getNameCZ() {
        return this.cz;
    }
    
    public String getNameSK() {
        return this.sk;
    }
    
    public String getNameEN() {
        return this.en;
    }
    
    public int getCost() {
        return this.cost;
    }
}
