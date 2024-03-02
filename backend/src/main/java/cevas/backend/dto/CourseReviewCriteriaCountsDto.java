package cevas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseReviewCriteriaCountsDto {
    private String criteria;
    private Long count;
}
