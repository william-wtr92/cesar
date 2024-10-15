package com.example.cesar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name= "courses")
public class Course {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date startDate;

    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User teacher;
}
