package com.chrisp1985.cockroachdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LowestRankedGameDto {
    private String gameName;
    private String consoleName;
}
