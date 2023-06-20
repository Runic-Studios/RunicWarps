package com.runicrealms.runicwarps.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.runicrealms.runicwarps.RunicWarps;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("warps")
@CommandPermission("runic.warp")
public class WarpsCommand extends BaseCommand {
    @CatchUnknown
    @Default
    private void onExecute(CommandSender sender) {
        sender.sendMessage(Component.text().append(Component.text("Available warps: " + StringUtils.join(RunicWarps.getInstance().getWarpManager().getWarps(), ","), NamedTextColor.GREEN)));
    }
}
