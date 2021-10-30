package com.jaoow.bungeeactivity.manager;

import com.jaoow.bungeeactivity.utils.Utils;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageManager {

    public static String prefix() {
        return Utils.color(DataManager.getMessages().getString("core.prefix"));
    }

    public static TextComponent getReloadMessage() {
        return new TextComponent(prefix() + Utils.color(DataManager.getMessages().getString("activity.reloaded")));
    }

    public static TextComponent getActivityNoPermissionMessage() {
        return new TextComponent(prefix() + Utils.color(DataManager.getMessages().getString("activity.no-permission")));
    }

    public static TextComponent getTargetNotFoundMessage() {
        return new TextComponent(prefix() + Utils.color(DataManager.getMessages().getString("activity.target-not-found")));
    }
}
