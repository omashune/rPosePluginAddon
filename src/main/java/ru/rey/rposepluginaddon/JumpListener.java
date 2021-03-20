package ru.rey.rposepluginaddon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.armagidon.poseplugin.api.PosePluginAPI;
import ru.armagidon.poseplugin.api.poses.EnumPose;
import ru.armagidon.poseplugin.api.poses.IPluginPose;
import ru.armagidon.poseplugin.api.poses.PoseBuilder;

/**
 * Created by Rey on 19.03.2021
 */
public class JumpListener implements Listener {

    private final DataManager dataManager = Core.getInstance().getDataManager();
    private final PosePluginAPI api = PosePluginAPI.getAPI();

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Vector velocity = p.getVelocity();

        if (velocity.getY() > 0) {
            double jumpVelocity = 0.42F;
            PotionEffect jumpPotion = p.getPotionEffect(PotionEffectType.JUMP);
            if (jumpPotion != null) {
                jumpVelocity += (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;
            }

            if (p.getLocation().getBlock().getType() != Material.LADDER
                    && Double.compare(velocity.getY(), jumpVelocity) == 0
                    && p.isSneaking()
                    && dataManager.userExists(p)) {

                e.setCancelled(true);

                IPluginPose pose = PoseBuilder.builder(EnumPose.SITTING).build(p);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> {
                    api.getPlayerMap().getPosePluginPlayer(p).changePose(pose);
                }, 10);
            }
        }
    }

}
