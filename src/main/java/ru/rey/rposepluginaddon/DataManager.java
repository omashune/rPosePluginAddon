package ru.rey.rposepluginaddon;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Rey on 19.03.2021
 */
public class DataManager {

    private final Core plugin;
    private FileConfiguration dataConfig;
    private File configFile;

    public DataManager(Core plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "data.yml");
        }

        dataConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource("data.yml");

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null) {
            return;
        }

        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "data.yml");
        }

        if (!configFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
    }

    public boolean userExists(Player p) {
        return getConfig().getStringList("users").contains(p.getName().toLowerCase());
    }

    public void userUpdate(Player p) {
        String name = p.getName().toLowerCase();
        List<String> users = getConfig().getStringList("users");

        if (userExists(p)) {
            users.remove(name);
        } else {
            users.add(name);
        }

        getConfig().set("users", users);
        saveConfig();
    }

}
