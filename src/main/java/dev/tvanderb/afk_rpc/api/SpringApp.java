package dev.tvanderb.afk_rpc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.NotNull;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@EnableWebMvc
public class SpringApp {

    private SpringApplication app;

    private final AtomicLong idCounter = new AtomicLong();

    public SpringApp() {
        createApp(true);
    }

    public SpringApp(@NotNull Boolean log) {
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
        System.out.println("API started!");
    }

    public long getNextId() {
        return idCounter.getAndIncrement();
    }

}
