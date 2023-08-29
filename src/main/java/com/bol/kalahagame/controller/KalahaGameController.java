package com.bol.kalahagame.controller;

import com.bol.kalahagame.dto.KalahaGame;
import com.bol.kalahagame.dto.Step;
import com.bol.kalahagame.service.KalahaGameSetupServiceImpl;
import com.bol.kalahagame.service.MoveStonesServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kalaha")
public class KalahaGameController {

    private final MoveStonesServiceImpl moveStonesServiceImpl;
    private final KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl;

    public KalahaGameController(MoveStonesServiceImpl moveStonesServiceImpl, KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl) {
        this.moveStonesServiceImpl = moveStonesServiceImpl;
        this.kalahaGameSetupServiceImpl = kalahaGameSetupServiceImpl;
    }


    @GetMapping("startGame")
    public KalahaGame startGame(@RequestParam String id) {
        return kalahaGameSetupServiceImpl.startGame(id);
    }

    @PostMapping("moveStones")
    public KalahaGame moveStones(@RequestBody Step step) {
        return moveStonesServiceImpl.moveStones(step);
    }
}
