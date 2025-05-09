package com.chrisp1985.cockroachdemo.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    private UUID id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer console_id;

    @NotNull
    private Integer rating;
}
