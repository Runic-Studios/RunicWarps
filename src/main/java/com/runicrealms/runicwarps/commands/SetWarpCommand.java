package com.runicrealms.runicwarps.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.runicrealms.runicwarps.RunicWarps;
import com.runicrealms.runicwarps.WarpManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("setwarp")
@CommandPermission("runic.warp")
public class SetWarpCommand extends BaseCommand {
    @Default
    @CatchUnknown
    public void onHelp(CommandSender sender) {
        sender.sendMessage(Component.text().append(Component.text("/setwarp <add|remove> <warp name>", NamedTextColor.RED)));
    }

    @Subcommand("add")
    @Conditions("is-player")
    @CommandCompletion("@nothing")
    @Syntax("<warp name>")
    public void onAdd(Player player, String[] args) {
        if (args.length != 1) {
            this.onHelp(player);
            return;
        }

        WarpManager warpManager = RunicWarps.getInstance().getWarpManager();

        if (warpManager.addWarp(args[0], player.getLocation())) {
            player.sendMessage(RunicWarps.PREFIX.append(Component.text("Successfully added the " + args[0] + " warp!", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(RunicWarps.PREFIX.append(Component.text("The " + args[0] + " warp already exists!", NamedTextColor.RED)));
        }
    }

    @Subcommand("remove")
    @CommandCompletion("@warps @nothing")
    public void onRemove(CommandSender sender, String[] args) {
        if (args.length != 1) {
            this.onHelp(sender);
            return;
        }

        WarpManager warpManager = RunicWarps.getInstance().getWarpManager();

        if (warpManager.removeWarp(args[0])) {
            sender.sendMessage(RunicWarps.PREFIX.append(Component.text("Successfully removed the " + args[0] + " warp!", NamedTextColor.GREEN)));
        } else {
            sender.sendMessage(RunicWarps.PREFIX.append(Component.text("The " + args[0] + " warp does not exist!", NamedTextColor.RED)));
        }
    }
}
