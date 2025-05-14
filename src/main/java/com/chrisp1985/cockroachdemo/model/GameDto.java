package com.chrisp1985.cockroachdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {

    private UUID id;
    private String name;
    private Integer console_id;
    private Integer rating;
}
