package com.yuzarsif.seriesservice.dto.request;

import java.util.Set;

public record SearchByList(
        Set<Long> artistsId
) {
}
