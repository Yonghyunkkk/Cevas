package cevas.backend.service;

import cevas.backend.controller.request.CreateCourseReviewRequest;
import cevas.backend.domain.Course;
import cevas.backend.domain.CourseReview;
import cevas.backend.domain.Member;
import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import cevas.backend.repository.CourseRepository;
import cevas.backend.repository.CourseReviewRepository;
import cevas.backend.repository.MemberRepository;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cevas.backend.exception.ErrorInfo.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CourseReviewServiceTest {

    @Autowired
    CourseReviewService courseReviewService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseReviewRepository courseReviewRepository;

    @Test
    public void createCourseReview_Test() {
        Member member = createMember();

        Course course = createCourse();

        CreateCourseReviewRequest createCourseReviewRequest = createCourseReviewRequest(course.getId());

        Long courseReviewId = courseReviewService.createCourseReview(member.getId(), createCourseReviewRequest);

        CourseReview courseReview = courseReviewRepository.findById(courseReviewId).get();
        CourseReview findCourseReview = courseReviewRepository.findByMemberIdAndCourseId(member.getId(), course.getId());

        assertEquals(courseReview, findCourseReview);
    }

    @Test
    public void createCourseReview_MemberNotFoundException_Test() throws Exception {
        Course course = createCourse();

        CreateCourseReviewRequest createCourseReviewRequest = createCourseReviewRequest(course.getId());

        try {
            courseReviewService.createCourseReview(-1L, createCourseReviewRequest);

            fail("MemberNotFoundException should be thrown.");
        } catch (CustomException e) {
            assertEquals(MEMBER_NOT_FOUND, e.getErrorInfo());
        }
    }

    @Test
    public void createCourseReview_CourseNotFoundException_Test() throws Exception {
        Member member = createMember();

        Course course = createCourse();

        CreateCourseReviewRequest createCourseReviewRequest = createCourseReviewRequest(-1L);

        try {
            courseReviewService.createCourseReview(member.getId(), createCourseReviewRequest);

            fail("CourseNotFoundException should be thrown.");
        } catch (CustomException e) {
            assertEquals(COURSE_NOT_FOUND, e.getErrorInfo());
        }
    }

    @Test
    public void createCourseReview_ReviewAlreadyExistsException_Test() throws Exception {
        Member member = createMember();

        Course course = createCourse();

        CreateCourseReviewRequest createCourseReviewRequest = createCourseReviewRequest(course.getId());

        try {
            courseReviewService.createCourseReview(member.getId(), createCourseReviewRequest);
            courseReviewService.createCourseReview(member.getId(), createCourseReviewRequest);

            fail("ReviewAlreadyExistsException should be thrown.");
        } catch (CustomException e) {
            assertEquals(REVIEW_ALREADY_EXIST, e.getErrorInfo());
        }
    }

    @Test
    public void createCourseReview_TotalRatioExceedsException_Test() throws Exception {
        Member member = createMember();

        Course course = createCourse();

        CreateCourseReviewRequest createCourseReviewRequest = new CreateCourseReviewRequest(
                course.getId(),
                "2024",
                "A+",
                5,
                5,
                5,
                5,
                5,
                50,
                20,
                20,
                30
        );

        try {
            courseReviewService.createCourseReview(member.getId(), createCourseReviewRequest);

            fail("TotalRatioExceedsException should be thrown.");
        } catch (CustomException e) {
            assertEquals(TOTAL_RATIO_EXCEEDS, e.getErrorInfo());
        }
    }



    private Member createMember() {
        Member member = Member.createMember("yonghyunkwon98@gmail.com", "yonghyun", "abcdefg", "2018", "Computer Science");
        return memberRepository.save(member);
    }

    private Course createCourse() {
        Course course = Course.createCourse("COMP3230", "Operating System", "Engineering");
        return courseRepository.save(course);
    }

    private CreateCourseReviewRequest createCourseReviewRequest(Long courseId) {
        CreateCourseReviewRequest createCourseReviewRequest = new CreateCourseReviewRequest(
                courseId,
                "2024",
                "A+",
                5,
                5,
                5,
                5,
                5,
                20,
                20,
                20,
                30
        );
        return createCourseReviewRequest;
    }

}