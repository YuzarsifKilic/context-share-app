package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.SystemRequirement;

import java.util.List;

public record SystemRequirementDto(
    Long id,
    OsDto os,
    List<ProcessorDto> processor,
//    Integer memory,
//    Integer storage,
    List<GraphicsDto> graphics
) {

    public static SystemRequirementDto convert(SystemRequirement from) {
        return new SystemRequirementDto(
                from.getId(),
                OsDto.convert(from.getOs()),
                ProcessorDto.convertList(from.getProcessors()),
//                from.getMemory(),
//                from.getStorage(),
                GraphicsDto.convertList(from.getGraphics())
        );
    }
}
