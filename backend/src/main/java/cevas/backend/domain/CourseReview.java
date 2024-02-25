package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CourseReview {

    @Id
    @GeneratedValue
    @Column(name = "course_review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotEmpty
    private int academicYear;

    @NotEmpty
    private int workload;

    @NotEmpty
    private int lectureDifficulty;

    @NotEmpty
    private int finalExamDifficulty;

    @NotEmpty
    private int courseEntertainment;

    @NotEmpty
    private int courseDelivery;

    @NotEmpty
    private int finalExamRatio;

    @NotEmpty
    private int midTermRatio;

    @NotEmpty
    private int assignmentsRatio;

    @NotEmpty
    private int projectRatio;
}
