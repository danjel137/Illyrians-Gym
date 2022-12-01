package com.example.gym.controller;

import com.example.gym.model.Session;
import com.example.gym.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }

    @GetMapping("/sessions/{session_id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long session_id) {
        return sessionService.getSessionById(session_id);
    }

    @PutMapping("/sessions/{session_id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long session_id, @RequestBody Session session) {
        return sessionService.updateSession(session_id, session);
    }

    @DeleteMapping("/sessions/{session_id}")
    public ResponseEntity<Long> deleteSession(@PathVariable Long session_id) {
        return sessionService.deleteSession(session_id);
    }

}
