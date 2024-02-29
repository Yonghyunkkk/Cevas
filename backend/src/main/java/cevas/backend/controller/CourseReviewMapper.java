package cevas.backend.controller;

import cevas.backend.controller.response.GetCourseReviewResponse;
import cevas.backend.domain.CourseReview;
import org.springframework.stereotype.Component;

@Component
public class CourseReviewMapper {

    public GetCourseReviewResponse convertToResponse(CourseReview review) {
        return new GetCourseReviewResponse(
                review.getCourse().getId(),
                review.getCourse().getCourseCode(),
                review.getCourse().getCourseName(),
                review.getAcademicYear(),
                review.getProfessorName(),
                review.getGpa(),
                review.getWorkload(),
                review.getLectureDifficulty(),
                review.getFinalExamDifficulty(),
                review.getCourseEntertainment(),
                review.getCourseDelivery(),
                review.getFinalExamRatio(),
                review.getMidTermRatio(),
                review.getAssignmentsRatio(),
                review.getProjectRatio()
        );
    }

}
