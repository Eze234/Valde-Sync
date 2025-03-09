package eze.dev.valdeSync.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class utils {
    public static void console(String message) {
        ConsoleCommandSender logger = Bukkit.getConsoleSender();
        logger.sendMessage(message);
    }

    public static String colorMsg(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String genCode() {
        String dict2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(dict2.length());
            code.append(dict2.charAt(index));
        }

        return code.toString();
    }

    public static boolean getPlayer (String tag) {
        Player player = Bukkit.getServer().getPlayer(tag);

        return player != null;
    }
}
