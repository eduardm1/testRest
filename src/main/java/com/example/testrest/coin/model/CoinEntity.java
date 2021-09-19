package com.example.testrest.coin.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Coin")
public class CoinEntity {

    @Id
    private int id;

    private String name;

    private Double price;
}
