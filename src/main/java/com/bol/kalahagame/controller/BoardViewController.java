package com.bol.kalahagame.controller;

import com.bol.kalahagame.model.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kalaha")
@Slf4j
public class BoardViewController {

    @GetMapping("/")
    public String viewBoard() {
        log.info("ViewBoard endpoint is called");
        return Constants.BOARD_TEMPLATE;
    }
}
