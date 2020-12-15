package dev.tvanderb.afk_rpc.api;

import com.ts_mc.smcb.internal.logging.LogLevel;
import com.ts_mc.smcb.main.Main;
import com.ts_mc.smcb.main.Utils;
import com.ts_mc.smcb.main.discord.StormBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

@SpringBootApplication
@EnableWebMvc
public class StormAPI {

//    private static final String MASTER_AUTH_KEY = "";
    private static final String FACTIONS_AUTH_KEY = "HofarSfo.UlzopfyhLMP.idjcahjaibhcg.KRUDGU3UGBZE2YLQNFGTIU2UGNZEAS2FLE======";
    private static final String TOWNY_AUTH_KEY = "HofarSfo.OfjyxLMP.idjcahhidfeia.KRUDGU3UGBZE2YLQNFGTIU2UGNZEAS2FLE======";
    private static final String HUB_AUTH_KEY = "HofarSfo.IvsLMP.idjcahhhfegej.KRUDGU3UGBZE2YLQNFGTIU2UGNZEAS2FLE======";

    private SpringApplication app;

    private final AtomicLong idCounter = new AtomicLong();

    private StormBot bot;

    public StormAPI() {};

    public StormAPI(@NotNull StormBot bot) {
        this.bot = bot;

        createApp(true);
    }

    public StormAPI(@NotNull StormBot bot, @NotNull Boolean log) {
        this.bot = bot;

        createApp(log);
    }

    private void createApp(boolean log) {
        app = new SpringApplication(getClass());

        Properties properties = new Properties();
        if (!log) {
            properties.setProperty("spring.main.banner-mode", "off");
            properties.setProperty("logging.pattern.console", "");
        }

        app.setDefaultProperties(properties);
    }

    public void start() {
        app.run();
        Main.getLoggingSystem().log(getClass(), LogLevel.FINE, "StormBot API up and running.");
    }

    public long getNextId() {
        return idCounter.getAndIncrement();
    }

    @Configuration
    @EnableWebMvc
    public class AppConfiguration implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            BiConsumer<HttpServletRequest, HttpServletResponse> validCall = (req, res) -> {};

            BiConsumer<HttpServletRequest, HttpServletResponse> invalidCall = (req, res) -> {
                String message = "[" + Utils.Request.getAsRequestLog(req) + "] Unauthorized request.";
                Main.getLoggingSystem().log(getClass(), LogLevel.WARNING, message);
            };

            // TODO: Create and add master auth token to authorization managers.

            AuthorizationManager factionsAuthMan = new AuthorizationManager(
                    "X-Storm-Authorization",
                    validCall,
                    invalidCall
            );


            factionsAuthMan.setPath("/factions");
            factionsAuthMan.addKey(FACTIONS_AUTH_KEY);

            AuthorizationManager townyAuthMan = new AuthorizationManager(
                    "X-Storm-Authorization",
                    validCall,
                    invalidCall
            );

            townyAuthMan.setPath("/towny");
            townyAuthMan.addKey(TOWNY_AUTH_KEY);

            AuthorizationManager hubAuthMan = new AuthorizationManager(
                    "X-Storm-Authorization",
                    validCall,
                    invalidCall
            );

            hubAuthMan.setPath("/hub");
            hubAuthMan.addKey(HUB_AUTH_KEY);

            registry.addInterceptor(factionsAuthMan);
            registry.addInterceptor(townyAuthMan);
            registry.addInterceptor(hubAuthMan);

            HandlerInterceptor logger = new HandlerInterceptor() {

                @Override
                public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                    String msg = Utils.Request.getAsRequestLog(request) + "  \"" + request.getHeader("User-Agent") + "\"  " + response.getStatus();
                    Main.getLoggingSystem().log(LogLevel.INFO, msg);
                }

            };

            registry.addInterceptor(logger);
        }

    }

}
