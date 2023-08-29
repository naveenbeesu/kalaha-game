package com.bol.kalahagame.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class KalahaGame {
    public String id;
    public String presentPlayer;
    public String winnerOfGame;
    public Player player1;
    public Player player2;
}
