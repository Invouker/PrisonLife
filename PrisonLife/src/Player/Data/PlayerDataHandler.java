// 
// Decompiled by Procyon v0.5.36
// 

package Player.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Chat;
import Main.Main;
import Main.MySQL;

public class PlayerDataHandler
{
    Main main;
    MySQL sql;
    private static HashMap<Player, PlayerData> playerData;
    
    static {
        PlayerDataHandler.playerData = new HashMap<Player, PlayerData>();
    }
    
    public PlayerDataHandler() {
        this.main = Main.getInstance();
        this.sql = new MySQL("player_data");
    }
    
    public void addPlayer(final Player p) {
        if (!this.hasData(p)) {
            PlayerDataHandler.playerData.put(p, new PlayerData(p));
        }
    }
    
    public void removePlayer(final Player p) {
        if (this.hasData(p)) {
            PlayerDataHandler.playerData.remove(p);
        }
    }
    
    public boolean hasData(final Player p) {
        return PlayerDataHandler.playerData.containsKey(p);
    }
    
    public void createFirstTimeConnect(final Player p) {
        this.addPlayer(p);
        this.loadData(p);
    }
    
    public PlayerData getData(final Player p) {
        if (this.hasData(p)) {
            return PlayerDataHandler.playerData.get(p);
        }
        this.addPlayer(p);
        return PlayerDataHandler.playerData.get(p);
    }
    
    public void dataSynchronizing() {
        new BukkitRunnable() {
            public void run() {
                for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p != null) {
                        PlayerDataHandler.this.saveData(p);
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 10L, 6000L);
    }
    
    public void loadData(final Player p) {
        if (!this.hasData(p)) {
            try {
                this.getData(p).setName(this.getSQLData(p).getString("NAME"));
                this.getData(p).setUUID(this.getSQLData(p).getString("UUID"));
                this.getData(p).setPrisoner(this.getSQLData(p).getInt("Prisoner"));
                this.getData(p).setWC(this.getSQLData(p).getInt("Wc"));
                this.getData(p).setHygiene(this.getSQLData(p).getInt("Hygiene"));
                this.getData(p).setSleep(this.getSQLData(p).getInt("Sleep"));
                this.getData(p).setThirst(this.getSQLData(p).getInt("Thirst"));
                this.getData(p).setStrength(this.getSQLData(p).getInt("Strength"));
                this.getData(p).setStrengthExp(this.getSQLData(p).getInt("Strength_Experience"));
                this.getData(p).setStamina(this.getSQLData(p).getInt("Stamina"));
                this.getData(p).setStaminaExp(this.getSQLData(p).getInt("Stamina_Experience"));
                this.getData(p).setLuck(this.getSQLData(p).getInt("Luck"));
                this.getData(p).setCraftingSkill(this.getSQLData(p).getInt("Crafting_Skill"));
                this.getData(p).setLangString(this.getSQLData(p).getString("Lang"));
                this.getData(p).setLVL(this.getSQLData(p).getInt("Level"));
                this.getData(p).setXP(this.getSQLData(p).getInt("Experience"));
                this.getData(p).setPoints(this.getSQLData(p).getInt("Points"));
                this.getData(p).setMoney(this.getSQLData(p).getInt("Money"));
                this.getData(p).setCoins(this.getSQLData(p).getInt("Coins"));
                this.getData(p).setPremiumInt(this.getSQLData(p).getInt("Premium"));
                this.getData(p).PremiumSetLong(this.getSQLData(p).getInt("Premium_toDate"));
                this.getData(p).setSolitary(this.getSQLData(p).getInt("solitary_toDate"));
                this.sql.disconnect();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveData(final Player p) {
        if (this.hasData(p)) {
            this.setData(p, "NAME", this.getData(p).getName());
            this.setData(p, "UUID", this.getData(p).getUUID());
            this.setData(p, "Prisoner", this.getData(p).getPrisoner());
            this.setData(p, "Wc", this.getData(p).getWC());
            this.setData(p, "Hygiene", this.getData(p).getHygiene());
            this.setData(p, "Sleep", this.getData(p).getSleep());
            this.setData(p, "Thirst", this.getData(p).getThirst());
            this.setData(p, "Strength", this.getData(p).getStrength());
            this.setData(p, "Strength_Experience", this.getData(p).getStrengthExp());
            this.setData(p, "Stamina", this.getData(p).getStamina());
            this.setData(p, "Stamina_Experience", this.getData(p).getStaminaExp());
            this.setData(p, "Luck", this.getData(p).getLuck());
            this.setData(p, "Crafting_Skill", this.getData(p).getCraftingSkill());
            this.setData(p, "Lang", this.getData(p).getLang().getInShort());
            this.setData(p, "Level", this.getData(p).getLVL());
            this.setData(p, "Experience", this.getData(p).getXP());
            this.setData(p, "Points", this.getData(p).getPoints());
            this.setData(p, "Money", this.getData(p).getMoney());
            this.setData(p, "Coins", this.getData(p).getCoins());
            this.setData(p, "Premium", this.getData(p).getPremium().getType());
            this.setData(p, "Premium_toDate", this.getData(p).getHowLongPremium());
            this.setData(p, "solitary_toDate", this.getData(p).getSolitary());
        }
    }
    
    public void reloadData(final Player p) {
        this.saveData(p);
        this.loadData(p);
    }
    
    private void setData(final Player p, final String Path, final Object data) {
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("UPDATE " + this.sql.getTable() + " SET " + Path + "=? WHERE UUID=?");
            if (this.checkType(data) == null) {
                Chat.print("TYPE: " + this.checkType(data) + " PATH: " + Path + " OBJECT: " + data);
                return;
            }
            if (this.checkType(data).equalsIgnoreCase("string") || Path.equalsIgnoreCase("UUID")) {
                statement.setString(1, (String)data);
            }
            else if (this.checkType(data).equalsIgnoreCase("int")) {
                statement.setInt(1, (int)data);
            }
            else if (this.checkType(data).equalsIgnoreCase("double")) {
                statement.setDouble(1, (double)data);
            }
            else if (this.checkType(data).equalsIgnoreCase("float")) {
                statement.setFloat(1, (float)data);
            }
            else if (this.checkType(data).equalsIgnoreCase("long")) {
                statement.setLong(1, (long)data);
            }
            else {
                Chat.print("ERROR! DATA: " + data);
            }
            statement.setString(2, p.getPlayer().getUniqueId().toString());
            this.sql.QuaryUpdate(statement);
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Chat.print("§cPlayer: §7" + p.getName() + " §cPATH:§7 " + Path + " §cDATA:§7 " + data);
        }
    }
    
    private ResultSet getSQLData(final Player p) {
        ResultSet rSet = null;
        try {
            this.sql.connect();
            final PreparedStatement statement = this.sql.getConnection().prepareStatement("SELECT * FROM " + this.sql.getTable() + " WHERE UUID=?");
            statement.setString(1, p.getPlayer().getUniqueId().toString());
            final ResultSet results = this.sql.Quary(statement);
            results.next();
            rSet = results;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rSet;
    }
    
    public <T> String checkType(final T object) {
        if (object instanceof Integer) {
            return "int";
        }
        if (object instanceof Double) {
            return "double";
        }
        if (object instanceof Float) {
            return "float";
        }
        if (object instanceof String) {
            return "string";
        }
        if (object instanceof Long) {
            return "long";
        }
        return null;
    }
}
