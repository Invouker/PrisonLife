// 
// Decompiled by Procyon v0.5.36
// 

package DayLightCycle;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import Main.Chat;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DayLight
{
    private static int speedTime_Day;
    private static int speedTime_Night;
    static BukkitTask playerTask;
    static BukkitTask globalTask;
    static BukkitTask timeTask;
    static Long time;
    public static boolean isClosed;
    private static long globalTime;
    
    static {
        DayLight.speedTime_Day = 1;
        DayLight.speedTime_Night = 4;
        DayLight.time = 0L;
        DayLight.isClosed = false;
        DayLight.globalTime = 0L;
    }
    
    public static void timeTask() {
        DayLight.timeTask = new BukkitRunnable() {
            boolean isNight = false;
            
            public void run() {
                ++DayLight.time;
                final long wTime = DayLight.time % 24000L;
                DoorControls cellDoors = null;
                DoorControls gates = null;
                if (wTime == 23000L) {
                    Chat.sendAll("day-night.get-day", String.valueOf(DayLight.time % 24000L));
                    this.isNight = false;
                    cellDoors = new NightDoorControl();
                    gates = new DoorControler(true);
                }
                else if (wTime == 13000L) {
                    Chat.sendAll("day-night.get-night", String.valueOf(DayLight.time % 24000L));
                    CloseCell.delayDoor();
                    cellDoors = new DayDoorControl();
                    gates = new DoorControler(false);
                    this.isNight = true;
                }
                if (cellDoors != null) {
                    cellDoors.updateDoorState();
                }
                if (gates != null) {
                    gates.updateDoorState();
                }
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().getWorld().getName().equals(Main.mainWorld)) {
                        p.setPlayerTime(DayLight.time % 24000L, true);
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1L);
    }
    
    public static void setTime(final long time) {
        DayLight.time = time;
    }
    
    public static void updateTimes() {
        DayLight.playerTask = new BukkitRunnable() {
            public void run() {
                long time = DayLight.globalTime;
                if (time >= 13000L && time <= 22999L) {
                    if (time >= 13000L && time <= 13003L) {
                        Chat.sendAll("day-night.get-night", new StringBuilder(String.valueOf(time)).toString());
                        DayLight.isClosed = true;
                        Chat.print("Nastala noc!");
                        CloseCell.delayDoor();
                    }
                    time += DayLight.speedTime_Night;
                }
                else {
                    if (time == 23000L) {
                        DayLight.isClosed = false;
                        Chat.sendAll("day-night.get-day", new StringBuilder(String.valueOf(time)).toString());
                        Chat.print("Nastala de\u0148!");
                        OpenCell.controlDoor();
                    }
                    time += DayLight.speedTime_Day;
                }
                setPlayerTime(time);
                DayLight.access$4(time);
                if (DayLight.globalTime >= 23999L) {
                    DayLight.access$4(0L);
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1L);
    }
    
    private static void setPlayerTime(final long time) {
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.setPlayerTime(time, false);
        }
    }
    
    public static void globTime() {
        new BukkitRunnable() {
            public void run() {
                final World w = Bukkit.getServer().getWorld(Main.mainWorld);
                try {
                    w.setTime(18000L);
                }
                catch (Exception ex) {
                    Chat.getException(1, 73, this.getClass());
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 100L);
    }
    
    public static long getTime() {
        return DayLight.globalTime;
    }
    
    @Deprecated
    public static void setTimeOLD(final long time) {
        DayLight.globalTime = time;
    }
    
    static /* synthetic */ void access$4(final long globalTime) {
        DayLight.globalTime = globalTime;
    }
}
