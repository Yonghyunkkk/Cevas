package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @NotBlank
    @Column(name = "course_code", unique = true, nullable = false, length = 100)
    private String courseCode;

    @NotBlank
    @Column(name = "course_name", unique = true, nullable = false, length = 100)
    private String courseName;

    @NotBlank
    @Column(name = "faculty", nullable = false, length = 100)
    private String faculty;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Subclass> subclasses = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteCourses")
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    public void addSubclass(Subclass subclass) {
        this.subclasses.add(subclass);
        subclass.setCourse(this);
    }

    public void addReview(CourseReview review) {
        this.reviews.add(review);
        review.setCourse(this);
    }

    public static Course createCourse(
            String courseCode,
            String courseName,
            String faculty
    ) {
        Course course = Course.builder()
                .courseCode(courseCode)
                .courseName(courseName)
                .faculty(faculty)
                .subclasses(new ArrayList<>())
                .reviews(new ArrayList<>())
                .members(new ArrayList<>())
                .build();

        return course;
    }
}