package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;

// Interface defining the contract for compatibility rules
public interface CompatibilityRule {
    
    // Checks if the rule condition is satisfied based on the request IDs
    boolean isSatisfiedBy(PcBuildRequest request);
    
    // Returns the error message if the compatibility rule is violated
    String getErrorMessage();
}