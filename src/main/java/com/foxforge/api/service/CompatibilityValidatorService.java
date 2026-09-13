package com.foxforge.api.service;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.exception.IncompatibleHardwareException;
import com.foxforge.api.service.rule.CompatibilityRule;
import org.springframework.stereotype.Service;

import java.util.List;

// Service responsible for running all compatibility validations
@Service
public class CompatibilityValidatorService {

    private final List<CompatibilityRule> rules;

    // Spring IoC container automatically injects all beans implementing CompatibilityRule here
    public CompatibilityValidatorService(List<CompatibilityRule> rules) {
        this.rules = rules;
    }

    // Iterates through every configured rule and interrupts the flow if any returns false
    public void validateBuild(PcBuildRequest request) {
        for (CompatibilityRule rule : rules) {
            if (!rule.isSatisfiedBy(request)) {
                throw new IncompatibleHardwareException(rule.getErrorMessage());
            }
        }
    }
}