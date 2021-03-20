package ru.rey.rposepluginaddon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rey on 19.03.2021
 */
public class BindCommand implements CommandExecutor {

    private final Core instance = Core.getInstance();
    private final DataManager dataManager = instance.getDataManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if (dataManager.userExists(p)) {
            dataManager.userUpdate(p);
            p.sendMessage(instance.getConfig().getString("messages.disabled"));
        }
        else {
            dataManager.userUpdate(p);
            p.sendMessage(instance.getConfig().getString("messages.enabled"));
        }

        return true;
    }

}
