package dev.tvanderb.afk_rpc.api.controller;

import club.minnced.discord.rpc.DiscordRichPresence;
import dev.tvanderb.afk_rpc.Main;
import dev.tvanderb.afk_rpc.api.Response;
import dev.tvanderb.afk_rpc.api.exception.BadRequestException;
import dev.tvanderb.afk_rpc.api.responses.DefaultResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/")
public class ApiController {

    private enum TimeUnit {

        SECOND(1.0),
        MINUTE(60.0),
        HOUR(60.0 * 60.0),
        DAY(60.0 * 60.0 * 24.0);

        private final Double multiplier;
        TimeUnit(@NotNull Double multiplier) {
            this.multiplier = multiplier;
        }

        @NotNull
        protected Double getMultiplier() {
            return multiplier;
        }

        public static TimeUnit fromString(@NotNull String s) {
            return switch (s.toLowerCase()) {
                case "s" -> TimeUnit.SECOND;
                case "m" -> TimeUnit.MINUTE;
                case "h" -> TimeUnit.HOUR;
                case "d" -> TimeUnit.DAY;
                default -> null;
            };
        }

    }

    @GetMapping(value = "/go-afk", produces = {"application/json", "application/xml"})
    public Response<DefaultResponse> goAfk(@RequestParam("amount") Double amount, @RequestParam("unit") String unit, HttpServletRequest req) throws BadRequestException {
        TimeUnit tu = TimeUnit.fromString(unit);
        if (tu == null) {
            throw new BadRequestException("Unknown time unit \"" + unit + "\".", req);
        }

        long end = (long) (Double.parseDouble(String.valueOf(System.currentTimeMillis())) + amount * 1000.0 * tu.getMultiplier());

        System.out.println("INFO: Received go-afk request. End time: " + new Date(end));

        DiscordRichPresence drp = new DiscordRichPresence();

        drp.endTimestamp = end;
        drp.startTimestamp = System.currentTimeMillis();
        drp.details = "Unavailable.";
        drp.state = "Estimated time remaining below.";

        Main.getPresence().setPresence(drp);
        return new Response<>(Main.getApp().getNextId(), 200, new DefaultResponse("Started presence. End time: " + new Date(end)));
    }

    @GetMapping(value = "/stop", produces = {"application/json", "application/xml"})
    public Response<DefaultResponse> back() {
        System.out.println("INFO: Received stop request. Stopping. ");

        Main.getPresence().stopPresence();
        return new Response<>(Main.getApp().getNextId(), 200, new DefaultResponse("Stopped"));
    }

}
