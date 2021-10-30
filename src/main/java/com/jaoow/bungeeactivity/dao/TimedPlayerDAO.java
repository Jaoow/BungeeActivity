package com.jaoow.bungeeactivity.dao;

import com.jaoow.bungeeactivity.dao.adapter.TimedPlayerAdapter;
import com.jaoow.bungeeactivity.model.TimedPlayer;
import com.jaoow.sql.executor.SQLExecutor;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class TimedPlayerDAO {

    private static final String TABLE = "onlinetime_players";
    private final SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "name VARCHAR(16) NOT NULL PRIMARY KEY UNIQUE," +
                "data TEXT" +
                ");");
    }

    public TimedPlayer selectOne(String name) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE name = ?",
                statement -> statement.set(1, name),
                TimedPlayerAdapter.class
        );
    }

    public Set<TimedPlayer> selectAll() {
        return selectAll("");
    }

    public Set<TimedPlayer> selectAll(String preferences) {
        return sqlExecutor.resultManyQuery(
                "SELECT * FROM " + TABLE + " " + preferences,
                statement -> {
                },
                TimedPlayerAdapter.class
        );
    }

    public void saveOne(TimedPlayer timedPlayer) {
        sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?)", TABLE),
                statement -> {
                    statement.set(1, timedPlayer.getPlayerName());
                    statement.set(2, TimedPlayerAdapter.GSON.toJson(timedPlayer));
                }
        );
    }
}
