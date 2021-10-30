package com.jaoow.bungeeactivity;

import com.jaoow.bungeeactivity.command.ActivityCommand;
import com.jaoow.bungeeactivity.dao.TimedPlayerDAO;
import com.jaoow.bungeeactivity.listener.PlayerListener;
import com.jaoow.bungeeactivity.manager.DataManager;
import com.jaoow.bungeeactivity.manager.TimedPlayerManager;
import com.jaoow.sql.connector.SQLConnector;
import com.jaoow.sql.connector.type.impl.MySQLDatabaseType;
import com.jaoow.sql.executor.SQLExecutor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

@Getter
public final class BungeeActivity extends Plugin {

    private SQLConnector sqlConnector;
    private TimedPlayerDAO timedPlayerDAO;
    private TimedPlayerManager timedPlayerManager;

    @Override
    public void onEnable() {
        // Setup DataManager
        DataManager.setup(this);

        // Init MySQL.
        try {
            sqlConnector = this.configureSqlProvider(DataManager.getConfig().getSection("mysql"));
            timedPlayerDAO = new TimedPlayerDAO(new SQLExecutor(sqlConnector));
            timedPlayerManager = new TimedPlayerManager(timedPlayerDAO);

            // Create table.
            timedPlayerDAO.createTable();

            // Load all users.
            timedPlayerDAO.selectAll().forEach(timedPlayerManager::loadUser);

        } catch (SQLException e) {
            getLogger().warning("Failed to connect to database. Shutting down.");
            return;
        }

        // Register Commands
        getProxy().getPluginManager().registerCommand(this, new ActivityCommand(this));

        // Register Listeners
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    private SQLConnector configureSqlProvider(@NotNull Configuration section) throws SQLException {
        return MySQLDatabaseType.builder()
                .address(section.getString("address"))
                .username(section.getString("username"))
                .password(section.getString("password"))
                .database(section.getString("database") + "?autoReconnect=true")
                .build()
                .connect();
    }
}
