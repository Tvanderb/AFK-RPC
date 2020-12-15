package dev.tvanderb.afk_rpc;

import dev.tvanderb.afk_rpc.api.SpringApp;
import org.jetbrains.annotations.NotNull;

public class Main {

    private static final SpringApp app = new SpringApp(false);
    private static Presence presence;

    public static void main(String[] args) {
        int port = 8080;
        String clientId;

        try {
            clientId = args[1];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            System.out.println("ERROR: Client ID missing. Make sure to make a Discord application for this.\nERROR: Usage: <port number> <client id>");
            Runtime.getRuntime().exit(1);
            return;
        }

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException | NullPointerException ignore) {
            System.out.println("ERROR: Invalid port number. Using default. (8080)");
        }

        if (clientId == null) {
            System.out.println("ERROR: Client ID missing. Make sure to make a Discord application for this.");
            Runtime.getRuntime().exit(1);
            return;
        }

        presence = new Presence(clientId);

        System.out.println("INFO: Starting up API...");
        app.start(port);
    }

    @NotNull
    public static SpringApp getApp() {
        return app;
    }

    @NotNull
    public static Presence getPresence() {
        return presence;
    }

}
