package com.bol.kalahagame.controller;

import com.bol.kalahagame.dto.KalahaGame;
import com.bol.kalahagame.service.KalahaGameSetupServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kalaha")
public class KalahaGameController {

    private final KalahaGameSetupServiceImpl kalahaGameServiceImpl;

    public KalahaGameController(KalahaGameSetupServiceImpl kalahaGameServiceImpl) {
        this.kalahaGameServiceImpl = kalahaGameServiceImpl;
    }

    @GetMapping("startGame")
    public KalahaGame startGame(@RequestParam String id) {
        return kalahaGameServiceImpl.startGame(id);
    }
}
