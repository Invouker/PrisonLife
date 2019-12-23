// 
// Decompiled by Procyon v0.5.36
// 

package Version;

import net.minecraft.server.v1_14_R1.EntityPlayer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import net.minecraft.server.v1_14_R1.Packet;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import Main.Main;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import org.bukkit.entity.Player;
import java.text.SimpleDateFormat;
import Player.Data.PlayerDataHandler;

public class ActionBar
{
    PlayerDataHandler pdh;
    public static final int ticksAtMidnight = 18000;
    public static final int ticksPerDay = 24000;
    public static final int ticksPerHour = 1000;
    public static final double ticksPerMinute = 16.666666666666668;
    public static final double ticksPerSecond = 0.2777777777777778;
    private static final SimpleDateFormat SDFTwentyFour;
    
    static {
        SDFTwentyFour = new SimpleDateFormat("HH:mm");
    }
    
    public ActionBar() {
        this.pdh = new PlayerDataHandler();
    }
    
    public void setupActionBar(final Player p) {
        final int points = this.pdh.getData(p).getPoints();
        final double money = this.pdh.getData(p).getMoney();
        final String text = "§c§lPing §f" + this.getPing(p) + "ms §7| " + "§aPoints §f" + points + " §7| " + "§6§l" + format24(p.getPlayerTime()) + " §7| " + "§2§l" + money;
        sendActionBar(p, text);
    }
    
    public static String format24(final long ticks) {
        synchronized (ActionBar.SDFTwentyFour) {
            // monitorexit(ActionBar.SDFTwentyFour)
            return formatDateFormat(ticks, ActionBar.SDFTwentyFour);
        }
    }
    
    public static String formatDateFormat(final long ticks, final SimpleDateFormat format) {
        final Date date = ticksToDate(ticks);
        return format.format(date);
    }
    
    public static Date ticksToDate(long ticks) {
        ticks = ticks - 18000L + 24000L;
        final long days = ticks / 24000L;
        ticks -= days * 24000L;
        final long hours = ticks / 1000L;
        ticks -= hours * 1000L;
        final long minutes = (long)Math.floor(ticks / 16.666666666666668);
        final double dticks = ticks - minutes * 16.666666666666668;
        final long seconds = (long)Math.floor(dticks / 0.2777777777777778);
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.ENGLISH);
        cal.setLenient(true);
        cal.set(0, 0, 1, 0, 0, 0);
        cal.add(6, (int)days);
        cal.add(11, (int)hours - 1);
        cal.add(12, (int)minutes);
        cal.add(13, (int)seconds + 1);
        return cal.getTime();
    }
    
    public void ActionBarUpdater() {
        new BukkitRunnable() {
            public void run() {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    ActionBar.this.setupActionBar(p);
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    
    public static void sendActionBarAll(final String text) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, text);
        }
    }
    
    public static void sendActionBar(final Player p, final String text) {
        final IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', text) + "\"}");
        final PacketPlayOutChat actionBarText = new PacketPlayOutChat(chat, ChatMessageType.GAME_INFO);
        final PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        connection.sendPacket((Packet)actionBarText);
    }
    
    public int getPing(final Player p) {
        try {
            final Class<?> c = Class.forName("org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer");
            final Object CraftPlayer = c.cast(p);
            final Method getHandle = CraftPlayer.getClass().getMethod("getHandle", (Class<?>[])new Class[0]);
            final Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);
            final Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            return ping.getInt(EntityPlayer);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static int getPings(final Player p) {
        final CraftPlayer cp = (CraftPlayer)p;
        final EntityPlayer ep = cp.getHandle();
        return ep.ping;
    }
}
