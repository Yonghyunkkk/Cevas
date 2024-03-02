package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subclass {

    @Id
    @GeneratedValue
    @Column(name = "subclass_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotBlank
    @Column(name = "academic_year", nullable = false, length = 4)
    private String academicYear;

    @NotBlank
    @Column(name = "professor", nullable = false, length = 100)
    private String professor;

    public Subclass(Long id, Course course, String academicYear, String professor) {
        this.id = id;
        this.course = course;
        this.academicYear = academicYear;
        this.professor = professor;
    }

    public static Subclass createSubclass(Course course, String academicYear, String professor) {
        Subclass subclass = Subclass.builder()
                .course(course)
                .academicYear(academicYear)
                .professor(professor)
                .build();

        course.addSubclass(subclass);

        return subclass;
    }
}