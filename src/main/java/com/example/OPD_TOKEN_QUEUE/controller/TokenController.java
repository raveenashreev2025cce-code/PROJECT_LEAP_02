package com.example.OPD_TOKEN_QUEUE.controller;
import java.util.HashMap;
import java.util.Map;
import com.example.OPD_TOKEN_QUEUE.model.Token;
import com.example.OPD_TOKEN_QUEUE.service.TokenService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import static tools.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // Generate token
    @PostMapping
    public Token generateToken(@RequestParam Long doctorId,
                               @RequestParam Long patientId,
                               @RequestParam(defaultValue = "false") boolean priority) {
        return tokenService.generateToken(doctorId, patientId, priority);
    }

    // Get today's tokens for a doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Token> getTodayTokens(@PathVariable Long doctorId) {
        return tokenService.getTodayTokens(doctorId);
    }

    // Get token by ID
    @GetMapping("/{id}")
    public Token getToken(@PathVariable Long id) {
        return tokenService.getTokenById(id);
    }

    // Serve next token
    @PutMapping("/doctor/{doctorId}/next")
    public Token serveNextToken(@PathVariable Long doctorId) {
        return tokenService.serveNextToken(doctorId);
    }

    // Delete token
    @DeleteMapping("/{id}")
    public String deleteToken(@PathVariable Long id) {
        tokenService.deleteToken(id);
        return "Token deleted successfully";
    }
    @GetMapping("/{id}/waiting-time")
    public Map<String, Object> getWaitingTime(@PathVariable Long id) {

        int waitingTime = tokenService.getWaitingTime(id);

        Map<String, Object> response = new HashMap<>();
        response.put("tokenId", id);
        response.put("estimatedWaitingTime", waitingTime);
        response.put("unit", "minutes");

        return response;
    }
    }

