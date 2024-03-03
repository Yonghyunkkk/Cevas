package cevas.backend.service;

import cevas.backend.controller.request.CreateCourseReviewRequest;
import cevas.backend.controller.request.UpdateCourseReviewRequest;
import cevas.backend.domain.Course;
import cevas.backend.domain.CourseReview;
import cevas.backend.domain.Member;
import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import cevas.backend.repository.CourseRepository;
import cevas.backend.repository.CourseReviewRepository;
import cevas.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static cevas.backend.exception.ErrorInfo.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    public List<CourseReview> getAllCourseReviews(Long memberId) {
        return courseReviewRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
    public CourseReview getSingleCourseReview(Long memberId, Long courseReviewId) {
        return courseReviewRepository.findByMemberIdAndId(memberId, courseReviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
    }

    @Transactional
    public Long createCourseReview(Long memberId, CreateCourseReviewRequest courseReviewRequest) {

        // check if member exists in DB
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // check if course exists in DB
        Course course = courseRepository.findById(courseReviewRequest.getCourseId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // check if member already left a review for course in DB
        CourseReview findReview = courseReviewRepository.findByMemberIdAndCourseId(memberId, courseReviewRequest.getCourseId());
        if (findReview != null) {
            throw new CustomException(REVIEW_ALREADY_EXIST);
        }

        // check if total ratio exceeds 100
        if (courseReviewRequest.getTotalRatio() > 100) {
            throw new CustomException(TOTAL_RATIO_EXCEEDS);
        }

        // create new course review
        CourseReview courseReview = CourseReview.createCourseReview(
                member,
                course,
                courseReviewRequest.getAcademicYear(),
                courseReviewRequest.getProfessorName(),
                courseReviewRequest.getGpa(),
                courseReviewRequest.getWorkload(),
                courseReviewRequest.getLectureQuality(),
                courseReviewRequest.getLectureDifficulty(),
                courseReviewRequest.getFinalExamDifficulty(),
                courseReviewRequest.getCourseEntertainment(),
                courseReviewRequest.getCourseDelivery(),
                courseReviewRequest.getCourseInteractivity(),
                courseReviewRequest.getFinalExamRatio(),
                courseReviewRequest.getMidTermRatio(),
                courseReviewRequest.getAssignmentsRatio(),
                courseReviewRequest.getProjectRatio()
        );

        // add course statistics for current course
        course.addCourseStatistics(
                courseReviewRequest.getGpa(),
                courseReviewRequest.getWorkload(),
                courseReviewRequest.getLectureDifficulty(),
                courseReviewRequest.getFinalExamDifficulty(),
                courseReviewRequest.getLectureQuality()
                );

        // save created course review
        CourseReview savedCourseReview = courseReviewRepository.save(courseReview);
        return savedCourseReview.getId();
    }

    @Transactional
    public void updateCourseReview(
            Long memberId,
            Long courseReviewId,
            UpdateCourseReviewRequest courseReviewRequest) {

        // check if member exists in DB
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // check if course exists in DB
        Course course = courseRepository.findById(courseReviewRequest.getCourseId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // check if review exists in DB
        CourseReview courseReview = courseReviewRepository.findByMemberIdAndId(memberId, courseReviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // check if total ratio exceeds 100
        if (courseReviewRequest.getTotalRatio() > 100) {
            throw new CustomException(TOTAL_RATIO_EXCEEDS);
        }

        // check if member has authorized access
        if (!Objects.equals(courseReview.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        // update course statistic for current course
        course.updateCourseStatistics(
                courseReview.getGpa(),
                courseReview.getWorkload(),
                courseReview.getLectureDifficulty(),
                courseReview.getFinalExamDifficulty(),
                courseReview.getLectureQuality(),
                courseReviewRequest.getGpa(),
                courseReviewRequest.getWorkload(),
                courseReviewRequest.getLectureDifficulty(),
                courseReviewRequest.getFinalExamDifficulty(),
                courseReviewRequest.getLectureQuality()
        );

        // update course review
        courseReview.updateCourseReview(
                member,
                course,
                courseReviewRequest.getAcademicYear(),
                courseReviewRequest.getProfessorName(),
                courseReviewRequest.getGpa(),
                courseReviewRequest.getWorkload(),
                courseReviewRequest.getLectureQuality(),
                courseReviewRequest.getLectureDifficulty(),
                courseReviewRequest.getFinalExamDifficulty(),
                courseReviewRequest.getCourseEntertainment(),
                courseReviewRequest.getCourseDelivery(),
                courseReviewRequest.getCourseInteractivity(),
                courseReviewRequest.getFinalExamRatio(),
                courseReviewRequest.getMidTermRatio(),
                courseReviewRequest.getAssignmentsRatio(),
                courseReviewRequest.getProjectRatio()
        );
    }

    @Transactional
    public void deleteCourseReview(Long memberId, Long courseReviewId) {

        // check if courseReview exists in DB
        CourseReview courseReview = courseReviewRepository.findById(courseReviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // check if course exists in DB
        Course course = courseRepository.findById(courseReview.getCourse().getId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        // check if member has authorized access
        if (!Objects.equals(courseReview.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        // remove course statistic for current course
        course.removeCourseStatistics(
                courseReview.getGpa(),
                courseReview.getWorkload(),
                courseReview.getLectureDifficulty(),
                courseReview.getFinalExamDifficulty(),
                courseReview.getLectureQuality()
        );

        // delete course review
        courseReviewRepository.delete(courseReview);
    }
}
