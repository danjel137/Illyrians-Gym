package com.gym.managmentsystem.repository;

import com.gym.managmentsystem.model.ManagerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IManagerRepository extends JpaRepository<ManagerModel, Long> {
}
