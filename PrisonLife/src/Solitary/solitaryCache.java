// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import Main.Chat;
import Main.Main;
import org.bukkit.scheduler.BukkitTask;
import java.util.HashMap;

public class solitaryCache
{
    public static HashMap<Integer, Double> pos_X;
    public static HashMap<Integer, Double> pos_Y;
    public static HashMap<Integer, Double> pos_Z;
    public static HashMap<Integer, Integer> yaw;
    public static HashMap<Integer, String> world;
    public static HashMap<Integer, Integer> sign_X;
    public static HashMap<Integer, Integer> sign_Y;
    public static HashMap<Integer, Integer> sign_Z;
    public static HashMap<Integer, Long> toDate;
    public static HashMap<Integer, String> reason;
    public static HashMap<Integer, String> name;
    static SolitaryData sd;
    public static final int maxSolitary = 10;
    static BukkitTask saveData;
    static Main plugin;
    
    static {
        solitaryCache.pos_X = new HashMap<Integer, Double>();
        solitaryCache.pos_Y = new HashMap<Integer, Double>();
        solitaryCache.pos_Z = new HashMap<Integer, Double>();
        solitaryCache.yaw = new HashMap<Integer, Integer>();
        solitaryCache.world = new HashMap<Integer, String>();
        solitaryCache.sign_X = new HashMap<Integer, Integer>();
        solitaryCache.sign_Y = new HashMap<Integer, Integer>();
        solitaryCache.sign_Z = new HashMap<Integer, Integer>();
        solitaryCache.toDate = new HashMap<Integer, Long>();
        solitaryCache.reason = new HashMap<Integer, String>();
        solitaryCache.name = new HashMap<Integer, String>();
        solitaryCache.sd = new SolitaryData();
        solitaryCache.plugin = Main.getInstance();
    }
    
