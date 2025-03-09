package eze.dev.valdeSync;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import eze.dev.valdeSync.Utils.utils;
import eze.dev.valdeSync.Managers.Client.DiscordClient;

import org.jetbrains.annotations.NotNull;

public final class Core extends JavaPlugin {
    private static Core instance;

    @Override
    public void onEnable() {
        instance = this;
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Plugin iniciado"));
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Iniciando Conexión con Discord..."));
        DiscordClient.client();
    }

    @Override
    public void onDisable() {
        instance = null;
        DiscordClient.shutdown();
        utils.console(utils.colorMsg(ChatColor.RED + "[Valde-Sync] Plugin apagado"));
    }

    @NotNull
    public static Core getInstance() {
        return instance;
    }
}
