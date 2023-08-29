package com.bol.kalahagame.controller;

import com.bol.kalahagame.model.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kalaha")
public class BoardViewController {

    @GetMapping("/")
    public String viewBoard() {
        return Constants.BOARD_TEMPLATE;
    }
}
