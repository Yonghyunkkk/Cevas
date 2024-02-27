package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseReview {

    @Id
    @GeneratedValue
    @Column(name = "course_review_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotBlank
    @Column(name = "academic_year", nullable = false, length = 4)
    private String academicYear;

    @NotBlank
    @Column(name = "gpa", nullable = false, length = 2)
    private String gpa;

    @Min(value = 1, message = "Workload must be greater than zero")
    @Max(value = 10, message = "Workload must be less than or equal to 10")
    @Column(name = "workload", nullable = false)
    private int workload;

    @Min(value = 1, message = "Lecture difficulty must be greater than zero")
    @Max(value = 10, message = "Lecture difficulty must be less than or equal to 10")
    @Column(name = "lecture_difficulty", nullable = false)
    private int lectureDifficulty;

    @Min(value = 1, message = "Final exam difficulty must be greater than zero")
    @Max(value = 10, message = "Final exam difficulty must be less than or equal to 10")
    @Column(name = "final_exam_difficulty", nullable = false)
    private int finalExamDifficulty;

    @Min(value = 1, message = "Course entertainment must be greater than zero")
    @Max(value = 10, message = "Course entertainment must be less than or equal to 10")
    @Column(name = "course_entertainment", nullable = false)
    private int courseEntertainment;

    @Min(value = 1, message = "Course delivery must be greater than zero")
    @Max(value = 10, message = "Course delivery must be less than or equal to 10")
    @Column(name = "course_delivery", nullable = false)
    private int courseDelivery;

    @Min(value = 0, message = "Final exam ratio must be greater than -1")
    @Max(value = 100, message = "Final exam ratio must be less than or equal to 100")
    @Column(name = "final_exam_ratio", nullable = false)
    private int finalExamRatio;

    @Min(value = 0, message = "Mid-term ratio must be greater than -1")
    @Max(value = 100, message = "Mid-term ratio must be less than or equal to 100")
    @Column(name = "mid_term_ratio", nullable = false)
    private int midTermRatio;

    @Min(value = 0, message = "Assignments ratio must be greater than -1")
    @Max(value = 100, message = "Assignments ratio must be less than or equal to 100")
    @Column(name = "assignments_ratio", nullable = false)
    private int assignmentsRatio;

    @Min(value = 0, message = "Project ratio must be greater than -1")
    @Max(value = 100, message = "Project ratio must be less than or equal to 100")
    @Column(name = "project_ratio", nullable = false)
    private int projectRatio;


    // Constructor
    public static CourseReview createCourseReview(
            Member member,
            Course course,
            String academicYear,
            String gpa,
            int workload,
            int lectureDifficulty,
            int finalExamDifficulty,
            int courseEntertainment,
            int courseDelivery,
            int finalExamRatio,
            int midTermRatio,
            int assignmentsRatio,
            int projectRatio
    ) {
        CourseReview review = CourseReview.builder()
                .member(member)
                .course(course)
                .academicYear(academicYear)
                .gpa(gpa)
                .workload(workload)
                .lectureDifficulty(lectureDifficulty)
                .finalExamDifficulty(finalExamDifficulty)
                .courseEntertainment(courseEntertainment)
                .courseDelivery(courseDelivery)
                .finalExamRatio(finalExamRatio)
                .midTermRatio(midTermRatio)
                .assignmentsRatio(assignmentsRatio)
                .projectRatio(projectRatio)
                .build();

        member.addReviews(review);
        course.addReview(review);

        return review;
    }
}
