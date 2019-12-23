// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.Ranks;

public enum GangRanks
{
    NON_MEMBER("NON_MEMBER", 0, "\u017diadny \u010dlen", "NoMember"), 
    MEMBER("MEMBER", 1, "\u010clen", "Member"), 
    ACTIVE_MEMBER("ACTIVE_MEMBER", 2, "Aktivn\u00fd \u010dlen", "ActiveMember"), 
    DEALER("DEALER", 3, "D\u00edler", "Dealer"), 
    MODERATOR("MODERATOR", 4, "Moder\u00e1tor", "Moderator"), 
    FOUNDER("FOUNDER", 5, "Zakladate\u013e", "Founder");
    
    private String displayName;
    private String saveName;
    
    private GangRanks(final String name2, final int ordinal, final String name, final String saveName) {
        this.displayName = name;
        this.setSaveName(saveName);
    }
    
    public String getName() {
        return this.displayName;
    }
    
    public GangRanks getRank(final String name) {
        if (name.equalsIgnoreCase("NoMember")) {
            return GangRanks.NON_MEMBER;
        }
        if (name.equalsIgnoreCase("Member")) {
            return GangRanks.MEMBER;
        }
        if (name.equalsIgnoreCase("ActiveMember")) {
            return GangRanks.ACTIVE_MEMBER;
        }
        if (name.equalsIgnoreCase("Dealer")) {
            return GangRanks.DEALER;
        }
        if (name.equalsIgnoreCase("Moderator")) {
            return GangRanks.MODERATOR;
        }
        if (name.equalsIgnoreCase("Founder")) {
            return GangRanks.FOUNDER;
        }
        return null;
    }
    
    public String getSaveName() {
        return this.saveName;
    }
    
    public void setSaveName(final String saveName) {
        this.saveName = saveName;
    }
}
