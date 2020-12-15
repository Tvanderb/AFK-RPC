package dev.tvanderb.afk_rpc;

import dev.tvanderb.afk_rpc.api.SpringApp;
import org.jetbrains.annotations.NotNull;

public class Main {

    private static final SpringApp app = new SpringApp(true);

    public static void main(String[] args) {
        System.out.println("Starting up API...");
        app.start();
    }

    @NotNull
    public static SpringApp getApp() {
        return app;
    }

}
