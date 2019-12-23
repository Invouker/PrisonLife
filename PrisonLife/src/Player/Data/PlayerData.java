// 
// Decompiled by Procyon v0.5.36
// 

package Player.Data;

import org.bukkit.Bukkit;
import Utils.Lang;
import Main.PrisonType;
import Player.Premium.PremiumType;
import org.bukkit.entity.Player;

public class PlayerData
{
    private Player p;
    private String name;
    private String uuid;
    private PremiumType premiumType;
    private long premiumLong;
    private PrisonType prisonType;
    private Lang lang;
    private int points;
    private int prisoner;
    private int respect;
    private int intellect;
    private int stamina;
    private int staminaExp;
    private int strength;
    private int strengthExp;
    private int craftingSkill;
    private int luck;
    private int hygiene;
    private int sleep;
    private int thirst;
    private int wc;
    private int lvl;
    private int xp;
    private int coins;
    private double money;
    private int cell;
    private boolean aggresive;
    private int bedcolor;
    private String chatcolor;
    private long solitary_toDate;
    private int warden_attack_warning;
    
    public PlayerData(final Player p) {
        this.premiumType = PremiumType.NOVIP;
        this.premiumLong = 0L;
        this.prisonType = PrisonType.LOBBY;
        this.lang = Lang.ENGLISH;
        this.points = 0;
        this.prisoner = 1;
        this.respect = 0;
        this.intellect = 0;
        this.stamina = 0;
        this.staminaExp = 0;
        this.strength = 0;
        this.strengthExp = 0;
        this.hygiene = 100;
        this.sleep = 100;
        this.thirst = 100;
        this.wc = 100;
        this.lvl = 1;
        this.xp = 0;
        this.coins = 0;
        this.money = 0.0;
        this.cell = 0;
        this.aggresive = false;
        this.bedcolor = 0;
        this.chatcolor = "f";
        this.solitary_toDate = 0L;
        this.warden_attack_warning = 0;
        this.p = p;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    public Player searchPlayer() {
        return Bukkit.getServer().getPlayer(this.getName());
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setUUID(final String uuid) {
        this.uuid = uuid;
    }
    
    public String getUUID() {
        return this.uuid;
    }
    
    public void setPremium(final PremiumType type) {
        this.premiumType = type;
    }
    
    public PremiumType getPremium() {
        return this.premiumType;
    }
    
    public void setPremiumInt(final int i) {
        switch (i) {
            case 0: {
                this.premiumType = PremiumType.NOVIP;
                break;
            }
            case 1: {
                this.premiumType = PremiumType.MASTER;
                break;
            }
            case 2: {
                this.premiumType = PremiumType.LEGION;
                break;
            }
        }
    }
    
    public void PremiumSetLong(final long data) {
        this.premiumLong = data;
    }
    
    public long getHowLongPremium() {
        return this.premiumLong;
    }
    
    public void setType(final PrisonType type) {
        this.prisonType = type;
    }
    
    public PrisonType getType() {
        return this.prisonType;
    }
    
    public int getTypeInt() {
        switch (this.prisonType) {
            case PRISONER: {
                return 0;
            }
            case WARDEN: {
                return 1;
            }
            case ADMIN: {
                return 2;
            }
            default: {
                return -1;
            }
        }
    }
    
    public void setTypeInt(final int i) {
        switch (i) {
            case 1: {
                this.prisonType = PrisonType.WARDEN;
                break;
            }
            case 2: {
                this.prisonType = PrisonType.ADMIN;
                break;
            }
            default: {
                this.prisonType = PrisonType.PRISONER;
                break;
            }
        }
    }
    
    public void setRespect(final int respect) {
        this.respect = respect;
    }
    
    public int getRespect() {
        return this.respect;
    }
    
    public void giveRespect(final int respect) {
        this.setRespect(this.getRespect() + respect);
    }
    
    public void setIntellect(final int intellect) {
        this.intellect = intellect;
    }
    
    public int getIntellect() {
        return this.intellect;
    }
    
    public void giveIntellect(final int intellect) {
        this.setIntellect(this.getIntellect() + intellect);
    }
    
    public void setStamina(final int stamina) {
        this.stamina = stamina;
    }
    
    public int getStamina() {
        return this.stamina;
    }
    
    public void giveStamina(final int stamina) {
        this.setStamina(this.getStamina() + stamina);
    }
    
    public void setStaminaExp(final int staminaExp) {
        this.staminaExp = staminaExp;
    }
    
    public int getStaminaExp() {
        return this.staminaExp;
    }
    
    public void giveStaminaExp(final int staminaExp) {
        this.setStaminaExp(this.getStaminaExp() + staminaExp);
    }
    
    public void setStrength(final int strength) {
        this.strength = strength;
    }
    
    public int getStrength() {
        return this.strength;
    }
    
    public void giveStrength(final int strength) {
        this.setStrength(this.getStrength() + strength);
    }
    
    public void setStrengthExp(final int strengthExp) {
        this.strengthExp = strengthExp;
    }
    
    public int getStrengthExp() {
        return this.strengthExp;
    }
    
    public void giveStrengthExp(final int strengthExp) {
        this.setStrengthExp(this.getStrengthExp() + strengthExp);
    }
    
    public void setHygiene(final int hygiene) {
        this.hygiene = hygiene;
    }
    
    public int getHygiene() {
        return this.hygiene;
    }
    
    public void giveHygiene(final int hygiene) {
        this.setHygiene(this.getHygiene() + hygiene);
    }
    
    public void setSleep(final int sleep) {
        this.sleep = sleep;
    }
    
    public int getSleep() {
        return this.sleep;
    }
    
    public void giveSleep(final int sleep) {
        this.setSleep(this.getSleep() + sleep);
    }
    
    public void setThirst(final int thirst) {
        this.thirst = thirst;
    }
    
    public int getThirst() {
        return this.thirst;
    }
    
    public void giveThirst(final int thirst) {
        this.setThirst(this.getThirst() + thirst);
    }
    
    public void setWC(final int wc) {
        this.wc = wc;
    }
    
    public int getWC() {
        return this.wc;
    }
    
    public void giveWC(final int wc) {
        this.setWC(this.getWC() + wc);
    }
    
    public void setLVL(final int lvl) {
        this.lvl = lvl;
    }
    
    public int getLVL() {
        return this.lvl;
    }
    
    public void giveLVL(final int lvl) {
        this.setLVL(this.getLVL() + lvl);
    }
    
    public void setXP(final int xp) {
        this.xp = xp;
    }
    
    public int getXP() {
        return this.xp;
    }
    
    public void giveXP(final int xp) {
        this.setXP(this.getXP() + xp);
    }
    
    public void setBedColor(final int bedcolor) {
        this.bedcolor = bedcolor;
    }
    
    public int getBedColor() {
        return this.bedcolor;
    }
    
    public void setChatColor(final String chatcolor) {
        this.chatcolor = chatcolor;
    }
    
    public String getChatColor() {
        return this.chatcolor;
    }
    
    public void setCoins(final int coins) {
        this.coins = coins;
    }
    
    public int getCoins() {
        return this.coins;
    }
    
    public void giveCoins(final int coins) {
        this.setCoins(this.getCoins() + coins);
    }
    
    public void setCell(final int cell) {
        this.cell = cell;
    }
    
    public int getCell() {
        return this.cell;
    }
    
    public void setAggresive(final boolean agr) {
        this.aggresive = agr;
    }
    
    public boolean getAggresive() {
        return this.aggresive;
    }
    
    public void setLuck(final int luck) {
        this.luck = luck;
    }
    
    public int getLuck() {
        return this.luck;
    }
    
    public void giveLuck(final int luck) {
        this.setLuck(this.getLuck() + luck);
    }
    
    public void setCraftingSkill(final int craftingSkill) {
        this.craftingSkill = craftingSkill;
    }
    
    public int getCraftingSkill() {
        return this.craftingSkill;
    }
    
    public void giveCraftingSkill(final int craftingSkill) {
        this.setCraftingSkill(this.getCraftingSkill() + craftingSkill);
    }
    
    public void setLang(final Lang lang) {
        this.lang = lang;
    }
    
    public Lang getLang() {
        return this.lang;
    }
    
    public void setLangString(final String shortName) {
        if (shortName.equalsIgnoreCase("sk")) {
            this.lang = Lang.SLOVAKIA;
        }
        else if (shortName.equalsIgnoreCase("en")) {
            this.lang = Lang.ENGLISH;
        }
        else if (shortName.equalsIgnoreCase("cz")) {
            this.lang = Lang.CZECH;
        }
    }
    
    public void setLangInt(final int lang) {
        switch (lang) {
            case 1: {
                this.lang = Lang.ENGLISH;
                break;
            }
            case 2: {
                this.lang = Lang.SLOVAKIA;
                break;
            }
            case 3: {
                this.lang = Lang.CZECH;
                break;
            }
        }
    }
    
    public void setPoints(final int points) {
        this.points = points;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void givePoints(final int points) {
        this.setPoints(this.getPoints() + points);
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    public double getMoney() {
        return this.money;
    }
    
    public void giveMoney(final double money) {
        this.setMoney(this.getMoney() + money);
    }
    
    public void setSolitary(final long solitary_toDate) {
        this.solitary_toDate = solitary_toDate;
    }
    
    public long getSolitary() {
        return this.solitary_toDate;
    }
    
    public void giveSolitary(final long solitary_toDate) {
        this.setSolitary(this.getSolitary() + solitary_toDate);
    }
    
    public void setPrisoner(final int prisoner) {
        this.prisoner = prisoner;
    }
    
    public int getPrisoner() {
        return this.prisoner;
    }
    
    public int getWarden_attack_warning() {
        return this.warden_attack_warning;
    }
    
    public void setWarden_attack_warning(final int warden_attack_warning) {
        this.warden_attack_warning = warden_attack_warning;
    }
}
