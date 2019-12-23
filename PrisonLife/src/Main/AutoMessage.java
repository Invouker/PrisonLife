// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;

public class AutoMessage
{
    public static List<String> ad;
    private static String adPrefix;
    
    static {
        AutoMessage.ad = new ArrayList<String>();
        AutoMessage.adPrefix = "&7&l&m] &6&m*&l&6TIP&m*&7&m &l[&8 » &7";
    }
    
    public static void setupAD() {
        new BukkitRunnable() {
            public void run() {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    AutoMessage.ad = Chat.getTList("Ads-List", p, new String[0]);
                    final int size = AutoMessage.ad.size();
                    final Random rand = new Random();
                    final int nahodne = rand.nextInt(size);
                    AutoMessage.adForm(p, AutoMessage.ad.get(nahodne));
                    AutoMessage.ad.clear();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 6000L);
    }
    
    public static void adForm(final Player p, final String text) {
        p.sendMessage(String.valueOf(AutoMessage.adPrefix.replaceAll("&", "§")) + text.replaceAll("&", "§"));
    }
}
