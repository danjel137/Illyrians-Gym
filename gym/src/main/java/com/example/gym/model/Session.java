package com.example.gym.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sessions")//from session to sessions
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long session_id;
    @Lob
    @Column(name = "session_description")
    private String description;
    @Column(name = "title")
    private String title;
    @Column(name = "session_type")
    @Enumerated(EnumType.ORDINAL)
    private Type session_type;
    @Column(name = "difficulty_level")
    private int difficultyLevel;
    @Column(name="time")
     private Timestamp time;
    @Column(name="duration")
    private String duration;
    @Column(name = "rate")
    private int rate;

    @ManyToMany(mappedBy = "sessions")
    private List<Customer> customerSession;

    @ManyToMany(mappedBy = "trainer_sessions")
    private List<Trainer> trainerSession;




}
