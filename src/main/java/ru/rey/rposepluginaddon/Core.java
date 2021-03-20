package ru.rey.rposepluginaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private static Core instance;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        dataManager = new DataManager(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new JumpListener(), this);
        getCommand("shiftsit").setExecutor(new BindCommand());
    }

    public static Core getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
