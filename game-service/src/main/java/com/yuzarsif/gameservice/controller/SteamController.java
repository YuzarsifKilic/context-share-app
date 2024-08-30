package com.yuzarsif.gameservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuzarsif.gameservice.client.SteamClient;
import com.yuzarsif.gameservice.service.GameSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/steam")
@RequiredArgsConstructor
public class SteamController {

    private final GameSaveService gameSaveService;

    @PostMapping("start-client")
    public String startClient() {
        gameSaveService.startClient();
        return "Client started";
    }

    @PostMapping("stop-client")
    public String stopClient() {
        gameSaveService.stopClient();
        return "Client stopped";
    }

}
