// 
// Decompiled by Procyon v0.5.36
// 

package Listeners;

import java.util.Calendar;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Main.Main;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;

public class ChatReaction implements Listener
{
    private static char[] alphabet;
    private static boolean isRunning;
    private static String attachedString;
    static PlayerDataHandler pdh;
    private static final int LENGTH_RANDOM_STRING = 7;
    private static BukkitTask afterTask;
    
    static {
        ChatReaction.alphabet = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        ChatReaction.pdh = new PlayerDataHandler();
    }
    
    @EventHandler
    public void on(final AsyncPlayerChatEvent e) {
        if (ChatReaction.isRunning && ChatReaction.attachedString != null && e.getMessage().contains(ChatReaction.attachedString)) {
            final Calendar rightNow = Calendar.getInstance();
            final int hour = rightNow.get(11);
            if (hour >= 10 && hour <= 22) {
                final Player p = e.getPlayer();
                if (ChatReaction.pdh.getData(p).getType() == PrisonType.PRISONER) {
                    for (final Player pl : Bukkit.getOnlinePlayers()) {
                        if (ChatReaction.pdh.getData(pl).getType() == PrisonType.PRISONER) {
                            pl.sendMessage("Hr\u00e1\u010d " + p.getName() + " vyhr\u00e1l reakciu!");
                            e.setCancelled(true);
                        }
                    }
                    ChatReaction.pdh.getData(p).setIntellect(ChatReaction.pdh.getData(p).getIntellect() + 1);
                    ChatReaction.isRunning = false;
                    ChatReaction.attachedString = null;
                    ChatReaction.afterTask.cancel();
                }
            }
        }
    }
    
    public static void runChatReaction() {
        new BukkitRunnable() {
            public void run() {
                final Calendar rightNow = Calendar.getInstance();
                final int hour = rightNow.get(11);
                if (hour < 10 || hour > 22) {
                    return;
                }
                if (ChatReaction.isRunning) {
                    ChatReaction.access$1(false);
                    ChatReaction.access$2(null);
                    return;
                }
                ChatReaction.access$2(getRandomStringOfLetters(7));
                ChatReaction.access$1(true);
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (ChatReaction.pdh.getData(p).getType() == PrisonType.PRISONER) {
                        p.sendMessage("§6§lChatReaction §8>>§7 Kto ako prv\u00fd nap\u00ed\u0161e " + ChatReaction.attachedString + " dostane +1 inteligenciu.");
                    }
                }
                runChatReactionAfter();
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 7200L);
    }
    
    private static void runChatReactionAfter() {
        ChatReaction.afterTask = new BukkitRunnable() {
            public void run() {
                ChatReaction.access$2(null);
                ChatReaction.access$1(false);
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (ChatReaction.pdh.getData(p).getType() == PrisonType.PRISONER) {
                        p.sendMessage("Nikto do minuty nenap\u00edsal spr\u00e1vny tvar slova. Reakcia sa ru\u0161\u00ed!");
                    }
                }
            }
        }.runTaskLaterAsynchronously((Plugin)Main.getInstance(), 1200L);
    }
    
    private static String getRandomStringOfLetters(final int length) {
        String randomLetters = "";
        for (int i = 0; i < length; ++i) {
            final Random rand = new Random();
            String x = String.valueOf(ChatReaction.alphabet[rand.nextInt(ChatReaction.alphabet.length)]);
            if (rand.nextBoolean()) {
                x = x.toUpperCase();
            }
            else {
                x = x.toLowerCase();
            }
            randomLetters = String.valueOf(randomLetters) + x;
        }
        return randomLetters;
    }
    
    static /* synthetic */ void access$1(final boolean isRunning) {
        ChatReaction.isRunning = isRunning;
    }
    
    static /* synthetic */ void access$2(final String attachedString) {
        ChatReaction.attachedString = attachedString;
    }
}
