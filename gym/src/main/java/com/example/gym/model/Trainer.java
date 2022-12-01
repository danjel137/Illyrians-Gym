package com.example.gym.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainers")//from trainer to trainers
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;
    @Column(name = "trainer_name")
    private String name;
    @Column(name = "title")
    private String title;
    @Column(name = "social_media")
    private String social_media_accounts;
    @Column(name = "biography")
    private String biography;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToMany
    @JoinTable(
            name = "trainer_session",
            joinColumns = {@JoinColumn(name = "trainer_id")},
            inverseJoinColumns = {@JoinColumn(name = "session_id")})
    private List<Session> trainer_sessions;


}
