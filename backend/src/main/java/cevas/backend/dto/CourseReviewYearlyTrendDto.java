package cevas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseReviewYearlyTrendDto {
    private String professor;
    private String academicYear;
    private Double gpa;
    private Double lectureDifficulty;
    private Double finalExamDifficulty;
    private Double workLoad;
    private Double lectureQuality;
    private Double entertainment;
    private Double delivery;
    private Double interactivity;
}
