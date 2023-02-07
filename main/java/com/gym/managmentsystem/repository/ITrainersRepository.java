package com.gym.managmentsystem.repository;

import com.gym.managmentsystem.model.TrainersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainersRepository extends JpaRepository<TrainersModel,Long> {
}
