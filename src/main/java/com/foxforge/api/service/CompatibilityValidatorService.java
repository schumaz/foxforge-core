package com.foxforge.api.service;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.exception.IncompatibleHardwareException;
import com.foxforge.api.service.rule.CompatibilityRule;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service orchestrator running the chain of hardware compatibility specifications.
 * Leverages Spring's Dependency Injection container to discover and inject all implementations
 * of CompatibilityRule following the Chain of Responsibility pattern.
 */
@Service
public class CompatibilityValidatorService {

    private final List<CompatibilityRule> rules;

    /**
     * Spring IoC scans and injects all components implementing CompatibilityRule into this list.
     *
     * @param rules the full collection of discovered compatibility rules
     */
    public CompatibilityValidatorService(List<CompatibilityRule> rules) {
        this.rules = rules;
    }

    /**
     * Executes the validation chain using a fail-fast strategy.
     *
     * @param request the PC build request containing component identifiers
     * @throws IncompatibleHardwareException if any compatibility specification fails
     */
    public void validateBuild(PcBuildRequest request) {
        for (CompatibilityRule rule : rules) {
            if (!rule.isSatisfiedBy(request)) {
                throw new IncompatibleHardwareException(rule.getErrorMessage());
            }
        }
    }
}
