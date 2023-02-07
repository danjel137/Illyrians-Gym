package com.gym.managmentsystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "clientInfoId")
@Table(name = "clientinfo")
public class ClientInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientInfoId")
    private Long  clientInfoId;

    @Column(name = "sessionName")
    private String sessionName;

    @Column(name = "day_of_week")
    private String day_of_week;

    @Column(name = "duration_in_minute")
    private int duration_in_minute;

    @Column(name = "training_description")
    private String training_description;

    @Column(name = "trainer_id")
    private int trainerId;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClientsModel clientsModel;

    public Long getClientInfoId() {
        return this.clientInfoId;
    }

    public String getSessionName() {
        return this.sessionName;
    }



    public String getDay_of_week() {
        return this.day_of_week;
    }

    public int getDuration_in_minute() {
        return this.duration_in_minute;
    }

    public String getTraining_description() {
        return this.training_description;
    }

    public int getTrainerId() {
        return this.trainerId;
    }

    public ClientsModel getClientsModel() {
        return this.clientsModel;
    }

    public void setClientInfoId(Long clientInfoId) {
        this.clientInfoId = clientInfoId;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }


    public void setDay_of_week(String DayOfWeek) {
        this.day_of_week = DayOfWeek;
    }

    public void setDuration_in_minute(int DurationInMinute) {
        this.duration_in_minute = DurationInMinute;
    }

    public void setTraining_description(String TrainingDescription) {
        this.training_description = TrainingDescription;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setClientsModel(ClientsModel clientsModel) {
        this.clientsModel = clientsModel;
    }







}
