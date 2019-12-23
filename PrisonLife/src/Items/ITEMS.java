// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ITEMS
{
    CLOSE_MENU("CLOSE_MENU", 0, "Close Menu", new ItemBuilder(Material.STRUCTURE_VOID).setName("§3Close Menu").toItemStack()), 
    BORDER_MENU("BORDER_MENU", 1, "", new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§b").toItemStack()), 
    NEXT_PAGE("NEXT_PAGE", 2, "Next Page", new ItemBuilder(Material.ARROW).setName("§fNext Page").toItemStack()), 
    PREVIOUS_PAGE("PREVIOUS_PAGE", 3, "Previous Page", new ItemBuilder(Material.ARROW).setName("§fPrevious Page").toItemStack()), 
    COIN("COIN", 4, "Coin", ItemRarity.COMMON, new ItemBuilder(Material.SUNFLOWER).setName("§fCoin").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    CIGARETA("CIGARETA", 5, "Cigareta", ItemRarity.RARE, new ItemBuilder(Material.STICK).setName("§fCigareta").setRare(ItemRarity.RARE, 1).toItemStack()), 
    BEETROOT("BEETROOT", 6, "Beetroot", ItemRarity.BASIC, new ItemBuilder(Material.BEETROOT).setName("§fBeetroot").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    COOKIE("COOKIE", 7, "Cookie", ItemRarity.BASIC, new ItemBuilder(Material.COOKIE).setName("§fCookie").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    MELON_SLICE("MELON_SLICE", 8, "Melon Slice", ItemRarity.BASIC, new ItemBuilder(Material.MELON_SLICE).setName("§fMelon Slice").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    CARROT("CARROT", 9, "Carrot", ItemRarity.BASIC, new ItemBuilder(Material.CARROT).setName("§fCarrot").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    APPLE("APPLE", 10, "Apple", ItemRarity.BASIC, new ItemBuilder(Material.APPLE).setName("§fApple").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    BAKED_POTATO("BAKED_POTATO", 11, "Baked Potato", ItemRarity.BASIC, new ItemBuilder(Material.BAKED_POTATO).setName("§fBaked Potato").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    BREAD("BREAD", 12, "Bread", ItemRarity.BASIC, new ItemBuilder(Material.BREAD).setName("§fBread").setRare(ItemRarity.BASIC, 1).toItemStack()), 
    COOKED_COD("COOKED_COD", 13, "Cooked Cod", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_COD).setName("§fCooked Cod").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    COOKED_RABBIT("COOKED_RABBIT", 14, "Cooked Rabbit", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_RABBIT).setName("§fCooked Rabbit").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    BEETROOT_SOUP("BEETROOT_SOUP", 15, "Beetroot Soup", ItemRarity.COMMON, new ItemBuilder(Material.BEETROOT_SOUP).setName("§fBeetroot soup").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    COOKED_CHICKEN("COOKED_CHICKEN", 16, "Cooked Chicken", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_CHICKEN).setName("§fCooked chicken").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    COOKED_MUTTON("COOKED_MUTTON", 17, "Cooked Mutton", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_MUTTON).setName("§fCooked Mutton").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    COOKED_SALMON("COOKED_SALMON", 18, "Cooked Salmon", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_SALMON).setName("§fCooked Salmon").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    MUSHROOM_STEW("MUSHROOM_STEW", 19, "Mushrom Stew", ItemRarity.COMMON, new ItemBuilder(Material.MUSHROOM_STEW).setName("§fMushroom Stew").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    COOKED_PORKCHOP("COOKED_PORKCHOP", 20, "Cooked Porkchop", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_PORKCHOP).setName("§fCooked Porkchop").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    PUMPKIN_PIE("PUMPKIN_PIE", 21, "Pumpkin Pie", ItemRarity.COMMON, new ItemBuilder(Material.PUMPKIN_PIE).setName("§fPumpkin Pie").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    STEAK("STEAK", 22, "Steak", ItemRarity.COMMON, new ItemBuilder(Material.COOKED_BEEF).setName("§fSteak").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    RABBIT_STEW("RABBIT_STEW", 23, "Rabbit Stew", ItemRarity.COMMON, new ItemBuilder(Material.RABBIT_STEW).setName("§fRabbit Stew").setRare(ItemRarity.COMMON, 1).toItemStack()), 
    BOTTLE_01L("BOTTLE_01L", 24, "§bBotte Of Water §f0.1L", ItemRarity.UNCOMMON, getBottle("0.1")), 
    BOTTLE_03L("BOTTLE_03L", 25, "§bBotte Of Water §f0.3L", ItemRarity.UNCOMMON, getBottle("0.3")), 
    BOTTLE_05L("BOTTLE_05L", 26, "§bBotte Of Water §f0.5L", ItemRarity.UNCOMMON, getBottle("0.5"));
    
    private ItemStack is;
    private String name;
    private ItemRarity ir;
    
    private ITEMS(final String name2, final int ordinal, final String name, final ItemStack is) {
        this.name = name;
        this.is = is;
    }
    
    private ITEMS(final String name2, final int ordinal, final String name, final ItemRarity ir, final ItemStack is) {
        this.name = name;
        this.ir = ir;
        this.is = is;
    }
    
    public ItemRarity getItemRarity() {
        return this.ir;
    }
    
    public ItemStack getItem() {
        return this.is;
    }
    
    public String getName() {
        return this.name;
    }
    
    private static ItemStack getBottle(final String amount) {
        final ItemStack isp = new ItemStack(Material.POTION, 1);
        final PotionMeta meta = (PotionMeta)isp.getItemMeta();
        meta.setDisplayName("§bBotte Of Water §f§l" + amount + "L");
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.WATER));
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        isp.setItemMeta((ItemMeta)meta);
        return new ItemBuilder(isp).setRare(ItemRarity.COMMON).toItemStack();
    }
}
