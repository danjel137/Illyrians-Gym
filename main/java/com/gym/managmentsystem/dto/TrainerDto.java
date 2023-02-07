package com.gym.managmentsystem.dto;

import com.gym.managmentsystem.model.SessionsModel;
import com.gym.managmentsystem.model.TrainersModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



public class TrainerDto {
    public List<TrainersModel> trainersModels;
    public List<SessionsModel> sessionsModels;

    public List<TrainersModel> getTrainersModels() {
        return trainersModels;
    }

    public void setTrainersModels(List<TrainersModel> trainersModels) {
        this.trainersModels = trainersModels;
    }

    public List<SessionsModel> getSessionsModels() {
        return sessionsModels;
    }

    public void setSessionsModels(List<SessionsModel> sessionsModels) {
        this.sessionsModels = sessionsModels;
    }
}
