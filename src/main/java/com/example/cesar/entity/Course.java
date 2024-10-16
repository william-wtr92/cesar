package com.example.cesar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "courses_files", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "file_url")
    private List<String> urlFiles;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User teacher;
}
