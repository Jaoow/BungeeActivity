package com.jaoow.bungeeactivity.manager;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DataManager {

    private static Configuration config;
    private static Configuration messages;

    private static File configFile;
    private static File messagesFile;

    public static void setup(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        try {
            if (!configFile.exists()) {
                Files.copy(plugin.getResourceAsStream("config.yml"), configFile.toPath());
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            if (!messagesFile.exists()) {
                Files.copy(plugin.getResourceAsStream("messages.yml"), messagesFile.toPath());
            }
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        try {
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    public static Configuration getMessages() {
        return messages;
    }
}
