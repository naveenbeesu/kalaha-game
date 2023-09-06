package com.bol.kalahagame.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="player_id")
    private Long playerId;

    @Column(columnDefinition = "JSON")
    private String stonesInPits;

    private int stonesInBigPit;
}
