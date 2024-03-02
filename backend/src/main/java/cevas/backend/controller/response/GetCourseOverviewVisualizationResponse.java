package cevas.backend.controller.response;

import cevas.backend.dto.CourseReviewCriteriaCountsDto;
import cevas.backend.dto.CourseReviewLectureQualityDto;
import cevas.backend.dto.CourseReviewPentagonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCourseOverviewVisualizationResponse {
    private Long totalReviews;

    private List<CourseReviewCriteriaCountsDto> gpa;

    private List<CourseReviewCriteriaCountsDto> workload;

    private List<CourseReviewCriteriaCountsDto> lectureDifficulty;

    private List<CourseReviewCriteriaCountsDto> finalExamDifficulty;

    private List<CourseReviewPentagonDto> pentagon;

    private List<CourseReviewLectureQualityDto> lectureQuality;
}
