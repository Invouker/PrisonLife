// 
// Decompiled by Procyon v0.5.36
// 

package Cells;

import java.util.HashMap;

import Main.Chat;

public class CellDataCache
{
    static CellDataProvider cd;
    public static HashMap<Integer, Integer> sign_X;
    public static HashMap<Integer, Integer> sign_Y;
    public static HashMap<Integer, Integer> sign_Z;
    public static HashMap<Integer, String> owner;
    public static HashMap<Integer, Integer> spawn_X;
    public static HashMap<Integer, Integer> spawn_Y;
    public static HashMap<Integer, Integer> spawn_Z;
    public static HashMap<Integer, Integer> wc_X;
    public static HashMap<Integer, Integer> wc_Y;
    public static HashMap<Integer, Integer> wc_Z;
    public static HashMap<Integer, Integer> area_min_X;
    public static HashMap<Integer, Integer> area_min_Y;
    public static HashMap<Integer, Integer> area_min_Z;
    public static HashMap<Integer, Integer> area_max_X;
    public static HashMap<Integer, Integer> area_max_Y;
    public static HashMap<Integer, Integer> area_max_Z;
    public static HashMap<Integer, Integer> chest_X;
    public static HashMap<Integer, Integer> chest_Y;
    public static HashMap<Integer, Integer> chest_Z;
    
    static {
        CellDataCache.cd = new CellDataProvider();
        CellDataCache.sign_X = new HashMap<Integer, Integer>();
        CellDataCache.sign_Y = new HashMap<Integer, Integer>();
        CellDataCache.sign_Z = new HashMap<Integer, Integer>();
        CellDataCache.owner = new HashMap<Integer, String>();
        CellDataCache.spawn_X = new HashMap<Integer, Integer>();
        CellDataCache.spawn_Y = new HashMap<Integer, Integer>();
        CellDataCache.spawn_Z = new HashMap<Integer, Integer>();
        CellDataCache.wc_X = new HashMap<Integer, Integer>();
        CellDataCache.wc_Y = new HashMap<Integer, Integer>();
        CellDataCache.wc_Z = new HashMap<Integer, Integer>();
        CellDataCache.area_min_X = new HashMap<Integer, Integer>();
        CellDataCache.area_min_Y = new HashMap<Integer, Integer>();
        CellDataCache.area_min_Z = new HashMap<Integer, Integer>();
        CellDataCache.area_max_X = new HashMap<Integer, Integer>();
        CellDataCache.area_max_Y = new HashMap<Integer, Integer>();
        CellDataCache.area_max_Z = new HashMap<Integer, Integer>();
        CellDataCache.chest_X = new HashMap<Integer, Integer>();
        CellDataCache.chest_Y = new HashMap<Integer, Integer>();
        CellDataCache.chest_Z = new HashMap<Integer, Integer>();
    }
    
    public static void setI(final int cell, final int data, final HashMap<Integer, Integer> map) {
        map.put(cell, data);
    }
    
    public static int getI(final int cell, final HashMap<Integer, Integer> map) {
        if (map.containsKey(cell)) {
            return map.get(cell);
        }
        Chat.print("§cError 201: Cell data §f " + cell + " doesnt exists !");
        return -1;
    }
    
    public static void setS(final int cell, final String data, final HashMap<Integer, String> map) {
        map.put(cell, data);
    }
    
    public static String getS(final int cell, final HashMap<Integer, String> map) {
        if (map.containsKey(cell)) {
            return map.get(cell);
        }
        Chat.print("§cError 202: Cell data §f " + cell + " doesnt exists !");
        return null;
    }
    
    public static void removeI(final int cell, final HashMap<Integer, Integer> map) {
        if (map.containsKey(cell)) {
            map.remove(cell);
        }
    }
    
    public static void removeS(final int cell, final HashMap<Integer, String> map) {
        if (map.containsKey(cell)) {
            map.remove(cell);
        }
    }
    
