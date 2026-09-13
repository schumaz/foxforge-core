package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;

/**
 * Contract for atomic hardware compatibility rules implementing the Specification Pattern.
 */
public interface CompatibilityRule {

    /**
     * Evaluates whether the given PC build request satisfies the specific compatibility rule.
     *
     * @param request the PC build request containing component identifiers
     * @return true if compatible according to this rule, false otherwise
     */
    boolean isSatisfiedBy(PcBuildRequest request);

    /**
     * Returns a human-readable semantic error message explaining why the compatibility check failed.
     *
     * @return semantic error message for failure diagnostics
     */
    String getErrorMessage();
}
