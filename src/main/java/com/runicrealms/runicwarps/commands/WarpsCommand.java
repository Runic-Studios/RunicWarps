package com.runicrealms.runicwarps.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.runicrealms.runicwarps.RunicWarps;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import java.util.Iterator;

@CommandAlias("warps")
@CommandPermission("runic.warp")
public class WarpsCommand extends BaseCommand {
    @CatchUnknown
    @Default
    private void onExecute(CommandSender sender) {
        TextComponent.Builder builder = Component.text()
                .append(RunicWarps.PREFIX)
                .append(Component.text("Available warps: ", NamedTextColor.GREEN));

        Iterator<String> iterator = RunicWarps.getInstance().getWarpManager().getWarps().iterator();

        while (iterator.hasNext()) {
            String warp = iterator.next();

            builder.append(Component.text(" " + warp + (iterator.hasNext() ? "," : ""), NamedTextColor.GREEN)
                    .clickEvent(ClickEvent.suggestCommand("/warp " + warp))
                    .hoverEvent(HoverEvent.showText(Component.text("Click here to warp to " + warp + "!", NamedTextColor.GREEN))));
        }

        sender.sendMessage(builder.build());
    }
}
