package com.bol.kalahagame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Step {
    public String id;
    public int pitIndex;
    public String player;
}
