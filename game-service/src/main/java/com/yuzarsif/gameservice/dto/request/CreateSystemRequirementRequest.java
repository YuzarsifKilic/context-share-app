package com.yuzarsif.gameservice.dto.request;

import java.util.List;

public record CreateSystemRequirementRequest(
        Long osId,
        Integer memory,
        Integer storage,
        List<Long> processorIdList,
        List<Long> graphicsIdList
) {
}
