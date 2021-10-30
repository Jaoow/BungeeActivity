package com.jaoow.bungeeactivity.command;

import com.jaoow.bungeeactivity.BungeeActivity;
import com.jaoow.bungeeactivity.manager.DataManager;
import com.jaoow.bungeeactivity.manager.MessageManager;
import com.jaoow.bungeeactivity.model.TimedPlayer;
import com.jaoow.bungeeactivity.utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ActivityCommand extends Command {

    private final BungeeActivity plugin;

    public ActivityCommand(BungeeActivity plugin) {
        super("activity");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length >= 1 && args[0].equalsIgnoreCase("RELOAD")) {
                if (player.hasPermission("activity.reload")) {
                    DataManager.reload();
                    player.sendMessage(MessageManager.getReloadMessage());
                } else {
                    player.sendMessage(MessageManager.getActivityNoPermissionMessage());
                }
            }

            if (player.hasPermission("activity.view")) {
                if (args.length == 1) {
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(MessageManager.getTargetNotFoundMessage());
                    } else {
                        sendStatistics(player, target);
                    }
                    return;
                }
                sendStatistics(player, player);
            } else {
                player.sendMessage(MessageManager.getActivityNoPermissionMessage());
            }
        }
    }

    private void sendStatistics(ProxiedPlayer player, ProxiedPlayer target) {
        TimedPlayer timedPlayer = plugin.getTimedPlayerManager().getOrCreate(target.getName());
        timedPlayer.updateAccumulative();

        for (String rawLine : DataManager.getMessages().getStringList("activity.format")) {
            String format = rawLine.replace("%player%", target.getName());
            format = format.replace("%day%", Utils.getFormattedTime(timedPlayer.getGlobal().getDailyTime()));
            format = format.replace("%week%", Utils.getFormattedTime(timedPlayer.getGlobal().getWeeklyTime()));
            format = format.replace("%month%", Utils.getFormattedTime(timedPlayer.getGlobal().getMonthlyTime()));
            format = format.replace("%total%", Utils.getFormattedTime(timedPlayer.getGlobal().getTotalTime()));
            format = format.replace("%session%", Utils.getFormattedTime(timedPlayer.getSessionTime()));
            player.sendMessage(new TextComponent(Utils.color(format)));
        }
    }
}
