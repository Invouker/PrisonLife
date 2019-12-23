// 
// Decompiled by Procyon v0.5.36
// 

package Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class ChooseTeamEvent extends Event implements Cancellable
{
    private final Player p;
    private int ClickedType;
    private boolean isCancelled;
    private PrisonType type;
    private static final HandlerList HANDLERS;
    PlayerDataHandler pdh;
    
    static {
        HANDLERS = new HandlerList();
    }
    
    public ChooseTeamEvent(final Player p, final int type) {
        this.pdh = new PlayerDataHandler();
        this.p = p;
        this.ClickedType = type;
        if (PrisonType.PRISONER.getID() == type) {
            this.type = PrisonType.PRISONER;
        }
        if (PrisonType.WARDEN.getID() == type) {
            this.type = PrisonType.WARDEN;
        }
        if (PrisonType.ADMIN.getID() == type) {
            this.type = PrisonType.ADMIN;
        }
        this.isCancelled = false;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    public int getClickedTypeS() {
        return this.ClickedType;
    }
    
    public PrisonType getType() {
        return this.type;
    }
    
    public PrisonType getTeam() {
        return this.pdh.getData(this.p).getType();
    }
    
    public int getNumberPrisoner() {
        return this.pdh.getData(this.p).getPrisoner();
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    public void setCancelled(final boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    
    public static HandlerList getHandlerList() {
        return ChooseTeamEvent.HANDLERS;
    }
    
    public HandlerList getHandlers() {
        return ChooseTeamEvent.HANDLERS;
    }
}
