// 
// Decompiled by Procyon v0.5.36
// 

package Abilities;

import Main.Chat;
import org.bukkit.entity.Player;
import Player.Data.PlayerDataHandler;

public class StaminaLevel
{
    PlayerDataHandler pdh;
    
    public StaminaLevel() {
        this.pdh = new PlayerDataHandler();
    }
    
    public void updateExp(final Player p) {
        while (this.pdh.getData(p).getStamina() >= this.toLevel(p)) {
            this.pdh.getData(p).giveStaminaExp(-this.toLevel(p));
            this.pdh.getData(p).giveStamina(1);
            Chat.info(p, "Updatovan\u00fd level... §b" + this.toLevel(p));
        }
    }
    
    public int toLevel(final Player p) {
        return 35 * this.pdh.getData(p).getStamina() + 17;
    }
}
