package com.foxforge.api.service;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.exception.IncompatibleHardwareException;
import com.foxforge.api.service.rule.CompatibilityRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompatibilityValidatorServiceTest {

    @Mock
    private CompatibilityRule rule1;

    @Mock
    private CompatibilityRule rule2;

    @Test
    @DisplayName("Should pass validation silently when all rules in chain are satisfied")
    void shouldPassValidationWhenAllRulesSatisfied() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        when(rule1.isSatisfiedBy(request)).thenReturn(true);
        when(rule2.isSatisfiedBy(request)).thenReturn(true);

        CompatibilityValidatorService service = new CompatibilityValidatorService(List.of(rule1, rule2));

        // Act & Assert
        assertDoesNotThrow(() -> service.validate(request));
        verify(rule1).isSatisfiedBy(request);
        verify(rule2).isSatisfiedBy(request);
    }

    @Test
    @DisplayName("Should throw IncompatibleHardwareException and stop chain immediately on first failed rule (Fail-Fast)")
    void shouldThrowExceptionAndStopChainOnFirstFailure() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        String expectedErrorMessage = "CPU socket is incompatible with Motherboard.";

        when(rule1.isSatisfiedBy(request)).thenReturn(false);
        when(rule1.getErrorMessage()).thenReturn(expectedErrorMessage);

        CompatibilityValidatorService service = new CompatibilityValidatorService(List.of(rule1, rule2));

        // Act & Assert
        IncompatibleHardwareException exception = assertThrows(
                IncompatibleHardwareException.class,
                () -> service.validate(request)
        );

        assertEquals(expectedErrorMessage, exception.getMessage());
        verify(rule1).isSatisfiedBy(request);
        // Golden check: verify rule2 was NEVER evaluated because rule1 already failed
        verify(rule2, never()).isSatisfiedBy(any());
    }

    @Test
    @DisplayName("Should handle empty rule list without throwing any exceptions")
    void shouldHandleEmptyRuleListGracefully() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        CompatibilityValidatorService service = new CompatibilityValidatorService(List.of());

        // Act & Assert
        assertDoesNotThrow(() -> service.validate(request));
    }
}
