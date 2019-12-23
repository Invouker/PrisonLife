// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import Gangs.Gang;
import Player.Data.PlayerDataHandler;
import Player.Premium.PremiumType;

public class Chat implements Listener
{
    static PlayerDataHandler pdh;
    private static final String prefix = "§ePrison §8»§7 ";
    private static final String gangPrefix = "§c[§lGANG§c]§7 ";
    private static final String cprefix = "§ePrison §8>>§f";
    private static net.milkbowl.vault.chat.Chat chat;
    
    static {
        Chat.pdh = new PlayerDataHandler();
    }
    
    public static void sendListAll(final String source, final String... args) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            sendList(p, source, args);
        }
    }
    
    public static void sendList(final Player p, final String source, final String... string) {
        final List<String> iterate = getTList(source, p, new String[0]);
        for (final String s : iterate) {
            if (p instanceof Player) {
                p.sendMessage("§ePrison §8»§7 " + s);
            }
            else {
                print(s);
            }
        }
    }
    
    public static void send(final Player p, final String source, final String... string) {
        final String translated = getT(source, p, string);
        if (p instanceof Player) {
            p.sendMessage(prefix + translated);
        }
        else {
            print(translated);
        }
    }
    
    public static void sendAll(final String source, final String... string) {
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            final String translated = getT(source, p, string);
            p.sendMessage(prefix + translated);
        }
    }
    
    public static void print(final String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(cprefix + msg);
    }
    
    public static void info(final Player p, final String msg) {
        p.sendMessage(prefix + msg);
    }
    
    public static void infoSender(final CommandSender sender, final String msg) {
        sender.sendMessage(msg);
    }
    
    public static void sendGangMessage(final Gang gang, final String message) {
        for (final String player : gang.getPlayers()) {
            final Player target = Bukkit.getPlayer(player);
            if (target != null) {
                target.sendMessage(gangPrefix + message);
            }
        }
        final Player founder = Bukkit.getPlayer(gang.getFounder());
        if (founder != null) {
            founder.sendMessage(gangPrefix + message);
        }
    }
    
    public static void noPerm(final Player p) {
        final String translated = getT("no-permissions", p, new String[0]);
        p.sendMessage(prefix + translated);
    }
    
    public static boolean setupChat() {
        final RegisteredServiceProvider<net.milkbowl.vault.chat.Chat> chatProvider = (RegisteredServiceProvider<net.milkbowl.vault.chat.Chat>)Bukkit.getServer().getServicesManager().getRegistration((Class)net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            Chat.chat = (net.milkbowl.vault.chat.Chat)chatProvider.getProvider();
        }
        return Chat.chat != null;
    }
    
    public static String getT(final String source, final Player p, final String... strings) {
        final String lang = Chat.pdh.getData(p).getLang().getInShort().toLowerCase();
        String returned = Main.getLang().getString(String.valueOf(lang) + "." + source);
        try {
            returned = returned.replaceAll("&", "§");
        }
        catch (NullPointerException ignore) {
            print("§4ERROR §cChat error - Source: " + lang + "." + source + ", Player: " + p.getName());
            return "";
        }
        int i = 1;
        for (final String s : strings) {
            if (s != null) {
                returned = returned.replaceFirst("%" + i, s);
                ++i;
            }
        }
        return returned;
    }
    
    public static List<String> getTList(final String source, final Player p, final String... strings) {
        final String lang = Chat.pdh.getData(p).getLang().getInShort().toLowerCase();
        List<String> returned = new ArrayList<String>();
        returned = (List<String>)Main.getLang().getStringList(String.valueOf(lang) + "." + source);
        if (returned == null) {
            print("§4ERROR §cChat error - Source:" + source + " Player: " + p.getName());
        }
        int i = 1;
        for (final String s : strings) {
            if (s != null) {
                for (final String b : returned) {
                    b.replaceAll("%" + i, s);
                    ++i;
                }
            }
        }
        return returned;
    }
    
    public String getFormated(final Player p, final String text) {
        return String.valueOf(this.getClass(p)) + "§f" + p.getName() + " " + this.getGroup(p) + " §8» §f" + text;
    }
    
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        String msg = e.getMessage();
        final PremiumType pType = Chat.pdh.getData(p).getPremium();
        if (pType == PremiumType.MASTER || pType == PremiumType.LEGION) {
            msg = msg.replaceAll("&", "§");
        }
        final String msg2 = msg.replaceAll("%", "%%");
        print("CLASS: " + this.getClass(p) + " TYPE: " + Chat.pdh.getData(p).getType().getName());
        e.setFormat(this.getFormated(p, msg2));
    }
    
    public String getClass(final Player p) {
        String Class = "";
        final PrisonType type = Chat.pdh.getData(p).getType();
        if (type == PrisonType.PRISONER) {
            Class = "§e§lPrisoner ";
        }
        if (type == PrisonType.WARDEN) {
            Class = "§9§lWarden ";
        }
        if (type == PrisonType.ADMIN) {
            Class = "§c§lAdmin ";
        }
        return Class;
    }
    
    public String getGroup(final Player p) {
        final String group = Chat.chat.getPrimaryGroup(p);
        String rgroup = "§cN";
        if (group == null || group.isEmpty()) {
            return rgroup = "§7P";
        }
        if (group.equalsIgnoreCase("default") || group.equalsIgnoreCase("Player")) {
            rgroup = "§7P";
        }
        if (group.equalsIgnoreCase("Support")) {
            rgroup = "§b§lS";
        }
        if (group.equalsIgnoreCase("Builder")) {
            rgroup = "§a§lB";
        }
        if (group.equalsIgnoreCase("Moderator")) {
            rgroup = "§3§lM";
        }
        if (group.equalsIgnoreCase("Developer")) {
            rgroup = "§6§lD";
        }
        if (group.equalsIgnoreCase("Admin")) {
            rgroup = "§3§lA";
        }
        if (group.equalsIgnoreCase("Leader")) {
            rgroup = "§9§lL";
        }
        return String.valueOf(rgroup) + "§f";
    }
    
    public static String getException(final int ex, final int line, final Class<?> c) {
        String message = null;
        switch (ex) {
            case 0: {
                message = "Error - Class found " + c.getName() + " Line " + line;
                break;
            }
            case 1: {
                message = "Warn - Function found " + c.getName() + " Line " + line;
                break;
            }
            case 2: {
                message = "Warn - Class found " + c.getName() + " Line " + line;
                break;
            }
            default: {
                message = "Error Exception Error !";
                break;
            }
        }
        return message;
    }
}
