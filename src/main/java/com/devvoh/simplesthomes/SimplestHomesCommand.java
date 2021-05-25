package com.devvoh.simplesthomes;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record SimplestHomesCommand(SimplestHomesPlugin plugin) implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!com.devvoh.simplesthomes.SimplestHomesTools.isPlayer(sender)) {
            return false;
        }

        Player player = com.devvoh.simplesthomes.SimplestHomesTools.getPlayer(sender);

        if (com.devvoh.simplesthomes.SimplestHomesTools.isCommand(command, "home")) {
            return sendToHome(player);
        }

        if (com.devvoh.simplesthomes.SimplestHomesTools.isCommand(command, "sethome")) {
            return this.setHome(player);
        }

        return false;
    }

    private Boolean sendToHome(Player player) {
        String playerUUID = player.getUniqueId().toString();

        double x = this.plugin.getConfig().getDouble(playerUUID + ".x");
        double y = this.plugin.getConfig().getDouble(playerUUID + ".y");
        double z = this.plugin.getConfig().getDouble(playerUUID + ".z");
        int yaw = this.plugin.getConfig().getInt(playerUUID + ".yaw");
        int pitch = this.plugin.getConfig().getInt(playerUUID + ".pitch");

        if (x == 0 && y == 0 && z == 0) {
            player.sendMessage(
                "§5[SimplestHomes]§c[ERROR] §eNo home set! First run /sethome to set your current location as your home."
            );

            return false;
        }

        player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));

        player.sendTitle(
            "§6Welcome home, " + player.getDisplayName(),
            null,
            10,
            70,
            20
        );

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        return true;
    }

    private Boolean setHome(Player player) {
        Double x = player.getLocation().getX();
        Double y = player.getLocation().getY() + 0.25; // bump it a little
        Double z = player.getLocation().getZ();
        Float yaw = player.getLocation().getYaw();
        Float pitch = player.getLocation().getPitch();

        String playerUUID = player.getUniqueId().toString();

        player.sendMessage("§5[SimplestHomes]§a[OK] §eSet home to: "
            + String.format("%.2f", x)
            + " "
            + String.format("%.2f", y)
            + " "
            + String.format("%.2f", z)
            + " ("
            + String.format("%.2f", yaw)
            + " "
            + String.format("%.2f", pitch)
            + ")"
        );

        this.plugin.getConfig().set(playerUUID + ".x", x);
        this.plugin.getConfig().set(playerUUID + ".y", y);
        this.plugin.getConfig().set(playerUUID + ".z", z);
        this.plugin.getConfig().set(playerUUID + ".yaw", yaw);
        this.plugin.getConfig().set(playerUUID + ".pitch", pitch);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        this.plugin.saveConfig();

        return true;
    }
}
