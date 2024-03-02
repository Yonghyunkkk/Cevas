package cevas.backend.controller.response;

import cevas.backend.dto.CourseReviewProfessorStatisticsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetProfessorStatisticsVisualizationResponse {
    private List<CourseReviewProfessorStatisticsDto> data;
}
