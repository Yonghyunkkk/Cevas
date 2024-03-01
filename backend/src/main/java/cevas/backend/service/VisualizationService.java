package cevas.backend.service;

import cevas.backend.controller.response.GetCourseOverviewVisualizationResponse;
import cevas.backend.domain.CourseReview;
import cevas.backend.dto.CourseReviewCriteriaCountsDto;
import cevas.backend.dto.CourseReviewPentagonDto;
import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import cevas.backend.repository.CourseRepository;
import cevas.backend.repository.CourseReviewQueryRepository;
import cevas.backend.repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cevas.backend.exception.ErrorInfo.COURSE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VisualizationService {

    private final CourseReviewQueryRepository courseReviewQueryRepository;
    private final CourseRepository courseRepository;

    public GetCourseOverviewVisualizationResponse courseOverviewVisualizationResponse(Long courseId, String year, String professorName) {

        // check if course exists in DB
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // get total number of reviews from DB
        Long totalCourseReviews = courseReviewQueryRepository.findTotalCourseReviews(courseId, year, professorName);
        System.out.println("log = " + totalCourseReviews);
        // get number of gpa for each category
        List<CourseReviewCriteriaCountsDto> gpaCounts = courseReviewQueryRepository.findGpaCounts(courseId, year, professorName);

        // get number of workload for each category
        List<CourseReviewCriteriaCountsDto> workloadCounts = courseReviewQueryRepository.findWorkloadCounts(courseId, year, professorName);

        // get number of lecture difficulty for each category
        List<CourseReviewCriteriaCountsDto> lectureDifficultyCounts = courseReviewQueryRepository.findLectureDifficultyCounts(courseId, year, professorName);

        // get number of final exam difficulty for each category
        List<CourseReviewCriteriaCountsDto> finalExamDifficultyCounts = courseReviewQueryRepository.findFinalExamDifficultyCounts(courseId, year, professorName);

        // get overall average for each category for petagon chart
        List<CourseReviewPentagonDto> averageForPentagon = courseReviewQueryRepository.findAverageForPentagon(courseId, year, professorName);

        return new GetCourseOverviewVisualizationResponse(
                totalCourseReviews,
                gpaCounts,
                workloadCounts,
                lectureDifficultyCounts,
                finalExamDifficultyCounts,
                averageForPentagon
        );
    }
}
