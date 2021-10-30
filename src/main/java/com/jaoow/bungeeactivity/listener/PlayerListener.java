package com.jaoow.bungeeactivity.listener;

import com.jaoow.bungeeactivity.BungeeActivity;
import com.jaoow.bungeeactivity.model.TimedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {

    private final BungeeActivity proxy;

    public PlayerListener(BungeeActivity proxy) {
        this.proxy = proxy;
    }

    @EventHandler
    public void onConnect(PostLoginEvent event) {
        TimedPlayer timedPlayer = proxy.getTimedPlayerManager().getOrCreate(event.getPlayer().getName());

        // Start network session.
        timedPlayer.startSession();
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        TimedPlayer timedPlayer = proxy.getTimedPlayerManager().getOrCreate(event.getPlayer().getName());

        // End network session.
        timedPlayer.endSession();

        // Save player data.
        proxy.getTimedPlayerDAO().saveOne(timedPlayer);
    }
}
