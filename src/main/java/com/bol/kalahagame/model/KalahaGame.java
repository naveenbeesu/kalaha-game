package com.bol.kalahagame.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class KalahaGame {
    public String id;
    public String presentPlayer;
    public String winnerOfGame;
    public PlayerDto player1;
    public PlayerDto player2;
}
