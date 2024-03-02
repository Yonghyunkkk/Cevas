package cevas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CourseReviewProfessorStatisticsDto {
    private String professor;
    private Double gpa;
    private Double lectureDifficulty;
    private Double finalExamDifficulty;
    private Double workLoad;
    private Double lectureQuality;
}