    public static void setL(final int solitary, final long data, final HashMap<Integer, Long> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
            map.put(solitary, data);
        }
        else {
            map.put(solitary, data);
        }
    }
    
    public static long getL(final int solitary, final HashMap<Integer, Long> map) {
        if (map.containsKey(solitary)) {
            return map.get(solitary);
        }
        Chat.print("Error in class: " + error() + ".class, on getS map not contains key !");
        return 0L;
    }
    
    public static void setS(final int solitary, final String data, final HashMap<Integer, String> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
            map.put(solitary, data);
        }
        else {
            map.put(solitary, data);
        }
    }
    
    public static void setI(final int solitary, final int data, final HashMap<Integer, Integer> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
            map.put(solitary, data);
        }
        else {
            map.put(solitary, data);
        }
    }
    
    public static String getS(final int solitary, final HashMap<Integer, String> map) {
        if (map.containsKey(solitary)) {
            return map.get(solitary);
        }
        Chat.print("Error in class: " + error() + ".class, on getS map not contains key !");
        return null;
    }
    
    public static int getI(final int solitary, final HashMap<Integer, Integer> map) {
        if (map.containsKey(solitary)) {
            return map.get(solitary);
        }
        return -1;
    }
    
    public static void setF(final int solitary, final float data, final HashMap<Integer, Float> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
            map.put(solitary, data);
        }
        else {
            map.put(solitary, data);
        }
    }
    
    public static float getF(final int solitary, final HashMap<Integer, Float> map) {
        if (map.containsKey(solitary)) {
            return map.get(solitary);
        }
        Chat.print("Error in class: " + error() + ".class, on getF map not contains key !");
        return -1.0f;
    }
    
    public static void setD(final int solitary, final double data, final HashMap<Integer, Double> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
            map.put(solitary, data);
        }
        else {
            map.put(solitary, data);
        }
    }
    
    public static double getD(final int solitary, final HashMap<Integer, Double> map) {
        if (map.containsKey(solitary)) {
            return map.get(solitary);
        }
        Chat.print("Error in class: " + error() + ".class, on getD map not contains key !");
        return -1.0;
    }
    
    public static String error() {
        return solitaryCache.class.getName();
    }
    
    public static void dataSynchronizing() {
        solitaryCache.saveData = new BukkitRunnable() {
            public void run() {
                solitaryCache.saveAll();
                Chat.print("§aSolitary data synchronizing");
            }
        }.runTaskTimer((Plugin)solitaryCache.plugin, 0L, 6000L);
    }
    
    public static void load(final int solitary) {
        if (solitaryCache.sd.solitaryExists(solitary)) {
            setD(solitary, solitaryCache.sd.getDData(solitary, "posX"), solitaryCache.pos_X);
            setD(solitary, solitaryCache.sd.getDData(solitary, "posY"), solitaryCache.pos_Y);
            setD(solitary, solitaryCache.sd.getDData(solitary, "posZ"), solitaryCache.pos_Z);
            setI(solitary, solitaryCache.sd.getIData(solitary, "yaw"), solitaryCache.yaw);
            setS(solitary, solitaryCache.sd.getSData(solitary, "world"), solitaryCache.world);
            setI(solitary, solitaryCache.sd.getIData(solitary, "signX"), solitaryCache.sign_X);
            setI(solitary, solitaryCache.sd.getIData(solitary, "signY"), solitaryCache.sign_Y);
            setI(solitary, solitaryCache.sd.getIData(solitary, "signZ"), solitaryCache.sign_Z);
            setL(solitary, solitaryCache.sd.getLData(solitary, "toDate"), solitaryCache.toDate);
            setS(solitary, solitaryCache.sd.getSData(solitary, "reason"), solitaryCache.reason);
            setS(solitary, solitaryCache.sd.getSData(solitary, "name"), solitaryCache.name);
        }
    }
    
    public static void save(final int solitary) {
        solitaryCache.sd.setDData(solitary, "posX", getD(solitary, solitaryCache.pos_X));
        solitaryCache.sd.setDData(solitary, "posY", getD(solitary, solitaryCache.pos_Y));
        solitaryCache.sd.setDData(solitary, "posZ", getD(solitary, solitaryCache.pos_Z));
        solitaryCache.sd.setIData(solitary, "yaw", getI(solitary, solitaryCache.yaw));
        solitaryCache.sd.setSData(solitary, "world", getS(solitary, solitaryCache.world));
        solitaryCache.sd.setIData(solitary, "signX", getI(solitary, solitaryCache.sign_X));
        solitaryCache.sd.setIData(solitary, "signY", getI(solitary, solitaryCache.sign_Y));
        solitaryCache.sd.setIData(solitary, "signZ", getI(solitary, solitaryCache.sign_Z));
        solitaryCache.sd.setLData(solitary, "toDate", getL(solitary, solitaryCache.toDate));
        solitaryCache.sd.setSData(solitary, "reason", getS(solitary, solitaryCache.reason));
        solitaryCache.sd.setSData(solitary, "name", getS(solitary, solitaryCache.name));
    }
    
    public static void saveAll() {
        for (int i = 1; i < 10; ++i) {
            load(i);
        }
    }
    
    public static void remove(final int solitary, final HashMap<?, ?> map) {
        if (map.containsKey(solitary)) {
            map.remove(solitary);
        }
    }
    
    public static void reload(final int solitary) {
        save(solitary);
        remover(solitary);
        load(solitary);
    }
    
    public static void loader() {
        for (int i = 1; i < 10; ++i) {
            load(i);
        }
    }
    
    public static void remover(final int solitary) {
        remove(solitary, solitaryCache.pos_X);
        remove(solitary, solitaryCache.pos_Y);
        remove(solitary, solitaryCache.pos_Z);
        remove(solitary, solitaryCache.yaw);
        remove(solitary, solitaryCache.world);
        remove(solitary, solitaryCache.sign_X);
        remove(solitary, solitaryCache.sign_Y);
        remove(solitary, solitaryCache.sign_Z);
        remove(solitary, solitaryCache.toDate);
        remove(solitary, solitaryCache.reason);
        remove(solitary, solitaryCache.name);
    }
    
    public static void reload() {
        for (int i = 0; i < 10; ++i) {
            reload(i);
        }
    }
}
