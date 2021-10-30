package com.jaoow.bungeeactivity.manager;

import com.jaoow.bungeeactivity.dao.TimedPlayerDAO;
import com.jaoow.bungeeactivity.model.TimedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TimedPlayerManager {

    private final TimedPlayerDAO timedPlayerDAO;
    private final Map<String, TimedPlayer> players = new ConcurrentHashMap<>();

    public TimedPlayerManager(TimedPlayerDAO timedPlayerDAO) {
        this.timedPlayerDAO = timedPlayerDAO;
    }

    public void loadUser(TimedPlayer timedPlayer) {
        this.players.put(timedPlayer.getPlayerName(), timedPlayer);
    }

    @NotNull
    public TimedPlayer getOrCreate(String nickname) {
        return Optional.ofNullable(players.get(nickname)).orElseGet(() -> {
            TimedPlayer timedPlayer = timedPlayerDAO.selectOne(nickname);

            if (timedPlayer == null) {
                timedPlayer = new TimedPlayer(nickname);
                timedPlayerDAO.saveOne(timedPlayer);
            }

            this.players.put(nickname, timedPlayer);
            return timedPlayer;
        });
    }

    @Nullable
    public TimedPlayer getByName(String nickname) {
        return players.get(nickname);
    }

}
