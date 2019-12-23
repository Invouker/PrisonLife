// 
// Decompiled by Procyon v0.5.36
// 

package Abilities;

import org.bukkit.entity.Player;

import Main.Chat;
import Player.Data.PlayerDataHandler;

public class StrengthLevel
{
    PlayerDataHandler pdh;
    
    public StrengthLevel() {
        this.pdh = new PlayerDataHandler();
    }
    
    public void updateExp(final Player p) {
        while (this.pdh.getData(p).getStrengthExp() >= this.toLevel(p)) {
            this.pdh.getData(p).giveStrengthExp(-this.toLevel(p));
            this.pdh.getData(p).giveStrength(1);
            Chat.info(p, "Updatovan\u00fd level... §b" + this.toLevel(p));
        }
    }
    
    public int toLevel(final Player p) {
        return 81 * this.pdh.getData(p).getStrength() + 26;
    }
}
