package com.example.gym.service;

import com.example.gym.exception.SessionNotFoundException;
import com.example.gym.model.Session;
import com.example.gym.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<List<Session>> getAllSessions() {
        return ResponseEntity.ok(sessionRepository.findAll());
    }

    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        return new ResponseEntity<>(sessionRepository.saveAndFlush(session), HttpStatus.CREATED);
    }

    public ResponseEntity<Session> getSessionById(Long session_id) {
        Session session = sessionRepository.findById(session_id).orElseThrow(() -> new SessionNotFoundException("Session not exist with id :" + session_id));
        return ResponseEntity.ok(session);
    }

    public ResponseEntity<Session> updateSession(Long session_id, Session session) {
        Session existingSession = sessionRepository.findById(session_id).orElseThrow(() -> new SessionNotFoundException("Session not exist with id :" + session_id));
        existingSession.setDescription(session.getDescription());
        existingSession.setTitle(session.getTitle());
        existingSession.setSession_type(session.getSession_type());
        existingSession.setDuration(session.getDuration());
        existingSession.setRate(session.getRate());
        Session updatedSession = sessionRepository.save(session);
        return ResponseEntity.ok(updatedSession);

    }

    public ResponseEntity<Long> deleteSession(@PathVariable Long session_id) {
        Session session = sessionRepository.findById(session_id)
                .orElseThrow(() -> new SessionNotFoundException("Session not exist with id :" + session_id));
        sessionRepository.delete(session);
        return new ResponseEntity<>(session_id, HttpStatus.OK);
    }
}
