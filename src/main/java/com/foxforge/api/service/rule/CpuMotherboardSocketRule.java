package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import org.springframework.stereotype.Component;

// Rule to validate if the CPU socket matches the Motherboard socket
@Component
public class CpuMotherboardSocketRule implements CompatibilityRule {

    // TODO: Inject CPU and Motherboard repositories via constructor (e.g., CpuRepository and MotherboardRepository)

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // TODO: Validate if the IDs are null
        if (request.cpuId() == null || request.motherboardId() == null) {
            return true; // Or false depending on your business rules for incomplete builds
        }

        // TODO: 1. Fetch the CPU from the database using request.cpuId()
        // TODO: 2. Fetch the Motherboard from the database using request.motherboardId()
        // TODO: 3. Compare their sockets, for example:
        // return cpu.getSocket().equalsIgnoreCase(motherboard.getSocket());

        return true; 
    }

    @Override
    public String getErrorMessage() {
        return "The chosen CPU is not compatible with the selected Motherboard's socket.";
    }
}