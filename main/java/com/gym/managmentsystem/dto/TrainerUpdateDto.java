package com.gym.managmentsystem.dto;

import com.gym.managmentsystem.model.SessionsModel;
import com.gym.managmentsystem.model.TrainersModel;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;



public class TrainerUpdateDto {
    public TrainersModel trainersModel;

    public TrainersModel getTrainersModel() {
        return trainersModel;
    }

    public void setTrainersModel(TrainersModel trainersModel) {
        this.trainersModel = trainersModel;
    }

    public List<SessionsModel> getSessionsModelList() {
        return sessionsModelList;
    }

    public void setSessionsModelList(List<SessionsModel> sessionsModelList) {
        this.sessionsModelList = sessionsModelList;
    }

    public  List<SessionsModel> sessionsModelList;
}
