package com.bol.kalahagame.controller;

import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.Step;
import com.bol.kalahagame.service.KalahaGameSetupServiceImpl;
import com.bol.kalahagame.service.MoveStonesServiceImpl;
import com.bol.kalahagame.service.ValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kalaha")
public class KalahaGameController {

    private final MoveStonesServiceImpl moveStonesServiceImpl;
    private final KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl;
    private final ValidationService validationService;

    public KalahaGameController(MoveStonesServiceImpl moveStonesServiceImpl, KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl, ValidationService validationService) {
        this.moveStonesServiceImpl = moveStonesServiceImpl;
        this.kalahaGameSetupServiceImpl = kalahaGameSetupServiceImpl;
        this.validationService = validationService;
    }


    @GetMapping("startGame")
    public KalahaGame startGame(@RequestParam String id) {
        return kalahaGameSetupServiceImpl.startGame(id);
    }

    @PostMapping("moveStones")
    public KalahaGame moveStones(@RequestBody Step step) {
        validationService.validateInputRequest(step);
        return moveStonesServiceImpl.moveStones(step);
    }
}
