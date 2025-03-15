package eze.dev.valdeSync.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

import eze.dev.valdeSync.Utils.utils;
import eze.dev.valdeSync.Core;
import eze.dev.valdeSync.Managers.Client.DiscordClient;
import eze.dev.valdeSync.Managers.DataBase.PlayerDataManager;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent data) {
        Player player = data.getPlayer();
        PlayerDataManager playerDataManager = new PlayerDataManager();

        if (!playerDataManager.playerExists(player.getUniqueId())) return;

        if (utils.getRank(player.getPlayerListName()).equals("miembro")) {
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
                DiscordClient.removeRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + playerDataManager.getRank(player.getUniqueId())));
                playerDataManager.updateRole(player.getUniqueId(), utils.getRank(player.getPlayerListName()));
            }, 23);
        }

        if (!playerDataManager.getRank(player.getUniqueId()).equals(utils.getRank(player.getPlayerListName()))) {
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
                if (!Core.getInstance().getConfig().getString("discord."+ utils.getRank(player.getPlayerListName())).isEmpty()) {
                    DiscordClient.removeRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + playerDataManager.getRank(player.getUniqueId())));
                    DiscordClient.syncRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + utils.getRank(player.getPlayerListName())));
                }
                playerDataManager.updateRole(player.getUniqueId(), utils.getRank(player.getPlayerListName()));
            }, 23);
        }

        //player.setPlayerListName(utils.colorMsg(ChatColor.BLUE + "「VIP PRE」" + ChatColor.WHITE + " " + player.getName())); //Fue utilizado para testear los rangos
    }
}
