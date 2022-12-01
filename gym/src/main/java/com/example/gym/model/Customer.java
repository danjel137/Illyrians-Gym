package com.example.gym.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customers")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customer_id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "difficulty_level")
    private String difficulty_level;
    @Column(name = "time_date")
    private Date time_date;

    @Column(name = "gender")//smallint
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "status")
    private boolean status;


    @ManyToMany
    @JoinTable(
            name = "costumer_session",
            joinColumns = { @JoinColumn(name = "costumer_id")},
            inverseJoinColumns = {@JoinColumn(name = "session_id")})
    private List<Session> sessions;

}
