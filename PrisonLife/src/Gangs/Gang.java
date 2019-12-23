// 
// Decompiled by Procyon v0.5.36
// 

package Gangs;

import Gangs.Ranks.GangRanks;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;

public class Gang
{
    private String name;
    private String tag;
    private String founder;
    private Location loc;
    private int kills;
    private int deaths;
    private int respects;
    private List<String> players;
    private HashMap<String, GangRanks> playersRanks;
    
    public Gang(final String gangName) {
        this.name = gangName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public String getFounder() {
        return this.founder;
    }
    
    public void setFounder(final String founder) {
        this.founder = founder;
    }
    
    public List<String> getPlayers() {
        return this.players;
    }
    
    public void setPlayers(final List<String> list) {
        this.players = list;
    }
    
    public HashMap<String, GangRanks> getPlayersRanks() {
        return this.playersRanks;
    }
    
    public void setPlayersRanks(final HashMap<String, GangRanks> playerRanks) {
        this.playersRanks = playerRanks;
    }
    
    public int getKills() {
        return this.kills;
    }
    
    public void setKills(final int kills) {
        this.kills = kills;
    }
    
    public int getDeaths() {
        return this.deaths;
    }
    
    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }
    
    public int getRespects() {
        return this.respects;
    }
    
    public void setRespects(final int respects) {
        this.respects = respects;
    }
    
    public Location getLoc() {
        return this.loc;
    }
    
    public void setLoc(final Location loc) {
        this.loc = loc;
    }
}
