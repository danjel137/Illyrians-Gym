package com.gym.managmentsystem.service;

import com.gym.managmentsystem.model.TrainersModel;

import java.util.List;

public interface ITrainersService {
    List<TrainersModel> getAllTrainers();
    TrainersModel saveTrainer(TrainersModel trainersModel);
    TrainersModel getTrainerById(Long id);
    TrainersModel updateTrainer(TrainersModel trainersModel);
    void deleteTrainerById(Long id);
}
