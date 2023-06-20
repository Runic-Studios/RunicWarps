package com.runicrealms.runicwarps;

import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.PaperCommandManager;
import com.runicrealms.runicwarps.commands.SetWarpCommand;
import com.runicrealms.runicwarps.commands.WarpCommand;
import com.runicrealms.runicwarps.commands.WarpsCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RunicWarps extends JavaPlugin {
    private static RunicWarps plugin;
    private WarpManager warpManager;
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        RunicWarps.plugin = this;
        this.warpManager = new WarpManager();
        this.commandManager = new PaperCommandManager(this);

        this.commandManager.getCommandConditions().addCondition("is-player", context -> {
            if (!(context.getIssuer().getIssuer() instanceof Player)) {
                throw new ConditionFailedException("This command cannot be run from console!");
            }
        });

        this.commandManager.getCommandCompletions().registerAsyncCompletion("warps", context -> this.warpManager.getWarps()
                .stream()
                .filter(warp -> warp.startsWith(context.getInput()))
                .toList());

        this.commandManager.registerCommand(new WarpCommand());
        this.commandManager.registerCommand(new WarpsCommand());
        this.commandManager.registerCommand(new SetWarpCommand());
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
