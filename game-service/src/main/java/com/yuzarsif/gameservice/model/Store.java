package com.yuzarsif.gameservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

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
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Float discount;
    private Float finalPrice;
    private Date discountStartDate;
    private Date discountEndDate;
    private LocalTime discountStartTime;
    private LocalTime discountEndTime;
    private String url;
    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) && storeName == store.storeName && Objects.equals(price, store.price) && Objects.equals(currency, store.currency) && Objects.equals(discount, store.discount) && Objects.equals(finalPrice, store.finalPrice) && Objects.equals(discountStartDate, store.discountStartDate) && Objects.equals(discountEndDate, store.discountEndDate) && Objects.equals(discountStartTime, store.discountStartTime) && Objects.equals(discountEndTime, store.discountEndTime) && Objects.equals(url, store.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, price, currency, discount, finalPrice, discountStartDate, discountEndDate, discountStartTime, discountEndTime, url);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName=" + storeName +
                ", price=" + price +
                ", currency='" + currency +
                ", discount=" + discount +
                ", finalPrice=" + finalPrice +
                ", discountStartDate=" + discountStartDate +
                ", discountEndDate=" + discountEndDate +
                ", discountStartTime=" + discountStartTime +
                ", discountEndTime=" + discountEndTime +
                ", url='" + url +
                '}';
    }
}
