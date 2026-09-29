package com.example.OPD_TOKEN_QUEUE.service.impl;

import com.example.OPD_TOKEN_QUEUE.model.*;
import com.example.OPD_TOKEN_QUEUE.repository.*;
import com.example.OPD_TOKEN_QUEUE.service.TokenService;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            DoctorRepository doctorRepository,
                            PatientRepository patientRepository) {
        this.tokenRepository = tokenRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Token generateToken(Long doctorId, Long patientId, boolean priority) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        LocalDate today = LocalDate.now();
        List<Token> tokens =
                tokenRepository.findByDoctorIdAndTokenDate(doctorId, today);

        if (priority && tokens.stream().anyMatch(
                t -> t.isPriority() && "WAITING".equals(t.getStatus()))) {
            throw new RuntimeException("Priority token already waiting");
        }

        int number = tokens.stream()
                .mapToInt(Token::getTokenNumber)
                .max().orElse(0) + 1;

        Token token = new Token();
        token.setTokenNumber(number);
        token.setTokenDate(today);
        token.setPriority(priority);
        token.setStatus("WAITING");
        token.setDoctor(doctor);
        token.setPatient(patient);

        return tokenRepository.save(token);
    }

    @Override
    public List<Token> getTodayTokens(Long doctorId) {
        return tokenRepository.findByDoctorIdAndTokenDate(
                doctorId, LocalDate.now());
    }

    @Override
    public Token getTokenById(Long id) {
        return tokenRepository.findById(id).orElseThrow();
    }

    @Override
    public Token serveNextToken(Long doctorId) {
        List<Token> tokens = getTodayTokens(doctorId);

        tokens.stream()
                .filter(t -> "SERVING".equals(t.getStatus()))
                .forEach(t -> {
                    t.setStatus("COMPLETED");
                    tokenRepository.save(t);
                });

        Token next = tokens.stream()
                .filter(t -> "WAITING".equals(t.getStatus()))
                .sorted(Comparator.comparing(Token::isPriority).reversed()
                        .thenComparingInt(Token::getTokenNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No waiting tokens"));

        next.setStatus("SERVING");
        return tokenRepository.save(next);
    }

    @Override
    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
        @Override
        public int getWaitingTime(Long tokenId) {

            Token current = getTokenById(tokenId);

            if (!"WAITING".equals(current.getStatus())) {
                return 0;
            }

            List<Token> tokens = tokenRepository.findByDoctorIdAndTokenDate(
                    current.getDoctor().getId(),
                    current.getTokenDate());

            long ahead = tokens.stream()
                    .filter(t -> "SERVING".equals(t.getStatus())
                            || ("WAITING".equals(t.getStatus())
                            && (t.isPriority() && !current.isPriority()
                            || t.isPriority() == current.isPriority()
                            && t.getTokenNumber() < current.getTokenNumber())))
                    .count();

            return (int) ahead * 10;
        }
    }
