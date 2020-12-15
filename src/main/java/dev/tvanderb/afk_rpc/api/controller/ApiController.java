package dev.tvanderb.afk_rpc.api.controller;

import dev.tvanderb.afk_rpc.api.Response;
import dev.tvanderb.afk_rpc.api.responses.DefaultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiController {

    @GetMapping(value = "/go-afk", produces = {"application/json", "application/xml"})
    public Response<DefaultResponse> goAfk(@RequestParam("amount") Integer amount, @RequestParam("unit") String unit) {

    }

}
