// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.Ranks;

import Gangs.Gang;
import org.bukkit.entity.Player;

public class Founder implements GRanks
{
    @Override
    public boolean canInvite(final Player p) {
        return true;
    }
    
    @Override
    public GangRanks getRank() {
        return GangRanks.FOUNDER;
    }
    
    @Override
    public boolean canOpenGangSettings(final Player p) {
        return true;
    }
    
    @Override
    public boolean hasPermission(final Gang gang, final String name) {
        return false;
    }
}
