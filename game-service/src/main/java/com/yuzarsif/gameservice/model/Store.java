package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private StoreType storeName;
    private Float price;
    private Float discount;
    private Float finalPrice;
    private Date discountStartDate;
    private Date discountEndDate;
    private LocalTime discountStartTime;
    private LocalTime discountEndTime;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
