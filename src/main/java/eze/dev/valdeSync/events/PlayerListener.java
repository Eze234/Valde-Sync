package eze.dev.valdeSync.events;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
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
        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(player);
        String rank = user.getPrimaryGroup().toLowerCase();

        if (!playerDataManager.playerExists(player.getUniqueId())) return;

        if (rank.equals("default")) {
            DiscordClient.removeRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + playerDataManager.getRank(player.getUniqueId())));
            playerDataManager.updateRole(player.getUniqueId(), rank);
        }

        if (!playerDataManager.getRank(player.getUniqueId()).equals(rank)) {
            playerDataManager.updateRole(player.getUniqueId(), rank);
            if (!Core.getInstance().getConfig().getString("discord."+ rank).isEmpty()) {
                DiscordClient.removeRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + playerDataManager.getRank(player.getUniqueId())));
                DiscordClient.syncRank(playerDataManager.getDiscordId(player.getUniqueId()), Core.getInstance().getConfig().getString("discord.roles." + rank));
            }
        }

        //player.setPlayerListName(utils.colorMsg(ChatColor.BLUE + "「VIP PRE」" + ChatColor.WHITE + " " + player.getName())); //Fue utilizado para testear los rangos
    }
}
