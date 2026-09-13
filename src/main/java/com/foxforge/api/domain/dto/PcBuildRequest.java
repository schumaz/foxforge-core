package com.foxforge.api.domain.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object representing a PC build request submitted by the client.
 * Uses Java 21 Record for native immutability and concise syntax.
 *
 * @param cpuId         ID of the selected processor
 * @param motherboardId ID of the selected motherboard
 * @param gpuId         ID of the selected graphics card
 * @param ramId         ID of the selected RAM kit
 * @param psuId         ID of the selected power supply unit
 * @param caseId        ID of the selected computer chassis
 * @param coolerId      Optional ID of the selected CPU cooler
 * @param storageId     Optional ID of the primary storage drive
 */
public record PcBuildRequest(
        @NotNull(message = "CPU ID is required")
        Long cpuId,

        @NotNull(message = "Motherboard ID is required")
        Long motherboardId,

        @NotNull(message = "GPU ID is required")
        Long gpuId,

        @NotNull(message = "RAM ID is required")
        Long ramId,

        @NotNull(message = "PSU ID is required")
        Long psuId,

        @NotNull(message = "Case ID is required")
        Long caseId,

        Long coolerId,

        Long storageId
) {}
