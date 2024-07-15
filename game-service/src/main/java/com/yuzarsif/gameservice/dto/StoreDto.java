package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Store;
import com.yuzarsif.gameservice.model.StoreType;

import java.util.List;
import java.util.Set;

public record StoreDto(
        Long id,
        StoreType storeName,
        Float price,
        Float discount,
        Float finalPrice,
        String discountStartDate,
        String discountEndDate,
        String discountStartTime,
        String discountEndTime
) {

    public static StoreDto convert(Store from) {
        return new StoreDto(
                from.getId(),
                from.getStoreName(),
                from.getPrice(),
                from.getDiscount(),
                from.getFinalPrice(),
                from.getDiscountStartDate().toString(),
                from.getDiscountEndDate().toString(),
                from.getDiscountStartTime().toString(),
                from.getDiscountEndTime().toString());
    }

    public static List<StoreDto> convertList(Set<Store> from) {
        return from.stream().map(StoreDto::convert).toList();
    }
}
