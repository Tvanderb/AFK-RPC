package dev.tvanderb.afk_rpc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@EnableWebMvc
public class SpringApp {


    private final Properties properties = new Properties();
    private final AtomicLong idCounter = new AtomicLong();

    private SpringApplication app;

    public SpringApp() {
        createApp(true);
    }

    public SpringApp(@NotNull Boolean log) {
        createApp(log);
    }

    private void createApp(boolean log) {
        app = new SpringApplication(getClass());

        if (!log) {
            properties.setProperty("spring.main.banner-mode", "off");
            properties.setProperty("logging.pattern.console", "");
        }

        app.setDefaultProperties(properties);
    }

    public void start(int port) {
        properties.setProperty("server.port", String.valueOf(port));
        app.setDefaultProperties(properties);

        app.run();
        System.out.println("INFO: API started!");
    }

    public long getNextId() {
        return idCounter.getAndIncrement();
    }

}
