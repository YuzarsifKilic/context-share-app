package com.yuzarsif.gameservice.dto.request;

public record CreateDiscountRequest(
        Float discount,
        Float finalPrice,
        String discountStartDate,
        String discountEndDate,
        String discountStartTime,
        String discountEndTime
) {
}
