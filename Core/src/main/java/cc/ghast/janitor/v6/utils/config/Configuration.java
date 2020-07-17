package cc.ghast.janitor.v6.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {
    private final JavaPlugin plugin;
    private final String name;
    @Getter
    private File file;
    @Getter
    private YamlConfiguration config;

    public Configuration(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        this.name = name;
        load();
    }

    public void load() {
        file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdir();
            } else {
                file.getAbsoluteFile().getParentFile().mkdir();
            }
            plugin.saveResource(name, true);

        }
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            Chat.sendConsoleMessage("&cError loading configuration file " + name);
            ex.printStackTrace();
        }
    }


    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            Chat.sendConsoleMessage("&cError saving config file " + name);
        }
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public ItemStack getItemStack(String path) {
        return getConfig().getItemStack(path);
    }

    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public Long getLong(String path) {
        return getConfig().getLong(path);
    }

    public Object get(String path) {
        return getConfig().get(path);
    }

    public void set(String path, Object value) {
        getConfig().set(path, value);
        save();
    }


    public YamlConfigurationOptions getOptions() {
        return getConfig().options();
    }

    public void setLocation(String path, Location location) {
        getConfig().set(path + ".X", location.getX());
        getConfig().set(path + ".Y", location.getY());
        getConfig().set(path + ".Z", location.getZ());
        getConfig().set(path + ".WORLD", location.getWorld().getName());
        getConfig().set(path + ".YAW", location.getYaw());
        getConfig().set(path + ".PITCH", location.getPitch());
        save();
    }

    public Location getLocation(String path) {
        return new Location(Bukkit.getWorld(getConfig().getString(path + ".WORLD")), getConfig().getDouble(path + ".X"), getConfig().getDouble(path + ".Y"), getConfig().getDouble(path + ".Z"), getConfig().getLong(path + ".YAW"), getConfig().getLong(path + ".PITCH"));
    }
}
