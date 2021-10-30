package com.jaoow.bungeeactivity.dao.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaoow.bungeeactivity.model.TimedPlayer;
import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import com.jaoow.sql.executor.result.SimpleResultSet;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

public final class TimedPlayerAdapter implements SQLResultAdapter<TimedPlayer> {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
            .setPrettyPrinting().create();

    @Override
    public TimedPlayer adaptResult(@NotNull SimpleResultSet resultSet) {
        return GSON.fromJson((String) resultSet.get("data"), TimedPlayer.class);
    }
}
