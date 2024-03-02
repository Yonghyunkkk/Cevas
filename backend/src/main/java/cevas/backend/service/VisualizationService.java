package cevas.backend.service;

import cevas.backend.controller.response.GetAvailableProfessorsVisualizationResponse;
import cevas.backend.controller.response.GetCourseOverviewVisualizationResponse;
import cevas.backend.controller.response.GetProfessorStatisticsVisualizationResponse;
import cevas.backend.domain.Subclass;
import cevas.backend.dto.CourseReviewCriteriaCountsDto;
import cevas.backend.dto.CourseReviewLectureQualityDto;
import cevas.backend.dto.CourseReviewPentagonDto;
import cevas.backend.dto.CourseReviewProfessorStatisticsDto;
import cevas.backend.exception.CustomException;
import cevas.backend.repository.CourseRepository;
import cevas.backend.repository.CourseReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cevas.backend.exception.ErrorInfo.COURSE_NOT_FOUND;
import static cevas.backend.exception.ErrorInfo.SUBCLASS_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisualizationService {

    private final CourseReviewQueryRepository courseReviewQueryRepository;
    private final CourseRepository courseRepository;

    public GetCourseOverviewVisualizationResponse getCourseOverview(Long courseId, String year, String professorName) {

        // check if course exists in DB
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // get total number of reviews from DB
        Long totalCourseReviews = courseReviewQueryRepository.findTotalCourseReviews(courseId, year, professorName);

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

        // get average for each category in lecture quality
        List<CourseReviewLectureQualityDto> averageForLectureQuality = courseReviewQueryRepository.findAverageForLectureQuality(courseId, year, professorName);

        return new GetCourseOverviewVisualizationResponse(
                totalCourseReviews,
                gpaCounts,
                workloadCounts,
                lectureDifficultyCounts,
                finalExamDifficultyCounts,
                averageForPentagon,
                averageForLectureQuality
        );
    }

    public GetProfessorStatisticsVisualizationResponse getProfessorStatistics(Long courseId) {
        // check if course exists in DB
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // get data for pentagon chart by each professor
        List<CourseReviewProfessorStatisticsDto> averageForPentagonByProfessor = courseReviewQueryRepository.findAverageForPentagonByProfessor(courseId);

        return new GetProfessorStatisticsVisualizationResponse(
                averageForPentagonByProfessor
        );
    }

    public List<String> getAvailableProfessors(Long courseId) {
        // check if course exists in DB
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // check if subclass with courseId exists in DB
        List<String> professors = courseRepository.findSubclassProfessorByCourseId(courseId)
                .orElseThrow(() -> new CustomException(SUBCLASS_NOT_FOUND));

        return professors;
    }

    public List<String> getAvailableAcadmicYears(Long courseId) {
        // check if course exists in DB
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // check if subclass with courseId exists in DB
        List<String> academicYears = courseRepository.findSubclassAcademicYearByCourseId(courseId)
                .orElseThrow(() -> new CustomException(SUBCLASS_NOT_FOUND));

        return academicYears;
    }
}
