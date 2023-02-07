package com.gym.managmentsystem.dto;

import com.gym.managmentsystem.model.ClientInfoModel;
import com.gym.managmentsystem.model.ClientsModel;
import com.gym.managmentsystem.model.TrainersModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



public class ClientInfoDto {
    //Student
    public List<ClientsModel> clientList;
    public List<TrainersModel> trainerList;
    public List<ClientInfoModel> clientInfoList;

    public List<ClientsModel> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientsModel> clientList) {
        this.clientList = clientList;
    }

    public List<TrainersModel> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(List<TrainersModel> trainerList) {
        this.trainerList = trainerList;
    }

    public List<ClientInfoModel> getClientInfoList() {
        return clientInfoList;
    }

    public void setClientInfoList(List<ClientInfoModel> clientInfoList) {
        this.clientInfoList = clientInfoList;
    }
}
