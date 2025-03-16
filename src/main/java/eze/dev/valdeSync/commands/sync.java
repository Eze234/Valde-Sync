package eze.dev.valdeSync.commands;

import eze.dev.valdeSync.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import eze.dev.valdeSync.Utils.utils;
import eze.dev.valdeSync.Managers.DataBase.PlayerDataManager;
import eze.dev.valdeSync.Managers.Client.DiscordClient;

public class sync implements CommandExecutor {
    PluginManager plManager = Bukkit.getPluginManager();
    Plugin luckPermsPl = plManager.getPlugin("LuckPerms");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(utils.colorMsg(ChatColor.RED + "[Valde-Sync] Utiliza este comando en el juego."));
            return true;
        }

        if (luckPermsPl == null && !luckPermsPl.isEnabled()) {
            sender.sendMessage(utils.colorMsg(ChatColor.RED + "LuckPerms no se encuentra habilitado."));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Debes proporcionar el código dado por el bot.\nhttps://discord.com/invite/planetavalde");
            return true;
        }

        PlayerDataManager playerDataManager = new PlayerDataManager();
        Player player = (Player) sender;
        String rank = utils.getRank(player.getPlayerListName());



        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] ListName es: " + player.getPlayerListName() + " su rango:" + utils.getRank(player.getPlayerListName())));

        if (playerDataManager.getSynced(player.getUniqueId())) {
            player.sendMessage(utils.colorMsg(ChatColor.RED + "Esta cuenta ya esta sincronizada con " + ChatColor.WHITE + DiscordClient.getDiscordUserName(playerDataManager.getDiscordId(player.getUniqueId()))));
            return true;
        }

        if (!playerDataManager.equalCode(player.getUniqueId(), args[0])) {
            player.sendMessage(utils.colorMsg(ChatColor.RED + "El código no concuerda al que dio el bot."));
            return true;
        }

        try {
            playerDataManager.setSynced(player.getUniqueId(), rank);
            if (rank.equals("miembro")) {
                player.sendMessage(utils.colorMsg(ChatColor.GREEN + "La cuenta " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " fue sincronizada con el discord de " + ChatColor.BLUE + DiscordClient.getDiscordUserName(playerDataManager.getDiscordId(player.getUniqueId()))));
            } else {
                player.sendMessage(utils.colorMsg(ChatColor.GREEN + "La cuenta " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " fue sincronizada con el discord de " + ChatColor.BLUE + DiscordClient.getDiscordUserName(playerDataManager.getDiscordId(player.getUniqueId()))));
                player.sendMessage(utils.colorMsg(ChatColor.GREEN + "Tu rango " + ChatColor.WHITE + rank + ChatColor.GREEN + " fue sincronizado con el Discord"));
                DiscordClient.syncRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + rank));
            }
        } catch (Exception e) {
            player.sendMessage(utils.colorMsg(ChatColor.RED + "Ocurrió un error al sincronizar tu cuenta de Discord con la de Minecraft..."));
            e.printStackTrace();
        }

        return true;
    }
}
