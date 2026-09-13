package com.foxforge.api.domain.dto;

// Record representing the request to build a PC, containing the IDs of each chosen part
public record PcBuildRequest(
        Long cpuId,
        Long motherboardId,
        Long ramId,
        Long gpuId,
        Long psuId,
        Long storageId,
        Long caseId
) {}