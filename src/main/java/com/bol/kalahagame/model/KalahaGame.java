package com.bol.kalahagame.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class KalahaGame {
    public String id;
    public String presentPlayer;
    public String winnerOfGame;
    public Player player1;
    public Player player2;
}
