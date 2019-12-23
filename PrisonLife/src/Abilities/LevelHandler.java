// 
// Decompiled by Procyon v0.5.36
// 

package Abilities;

import Main.Chat;
import org.bukkit.entity.Player;
import Player.Data.PlayerDataHandler;

public class LevelHandler
{
    int maxLevel;
    PlayerDataHandler pdh;
    
    public LevelHandler() {
        this.maxLevel = 95;
        this.pdh = new PlayerDataHandler();
    }
    
    public void updater(final Player p) {
        if (this.pdh.getData(p).getLVL() == 0) {
            this.pdh.getData(p).setLVL(1);
        }
        if (this.pdh.getData(p).getLVL() < this.maxLevel) {
            while (this.pdh.getData(p).getXP() >= this.xpToLevel(p)) {
                if (this.pdh.getData(p).getXP() > 500000) {
                    Chat.info(p, "Error ! Please repport it to Administrator !");
                    Chat.print(Chat.getException(1, 46, this.getClass()));
                    this.pdh.getData(p).setXP(1);
                    break;
                }
                if (this.pdh.getData(p).getLVL() > this.maxLevel) {
                    continue;
                }
                this.pdh.getData(p).giveXP(-this.xpToLevel(p));
                this.pdh.getData(p).giveLVL(1);
                p.sendTitle("§b§lLevel Up", "§dYou reached " + String.valueOf(this.pdh.getData(p).getLVL()) + " level.");
            }
        }
    }
    
    public int xpToLevel(final Player p) {
        int var = 0;
        if (this.pdh.getData(p).getLVL() <= 15) {
            var = 17 * this.pdh.getData(p).getLVL() * 11;
        }
        else if (this.pdh.getData(p).getLVL() <= 30) {
            var = (int)(1.5 * this.pdh.getData(p).getLVL() * this.pdh.getData(p).getLVL() - 29.5 * this.pdh.getData(p).getLVL() + 360.0) * 14;
        }
        else {
            var = (int)(3.5 * this.pdh.getData(p).getLVL() * this.pdh.getData(p).getLVL() - 151.5 * this.pdh.getData(p).getLVL() + 2220.0) * 3;
        }
        return var;
    }
}
