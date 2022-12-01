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
@Table(name = "sessions")//from session to sessions
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long session_id;
    @Column(name = "session_description")
    private String description;
    @Column(name = "title")
    private String title;
    @Column(name = "session_type")
    private String session_type;
    @Column(name = "difficulty_level")//change difficulty
    private String duration;
    @Column(name = "rate")
    private int rate;

    @ManyToMany(mappedBy = "sessions")
    private List<Customer> customerSession;

    @ManyToMany(mappedBy = "trainer_sessions")
    private List<Trainer> trainerSession;


}
