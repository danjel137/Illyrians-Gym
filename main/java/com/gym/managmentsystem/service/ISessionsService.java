package com.gym.managmentsystem.service;

import com.gym.managmentsystem.model.SessionsModel;


import java.util.List;

public interface ISessionsService {
    List<SessionsModel> getAllSessions();
    SessionsModel saveSession(SessionsModel sessionsModel);
    SessionsModel getSessionById(Long id);
    SessionsModel updateSession(SessionsModel sessionsModel);
    void deleteSessionById(Long id);
}
