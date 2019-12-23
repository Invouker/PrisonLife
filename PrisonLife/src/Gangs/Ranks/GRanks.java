// 
// Decompiled by Procyon v0.5.36
// 

package Gangs.Ranks;

import org.bukkit.entity.Player;
import Gangs.Gang;

public interface GRanks
{
    boolean hasPermission(final Gang p0, final String p1);
    
    boolean canInvite(final Player p0);
    
    boolean canOpenGangSettings(final Player p0);
    
    GangRanks getRank();
}
