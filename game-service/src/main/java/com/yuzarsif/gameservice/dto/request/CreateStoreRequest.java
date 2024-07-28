package com.yuzarsif.gameservice.dto.request;

import com.yuzarsif.gameservice.model.StoreType;

public record CreateStoreRequest(
        StoreType storeName,
        Float price,
        String url,
        Long gameId,
        Long platformId
) {
}