    public static void remover(final int cell) {
        removeI(cell, CellDataCache.sign_X);
        removeI(cell, CellDataCache.sign_Y);
        removeI(cell, CellDataCache.sign_Z);
        removeS(cell, CellDataCache.owner);
        removeI(cell, CellDataCache.spawn_X);
        removeI(cell, CellDataCache.spawn_Y);
        removeI(cell, CellDataCache.spawn_Z);
        removeI(cell, CellDataCache.wc_X);
        removeI(cell, CellDataCache.wc_Y);
        removeI(cell, CellDataCache.wc_Z);
        removeI(cell, CellDataCache.area_min_X);
        removeI(cell, CellDataCache.area_min_Y);
        removeI(cell, CellDataCache.area_min_Z);
        removeI(cell, CellDataCache.area_max_X);
        removeI(cell, CellDataCache.area_max_Y);
        removeI(cell, CellDataCache.area_max_Z);
        removeI(cell, CellDataCache.chest_X);
        removeI(cell, CellDataCache.chest_Y);
        removeI(cell, CellDataCache.chest_Z);
    }
    
    public static void load(final int cell) {
        setI(cell, CellDataCache.cd.getICell(cell, "Sign.X"), CellDataCache.sign_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Sign.Y"), CellDataCache.sign_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Sign.Z"), CellDataCache.sign_Z);
        setS(cell, "None", CellDataCache.owner);
        setI(cell, CellDataCache.cd.getICell(cell, "Spawn.X"), CellDataCache.spawn_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Spawn.Y"), CellDataCache.spawn_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Spawn.Z"), CellDataCache.spawn_Z);
        setI(cell, CellDataCache.cd.getICell(cell, "Wc.X"), CellDataCache.wc_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Wc.Y"), CellDataCache.wc_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Wc.Z"), CellDataCache.wc_Z);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.min.X"), CellDataCache.area_min_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.min.Y"), CellDataCache.area_min_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.min.Z"), CellDataCache.area_min_Z);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.max.X"), CellDataCache.area_max_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.max.Y"), CellDataCache.area_max_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Area.max.Z"), CellDataCache.area_max_Z);
        setI(cell, CellDataCache.cd.getICell(cell, "Chest.X"), CellDataCache.chest_X);
        setI(cell, CellDataCache.cd.getICell(cell, "Chest.Y"), CellDataCache.chest_Y);
        setI(cell, CellDataCache.cd.getICell(cell, "Chest.Z"), CellDataCache.chest_Z);
    }
    
    public static void save(final int cell) {
        CellDataCache.cd.setICell(cell, "Sign.X", getI(cell, CellDataCache.sign_X));
        CellDataCache.cd.setICell(cell, "Sign.Y", getI(cell, CellDataCache.sign_Y));
        CellDataCache.cd.setICell(cell, "Sign.Z", getI(cell, CellDataCache.sign_Z));
        CellDataCache.cd.setICell(cell, "Spawn.X", getI(cell, CellDataCache.spawn_X));
        CellDataCache.cd.setICell(cell, "Spawn.Y", getI(cell, CellDataCache.spawn_Y));
        CellDataCache.cd.setICell(cell, "Spawn.Z", getI(cell, CellDataCache.spawn_Z));
        CellDataCache.cd.setICell(cell, "Wc.X", getI(cell, CellDataCache.wc_X));
        CellDataCache.cd.setICell(cell, "Wc.Y", getI(cell, CellDataCache.wc_Y));
        CellDataCache.cd.setICell(cell, "Wc.Z", getI(cell, CellDataCache.wc_Z));
        CellDataCache.cd.setICell(cell, "Area.min.X", getI(cell, CellDataCache.area_min_X));
        CellDataCache.cd.setICell(cell, "Area.min.Y", getI(cell, CellDataCache.area_min_Y));
        CellDataCache.cd.setICell(cell, "Area.min.Z", getI(cell, CellDataCache.area_min_Z));
        CellDataCache.cd.setICell(cell, "Area.max.X", getI(cell, CellDataCache.area_max_X));
        CellDataCache.cd.setICell(cell, "Area.max.Y", getI(cell, CellDataCache.area_max_Y));
        CellDataCache.cd.setICell(cell, "Area.max.Z", getI(cell, CellDataCache.area_max_Z));
        CellDataCache.cd.setICell(cell, "Chest.X", getI(cell, CellDataCache.chest_X));
        CellDataCache.cd.setICell(cell, "Chest", getI(cell, CellDataCache.chest_Y));
        CellDataCache.cd.setICell(cell, "Chest", getI(cell, CellDataCache.chest_Z));
    }
    
    public static void reloadCell(final int cell) {
    }
}
