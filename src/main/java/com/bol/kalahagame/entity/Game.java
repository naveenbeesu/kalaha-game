package com.bol.kalahagame.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game {
    @Id
    public String gameId;
    @NonNull
    public String presentPlayer;
    public String winnerOfGame;
    @ManyToOne(cascade = CascadeType.ALL)
    public Player player1;
    @ManyToOne(cascade = CascadeType.ALL)
    public Player player2;
}