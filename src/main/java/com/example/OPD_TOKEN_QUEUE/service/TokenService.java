package com.example.OPD_TOKEN_QUEUE.service;

import com.example.OPD_TOKEN_QUEUE.model.Token;
import java.util.List;

public interface TokenService {

    Token generateToken(Long doctorId, Long patientId, boolean priority);

    List<Token> getTodayTokens(Long doctorId);

    Token getTokenById(Long id);

    Token serveNextToken(Long doctorId);

    void deleteToken(Long id);
    int getWaitingTime(Long tokenId);
}