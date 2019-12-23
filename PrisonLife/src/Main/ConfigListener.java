// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigListener extends YamlConfiguration
{
    private File file;
    private String defaults;
    private JavaPlugin plugin;
    
    public ConfigListener(final JavaPlugin plugin, final String fileName) {
        this(plugin, fileName, null);
    }
    
    public ConfigListener(final JavaPlugin plugin, final String fileName, final String fileNames) {
        this.plugin = plugin;
        this.defaults = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);
        this.reload();
    }
    
    public void reload() {
        if (!this.file.exists()) {
            try {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
            }
            catch (IOException exception) {
                exception.printStackTrace();
                this.plugin.getLogger().severe("Error while creating file " + this.file.getName());
            }
        }
        try {
            this.load(this.file);
            if (this.defaults == null) {
                final InputStreamReader reader = new InputStreamReader(this.plugin.getResource(this.defaults));
                final FileConfiguration defaultsConfig = (FileConfiguration)YamlConfiguration.loadConfiguration((Reader)reader);
                this.setDefaults((Configuration)defaultsConfig);
                this.options().copyDefaults(true);
                reader.close();
                this.save();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
            this.plugin.getLogger().severe("Error while loading file " + this.file.getName());
        }
        catch (InvalidConfigurationException exception2) {
            exception2.printStackTrace();
            this.plugin.getLogger().severe("Error while loading file " + this.file.getName());
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
            this.plugin.getLogger().severe("Error while loading file " + this.file.getPath() + ", File Name" + this.file.getName());
        }
    }
    
    public void save() {
        try {
            this.options().indent(2);
            this.save(this.file);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            this.plugin.getLogger().severe("Error while saving file " + this.file.getName());
        }
    }
}
