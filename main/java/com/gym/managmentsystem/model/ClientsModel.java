package com.gym.managmentsystem.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class ClientsModel {

    public ClientsModel(String password, Long tcNumber, String name,
                        String surName,  Integer gymClass,
                        Long phoneNumber, String mail, String gender, Date recordTime) {
        this.tcNumber = tcNumber;
        this.name = name;
        this.surName = surName;
        this.mail = mail;
        this.password = password;
        this.gymClass = gymClass;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.recordTime = recordTime;
    }

    public ClientsModel() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincreament
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "tcNumber")
    private Long tcNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surName")
    private String surName;


    @Column(name = "gymClass")
    private int gymClass;

    @Column(name = "phoneNumber")
    private Long phoneNumber;

    @Column(name = "mail")
    private String mail;

    @Column(name = "gender")
    private String gender;

    @Column(name = "password")
    private String password;

    @Column(name = "record_time")
    @Temporal(TemporalType.DATE)
    private Date recordTime;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_model_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ManagerModel managerModel;


    @OneToMany(mappedBy = "clientsModel", cascade = CascadeType.ALL)
    private Set<ClientInfoModel>    clientInfoModels = new HashSet<>();
    public Set<ClientInfoModel> getClientInfoModel() {
        return clientInfoModels;
    }
    public void setClientInfoModel(Set<ClientInfoModel> clientInfoModels) {
        this.clientInfoModels = clientInfoModels;
        for (ClientInfoModel b : clientInfoModels) {
            b.setClientsModel(this);
        }
    }


    public Long getClientId() {
        return this.clientId;
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



    public Integer getGymClass() {
        return this.gymClass;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getMail() {
        return this.mail;
    }

    public  String getGender() {
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

    public Set<ClientInfoModel> getClientInfoModels() {
        return this.clientInfoModels;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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



    public void setGymClass(Integer gymClass) {
        this.gymClass = gymClass;
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

    public void setClientInfoModels(Set<ClientInfoModel> clientInfoModels) {
        this.clientInfoModels = clientInfoModels;
    }


}
