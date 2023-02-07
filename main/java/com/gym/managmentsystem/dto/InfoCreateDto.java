package com.gym.managmentsystem.dto;


public class InfoCreateDto {
    public InfoCreateDto(int trainerId, String day_of_week, int duration_in_minute, String training_description, String rightOfAbsence) {
        this.trainerId = trainerId;
        this.day_of_week = day_of_week;
        this.duration_in_minute = duration_in_minute;
        this.training_description = training_description;

    }

    public int trainerId;
    public String day_of_week;
    public int duration_in_minute;
    public String training_description;


    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getDay_of_Week() {
        return day_of_week;
    }

    public void setDay_of_Week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public int getDuration_in_Minute() {
        return duration_in_minute;
    }

    public void setDuration_in_Minute(int duration_in_minute) {
        this.duration_in_minute = duration_in_minute;
    }

    public String getTraining_Description() {
        return training_description;
    }

    public void setTraining_Description(String Training_Description) {
        this.training_description = Training_Description;
    }


}
