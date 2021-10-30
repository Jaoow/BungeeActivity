package com.jaoow.bungeeactivity.model;

import com.jaoow.bungeeactivity.manager.DataManager;
import lombok.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.time.Duration;
import java.time.ZonedDateTime;

@Data
public class TimedPlayer {

    private final String playerName;

    private ZonedDateTime lastSeen = ZonedDateTime.now(); // timestamp millisecond
    private OnlineRecord global; // object

    private boolean hidden; // boolean
    private long sessionTime = 0L; // millisecond

    // Constructor
    public TimedPlayer(String playerName) {
        this.playerName = playerName;
        this.global = new OnlineRecord();
    }

    public void startSession() {
        this.lastSeen = ZonedDateTime.now();
        this.sessionTime = 0L;
    }

    public void endSession() {
        this.updateAccumulative();
        this.sessionTime = 0L;
    }


    public void updateAccumulative() {

        // Then, update the server records.
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.playerName);
        String serverName = player.getServer().getInfo().getName();
        if (DataManager.getConfig().getStringList("activity.blacklist").contains(serverName)) return;

        // Update session record.
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime lastSeen = this.lastSeen;

        long duration = Duration.between(lastSeen, currentTime).toMillis();

        this.lastSeen = currentTime;
        this.global.updateTime();

        this.sessionTime += duration;
    }
}
