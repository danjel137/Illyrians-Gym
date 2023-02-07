package com.gym.managmentsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "trainers")
public class TrainersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincreament
    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(name = "tcNumber")
    private Long tcNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surName")
    private String surName;

    @Column(name = "phoneNumber")
    private Long phoneNumber;

    @Column(name = "mail")
    private String mail;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.DATE)
    private Date recordTime;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_model_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ManagerModel managerModel;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_model_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private SessionsModel sessionsModel;


    public Long getTrainerId() {
        return this.trainerId;
    }

    public Long getTcNumber() {
        return this.tcNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getSurName() {
        return this.surName;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getMail() {
        return this.mail;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPassword() {
        return this.password;
    }

    public Date getRecordTime() {
        return this.recordTime;
    }

    public ManagerModel getManagerModel() {
        return this.managerModel;
    }

    public SessionsModel getSessionsModel() {
        return this.sessionsModel;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public void setTcNumber(Long tcNumber) {
        this.tcNumber = tcNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setManagerModel(ManagerModel managerModel) {
        this.managerModel = managerModel;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setSessionsModel(SessionsModel sessionsModel) {
        this.sessionsModel = sessionsModel;
    }


}
