package com.foxforge.api.controller;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.service.CompatibilityValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller providing endpoints for PC build operations and validation
@RestController
@RequestMapping("/api/builds")
public class PcBuildController {

    private final CompatibilityValidatorService validatorService;

    // Dependency injection via constructor
    public PcBuildController(CompatibilityValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    // Endpoint to validate component compatibility for a given PC build configuration
    @PostMapping("/validate")
    public ResponseEntity<String> validateBuild(@RequestBody PcBuildRequest request) {
        validatorService.validate(request);
        return ResponseEntity.ok("Build configuration is compatible!");
    }
}
