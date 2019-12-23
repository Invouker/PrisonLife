// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Maintance
{
    public static List<String> whitelist;
    public static List<String> un_whitelisted;
    
    static {
        Maintance.whitelist = new ArrayList<String>();
        Maintance.un_whitelisted = new ArrayList<String>();
    }
    
    public static void loadWhiteList() {
        Maintance.whitelist = (List<String>)Main.getWhiteList().getStringList("WhiteList");
        Maintance.un_whitelisted = (List<String>)Main.getWhiteList().getStringList("Connected");
    }
    
    public static void add(final String p) {
        if (!Maintance.whitelist.contains(p)) {
            Maintance.whitelist.add(p);
        }
    }
    
    public static void remove(final String p) {
        if (Maintance.whitelist.contains(p)) {
            Maintance.whitelist.remove(p);
        }
    }
    
    public static boolean isWhiteListed(final Player p) {
        if (Maintance.whitelist.contains(p.getName())) {
            return true;
        }
        Maintance.un_whitelisted.add(p.getName());
        saveUnWL();
        return false;
    }
    
    public static void saveUnWL() {
        Main.getWhiteList().set("Connected", (Object)Maintance.un_whitelisted);
        Main.getWhiteList().save();
    }
    
    public static void saveWhiteList() {
        Main.getWhiteList().set("WhiteList", (Object)Maintance.whitelist);
        Main.getWhiteList().save();
    }
    
    public static boolean statusWhitelist() {
        return Main.getWhiteList().getBoolean("White-List_Status");
    }
    
    public static void setStatus(final boolean data) {
        Main.getWhiteList().set("White-List_Status", (Object)data);
    }
}
