package com.example.cesar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name= "course")
public class Course {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Classroom specificClass;
}
