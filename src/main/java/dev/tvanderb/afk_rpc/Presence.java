package dev.tvanderb.afk_rpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import org.jetbrains.annotations.NotNull;

public class Presence {

    private final String CLIENT_ID;

    private DiscordRPC lib = DiscordRPC.INSTANCE;
    private DiscordEventHandlers eventHandlers = new DiscordEventHandlers();

    public Presence(@NotNull String clientId) {
        this.CLIENT_ID = clientId;

        eventHandlers.ready = user -> {
            System.out.println("INFO: Discord RPC ready for user " + user.username + "#" + user.discriminator);
        };

        lib.Discord_Initialize(CLIENT_ID, eventHandlers, true, null);
    }

    public void setPresence(@NotNull DiscordRichPresence presence) {
        lib.Discord_UpdatePresence(presence);
    }

    public void stopPresence() {
        lib.Discord_ClearPresence();
    }

}
