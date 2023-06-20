package com.runicrealms.runicwarps;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RunicWarps extends JavaPlugin {
    private static RunicWarps plugin;
    private WarpManager warpManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        RunicWarps.plugin = this;
        this.warpManager = new WarpManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public WarpManager getWarpManager() {
        return this.warpManager;
    }

    @NotNull
    public static RunicWarps getInstance() {
        if (RunicWarps.plugin == null) {
            throw new IllegalStateException("You have tried to access the singleton instance before the plugin was enabled!");
        }

        return RunicWarps.plugin;
    }
}
