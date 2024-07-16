package com.yuzarsif.gameservice.dto.request;

import java.util.List;

public record CreateSystemRequirementRequest(
        Long osId,
        Long processorId,
        Integer memory,
        Integer storage,
        List<Long> graphicsIdList
) {
}
