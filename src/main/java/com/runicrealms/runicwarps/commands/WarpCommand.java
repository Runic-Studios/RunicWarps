package com.runicrealms.runicwarps.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import com.runicrealms.runicwarps.RunicWarps;
import com.runicrealms.runicwarps.WarpManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("warp")
@Conditions("is-player")
@CommandPermission("runic.warp")
public class WarpCommand extends BaseCommand {
    @Default
    @CommandCompletion("@warps @nothing")
    public void onWarp(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(RunicWarps.PREFIX.append(Component.text("/warp <warp name>", NamedTextColor.RED)));
            return;
        }

        WarpManager warpManager = RunicWarps.getInstance().getWarpManager();

        Location location = warpManager.getWarp(args[0]);

        if (location == null || !location.isWorldLoaded()) {
            player.sendMessage(RunicWarps.PREFIX.append(Component.text("You have entered an invalid warp!", NamedTextColor.RED)));
            return;
        }

        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(RunicWarps.PREFIX.append(Component.text("You have warped to " + args[0] + "!", NamedTextColor.GREEN)));
    }
}
