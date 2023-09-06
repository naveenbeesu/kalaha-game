package com.bol.kalahagame.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerDto {
    public int [] stonesInPits;
    public int stonesInBigPit;
}
