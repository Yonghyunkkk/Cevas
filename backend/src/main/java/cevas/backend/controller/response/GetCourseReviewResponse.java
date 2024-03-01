package cevas.backend.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class GetCourseReviewResponse {

    private Long courseId;

    private String courseCode;

    private String courseName;

    private String academicYear;

    private String professorName;

    private String gpa;

    private int workload;

    private int lectureDifficulty;

    private int lectureQuality;

    private int finalExamDifficulty;

    private int courseEntertainment;

    private int courseDelivery;

    private int courseInteractivity;

    private int finalExamRatio;

    private int midTermRatio;

    private int assignmentsRatio;

    private int projectRatio;
}
