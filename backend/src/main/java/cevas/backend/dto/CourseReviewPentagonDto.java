package cevas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CourseReviewPentagonDto {
    private Double gpa;
    private Double lectureDifficulty;
    private Double finalExamDifficulty;
    private Double workLoad;
    private Double lectureQuality;
}
