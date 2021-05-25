package com.devvoh.simplesthomes;

import org.bukkit.plugin.java.JavaPlugin;

public class SimplestHomesPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getCommand("home").setExecutor(new SimplestHomesCommand(this));
        getCommand("sethome").setExecutor(new SimplestHomesCommand(this));
    }
}
