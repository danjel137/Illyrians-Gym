package com.gym.managmentsystem.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class SessionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    @Column(name = "session_name")
    private String sessionName;


    @OneToMany(mappedBy = "sessionsModel",  cascade = CascadeType.ALL)
    private Set<TrainersModel> trainer = new HashSet<>();
    public Set<TrainersModel> getTrainersModel() {
        return trainer;
    }
    public void setTrainersModel(Set<TrainersModel> trainer) {
        this.trainer = trainer;
        for (TrainersModel b : trainer) {
            b.setSessionsModel(this);
        }
    }

    public Long getSessionId() {
        return this.sessionId;
    }

    public String getSessionName() {
        return this.sessionName;
    }

    public Set<TrainersModel> getTrainer() {
        return this.trainer;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setTrainer(Set<TrainersModel> trainer) {
        this.trainer = trainer;
    }


}

