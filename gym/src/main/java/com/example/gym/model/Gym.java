package com.example.gym.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gym")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gym_id")
    private Long id;
    @Column(name = "gym_name")
    private String name;
    @Column(name = "gym_location")
    private String location;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinColumn(name = "gym_id")
    private List<Trainer> trainers;

}
