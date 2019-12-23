// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configs
{
    private File configFile;
    private FileConfiguration config;
    
    public Configs(final List<String> folder, final String name) {
        if (folder == null) {
            this.configFile = new File(Main.getInstance().getDataFolder(), String.valueOf(name) + ".yml");
        }
        else {
            String folders = null;
            for (final String s : folder) {
                folders = String.valueOf(folders) + s + "/";
            }
            this.configFile = new File(Main.getInstance().getDataFolder(), String.valueOf(folders) + name + ".yml");
        }
        try {
            this.configFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
    }
    
    public Configs(final String name) {
        this.configFile = new File(Main.getInstance().getDataFolder(), String.valueOf(name) + ".yml");
        this.createConfig("v1.0");
        (this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile)).set("ConfigVersion", (Object)1.0);
        try {
            this.config.save(this.configFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public FileConfiguration getFile() {
        return this.config;
    }
    
    public void createConfig(final String configVersion) {
        if (!this.configFile.exists()) {
            try {
                this.configFile.createNewFile();
                final YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(this.configFile);
                UserConfig.options().header("\nConfig Version is: " + configVersion + "\n");
                UserConfig.save(this.configFile);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void reload() {
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
    }
    
    public void save() {
        try {
            this.config.save(this.configFile);
        }
        catch (IOException e) {
            Chat.print("Cannot save the config!");
            Chat.getException(2, e.getStackTrace()[0].getLineNumber(), this.getClass());
        }
    }
}
