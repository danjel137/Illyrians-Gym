package com.gym.managmentsystem.repository;

import com.gym.managmentsystem.model.SessionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ISessionsRepository extends JpaRepository<SessionsModel,Long> {
}
