// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import org.bukkit.ChatColor;

public enum ItemRarity
{
    NONE("NONE", 0), 
    BASIC(1) {
        @Override
        public String toString() {
            return "BASIC";
        }
    }, 
    UNCOMMON(2) {
        @Override
        public String toString() {
            return "UNCOMMON";
        }
    }, 
    COMMON(3) {
        @Override
        public String toString() {
            return "COMMON";
        }
    }, 
    RARE(4) {
        @Override
        public String toString() {
            return "RARE";
        }
    }, 
    LEGENDARY(5) {
        @Override
        public String toString() {
            return "LEGENDARY";
        }
    };
    
    private ItemRarity(String name, int ordinal) {
    }
    
    private ItemRarity(int ordinal) {
    }
    
    public int getOrder(final ItemRarity rare) {
        int number = 0;
        switch (rare) {
            case BASIC: {
                number = 1;
                break;
            }
            case UNCOMMON: {
                number = 2;
                break;
            }
            case COMMON: {
                number = 3;
                break;
            }
            case RARE: {
                number = 4;
                break;
            }
            case LEGENDARY: {
                number = 5;
                break;
            }
            default: {
                number = -1;
                break;
            }
        }
        return number;
    }
    
    public ItemRarity getRarity(final int number) {
        ItemRarity rare = null;
        switch (number) {
            case 1: {
                rare = ItemRarity.BASIC;
                break;
            }
            case 2: {
                rare = ItemRarity.UNCOMMON;
                break;
            }
            case 3: {
                rare = ItemRarity.COMMON;
                break;
            }
            case 4: {
                rare = ItemRarity.RARE;
                break;
            }
            case 5: {
                rare = ItemRarity.LEGENDARY;
                break;
            }
            default: {
                rare = ItemRarity.BASIC;
                break;
            }
        }
        return rare;
    }
    
    public ChatColor getColor(final ItemRarity rare) {
        ChatColor col = ChatColor.RED;
        switch (rare) {
            case BASIC: {
                col = ChatColor.WHITE;
                break;
            }
            case UNCOMMON: {
                col = ChatColor.GRAY;
                break;
            }
            case COMMON: {
                col = ChatColor.AQUA;
                break;
            }
            case RARE: {
                col = ChatColor.DARK_PURPLE;
                break;
            }
            case LEGENDARY: {
                col = ChatColor.GOLD;
                break;
            }
            default: {
                col = ChatColor.DARK_RED;
                break;
            }
        }
        return col;
    }
}
