// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.Ranks;

import org.bukkit.entity.Player;

import Gangs.Gang;
import Gangs.GangDataManager;

public class Moderator implements GRanks
{
    private Gang gang;
    
    public Moderator(final Gang gang) {
        this.gang = gang;
    }
    
    @Override
    public boolean canInvite(final Player p) {
        this.hasPermission(this.gang, "");
        return false;
    }
    
    @Override
    public GangRanks getRank() {
        return GangRanks.MODERATOR;
    }
    
    @Override
    public boolean canOpenGangSettings(final Player p) {
        return false;
    }
    
    @Override
    public boolean hasPermission(final Gang gang, final String name) {
        final GangDataManager gdm = new GangDataManager();
        return gdm.getGangConfig().getBoolean("permissions." + gang.getName() + "." + this.getRank().getName() + "." + name);
    }
}
