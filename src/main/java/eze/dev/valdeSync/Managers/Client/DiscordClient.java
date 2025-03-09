package eze.dev.valdeSync.Managers.Client;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.ChatColor;

import eze.dev.valdeSync.Core;
import eze.dev.valdeSync.Utils.utils;

import java.util.EnumSet;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class DiscordClient extends ListenerAdapter {
    private static JDA jda;

    public static void client() {
        String token = Core.getInstance().getConfig().getString("discord.token");

        if (token == null || token.isEmpty()) {
            utils.console(utils.colorMsg(ChatColor.RED + "[Valde-Sync] El token del bot se encuentra vacío."));
            return;
        }

        try {
            jda = (JDA) JDABuilder.createLight(token, EnumSet.noneOf(GatewayIntent.class))
                    .addEventListeners(new DiscordClient()).build();
            CommandListUpdateAction commands = jda.updateCommands();

            commands.addCommands(
                    Commands.slash("sync", "Sincroniza tu cuenta de Discord con tu cuenta de Minecraft.")
                            .setContexts(InteractionContextType.ALL)
                            .setIntegrationTypes(IntegrationType.ALL)
                            .addOption(STRING, "nick", "Tu nombre de usuario de Minecraft.", true)
            );

            commands.queue();
        } catch (Exception e) {
            utils.console(ChatColor.RED + "[Valde-Sync] Error al conectar con Discord: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onReady(ReadyEvent data) {
        data.getJDA().getPresence().setActivity(Activity.watching(Objects.requireNonNull(Core.getInstance().getConfig().getString("minecraft.name"))));
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] Conexión a Discord( " + data.getJDA().getSelfUser().getName() + " ) exitosa!"));
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent data) {
        if (data.getName().equals("sync")) {
            String tag = data.getOption("nick").getAsString();
            String code = utils.genCode();

            if (!data.getMember().getRoles().stream().anyMatch(role -> role.getId().equals(Core.getInstance().getConfig().getString("discord.sub")))) {
                data.reply("Debés tener rol subscriptor para utilizar este comando.")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            if (!utils.getPlayer(tag)) {
                String message = "Asegurate de que estas conectado en la network para verificar tu cuenta.";
                data.reply(message)
                        .setEphemeral(true)
                        .queue();
                return;
            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(Core.getInstance().getConfig().getString("minecraft.name"), data.getJDA().getSelfUser().getEffectiveAvatarUrl())
                    .setTitle("Vincula tu cuenta de Minecraft de " + Core.getInstance().getConfig().getString("minecraft.name"))
                    .setDescription("**¿Cómo sincronizar?**\n" + "Para sincronizar tu cuenta de minecraft deberás seguir estos pasos:\n 1. Estar conectado en la network\n" + "2. Escribir el código: \"" + code + " \" en el chat")
                    .setColor(3756993)
                    .setImage("https://cdn.discordapp.com/attachments/848181072835772466/1344840183303901194/image.png?ex=67c25f85&is=67c10e05&hm=0cbac64fe319e419fb86f2990a5c416e9fbcbb66a282f437e61d2e16b121dfc3&")
                    .setFooter("Nota: Este comando solo se puede ejecutar una vez.");

            data.reply("> **Tu código de verificación es:** ||" + code + "||")
                    .addEmbeds(embed.build())
                    .setEphemeral(true)
                    .queue();
        }
    }

    public static void shutdown() {
        if (jda != null) {
            jda.shutdown();
            utils.console(utils.colorMsg(ChatColor.RED + "[Valde-Sync] El cliente de Discord fue desconectado."));
        }
    }

}