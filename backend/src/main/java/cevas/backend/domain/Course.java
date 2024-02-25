package cevas.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private String courseCode;

    private String courseName;

    private String faculty;

    @OneToMany(mappedBy = "course")
    private List<Subclass> subclasses = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteCourses")
    private List<Member> members = new ArrayList<>();
}