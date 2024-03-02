package cevas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseReviewLectureQualityDto {
    private Double entertainment;
    private Double delivery;
    private Double interactivity;
}
