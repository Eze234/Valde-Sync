package eze.dev.valdeSync.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.UUID;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
    static String packageCore = "eze.dev.valdeSync";

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

    public static boolean getPlayer(String tag) {
        Player player = Bukkit.getServer().getPlayer(tag);

        return player != null;
    }

    public static String getPlayerName(String tag) {
        Player player = Bukkit.getServer().getPlayer(tag);

        return player.getName();
    }

    public static UUID getPlayerUUID(String tag) {
        Player player = Bukkit.getServer().getPlayer(tag);

        return player.getUniqueId();
    }

    public static void eventHandler(Plugin plugin) {
        String packageName = packageCore + ".events";

        try {
            String jarPath = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName().replace("/", ".");

                if (name.startsWith(packageName) && name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6);
                    Class<?> clazz = Class.forName(className);

                    if (Listener.class.isAssignableFrom(clazz)) {
                        Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                        Bukkit.getPluginManager().registerEvents(listener, plugin);
                        console(ChatColor.BLUE + "[EzeCore.Utils.Event] Event " + clazz.getSimpleName() + " loaded!");
                    }
                }
            }

            jarFile.close();
        } catch (Exception e) {
            console(ChatColor.BLUE + "[EzeCore.Utils.Event] error " + e.getMessage());
        }
    }

    public static String getRank(String nick) {
        Pattern pattern = Pattern.compile("「(.*?)」");
        Matcher matcher = pattern.matcher(nick);

        if (matcher.find()) {
            return matcher.group(1).toLowerCase().replaceAll(" ", "_");
        } else {
            return "miembro";
        }
    }

}
