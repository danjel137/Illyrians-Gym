package com.gym.managmentsystem.serviceImpl;


import com.gym.managmentsystem.model.SessionsModel;
import com.gym.managmentsystem.repository.ISessionsRepository;
import com.gym.managmentsystem.service.ISessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionsServiceImpl implements ISessionsService {
    @Autowired
    private ISessionsRepository sessionRepository;

    public SessionsServiceImpl(ISessionsRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<SessionsModel> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public SessionsModel saveSession(SessionsModel sessionsModel) {
        return sessionRepository.save(sessionsModel);
    }

    @Override
    public SessionsModel getSessionById(Long id) {
        return sessionRepository.getById(id);
    }

    @Override
    public SessionsModel updateSession(SessionsModel sessionsModel) {
        return sessionRepository.save(sessionsModel);
    }

    @Override
    public void deleteSessionById(Long id) {
        sessionRepository.deleteById(id);
    }
}
