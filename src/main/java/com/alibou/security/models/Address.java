package com.alibou.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String city;
    private  String street;
    private int entrance;
    private int floor;
    private  int appartment;
    private LocalDate date;
    private LocalTime time;
    @OneToOne
    @JsonIgnore
    private  Order order;
}