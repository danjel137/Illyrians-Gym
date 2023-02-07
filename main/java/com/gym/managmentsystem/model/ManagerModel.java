package com.gym.managmentsystem.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "manager")
public class ManagerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincreament
    @Column(name = "manager_id")
    private Long managerId;
    @Column(name = "manager_name")
    private String managerName;
    @Column(name = "manager_email")
    private String managerEmail;
    @Column(name = "manager_password")
    private String managerPassword;
    @Column(name = "tcNumber")
    private Long tcNumber;
    @Column(name = "manager_phone")
    private String managerPhone;
    @Column(name = "manager_address")
    private String managerAddress;
    @Column(name = "manager_gender")
    private String managerGender;
    @Temporal(TemporalType.DATE)
    private Date recordTime;



    @OneToMany(mappedBy = "managerModel",  cascade = CascadeType.ALL)
    private Set<ClientsModel> clientsModels = new HashSet<>();
    public Set<ClientsModel> getClientsModels() {
        return clientsModels;
    }
    public void setClientsModel(Set<ClientsModel> clientsModels) {
        this.clientsModels = clientsModels;
        for (ClientsModel b : clientsModels) {
            b.setManagerModel(this);
        }
    }


    @OneToMany(mappedBy = "managerModel", cascade = CascadeType.ALL)
    private Set<TrainersModel>  trainersModels = new HashSet<>();
    public Set<TrainersModel> getTrainersModel() {
        return trainersModels;
    }
    public void setTrainersModel(Set<TrainersModel> trainersModels) {
        this.trainersModels = trainersModels;
        for (TrainersModel b : trainersModels) {
            b.setManagerModel(this);
        }
    }

    public Long getManagerId() {
        return this.managerId;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public String getManagerEmail() {
        return this.managerEmail;
    }

    public String getManagerPassword() {
        return this.managerPassword;
    }

    public Long getTcNumber() {
        return this.tcNumber;
    }

    public String getManagerPhone() {
        return this.managerPhone;
    }

    public String getManagerAddress() {
        return this.managerAddress;
    }

    public String getManagerGender() {
        return this.managerGender;
    }

    public Date getRecordTime() {
        return this.recordTime;
    }

    public Set<TrainersModel> getTrainersModels() {
        return this.trainersModels;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public void setTcNumber(Long tcNumber) {
        this.tcNumber = tcNumber;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public void setManagerGender(String managerGender) {
        this.managerGender = managerGender;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public void setClientsModels(Set<ClientsModel> clientsModels) {
        this.clientsModels = clientsModels;
    }

    public void setTrainersModels(Set<TrainersModel> trainersModels) {
        this.trainersModels = trainersModels;
    }


}
