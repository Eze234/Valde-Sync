package eze.dev.valdeSync;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import eze.dev.valdeSync.Utils.utils;
import eze.dev.valdeSync.Managers.Client.DiscordClient;
import eze.dev.valdeSync.Managers.DataBase.Connection;
import eze.dev.valdeSync.commands.*;

import org.jetbrains.annotations.NotNull;

public final class Core extends JavaPlugin {
    private static Core instance;
    private Connection mongo;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        luckPermsProvider();
        instance = this;
        loadCommands();
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Plugin iniciado"));
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Iniciando Conexión con Discord..."));
        DiscordClient.client();
        mongo = new Connection();
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Base de Datos iniciada"));
    }

    public void luckPermsProvider() {
        PluginManager plManager = Bukkit.getPluginManager();
        Plugin luckPermsPl = plManager.getPlugin("LuckPerms");

        if (luckPermsPl == null || !luckPermsPl.isEnabled()) {
            utils.console(utils.colorMsg(luckPermsPl.isEnabled() ? ChatColor.RED + "[Valde-Sync] El plugin LuckPerms no se encuentra instalado." : ChatColor.YELLOW + "[Valde-Sync] El plugin LuckPerms no se encuentra activado."));
        } else {
            utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] LuckPerms detectado"));

            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

            if (provider != null) {
                LuckPerms api = provider.getProvider();
            } else {
                utils.console(ChatColor.RED + "[Valde-Sync] Error al conectar LuckPerms al plugin.");
            }
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        if (mongo != null) {
            mongo.close();
        }
        DiscordClient.shutdown();
        utils.console(utils.colorMsg(ChatColor.RED + "[Valde-Sync] Plugin apagado"));
    }

    @NotNull
    public static Core getInstance() {
        return instance;
    }

    public void loadCommands() {
        getCommand("sync").setExecutor(new sync());
    }
}
