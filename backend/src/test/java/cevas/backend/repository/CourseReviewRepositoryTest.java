package cevas.backend.repository;

import cevas.backend.domain.Course;
import cevas.backend.domain.CourseReview;
import cevas.backend.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CourseReviewRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseReviewRepository courseReviewRepository;

    @Test
    public void findByMemberIdAndCourseId_Test() throws Exception {
        //given
        Member savedMember = createMember();
        Course savedCourse = createCourse();
        CourseReview savedCourseReview = createCourseReview(savedMember, savedCourse);

        //when
        CourseReview findCourseReview = courseReviewRepository.findByMemberIdAndCourseId(savedMember.getId(), savedCourse.getId());

        //then
        assertThat(findCourseReview.getId()).isEqualTo(savedCourseReview.getId());
        assertThat(findCourseReview.getMember().getId()).isEqualTo(savedMember.getId());
        assertThat(findCourseReview.getCourse().getId()).isEqualTo(savedCourse.getId());
    }

    @Test
    public void findByMemberIdAndId() throws Exception {
        //given
        Member savedMember = createMember();
        Course savedCourse = createCourse();
        CourseReview savedCourseReview = createCourseReview(savedMember, savedCourse);
        
        //when
        CourseReview findCourseReview = courseReviewRepository.findByMemberIdAndId(savedMember.getId(), savedCourseReview.getId()).get();

        //then
        assertThat(findCourseReview.getId()).isEqualTo(savedCourseReview.getId());
        assertThat(findCourseReview.getMember().getId()).isEqualTo(savedMember.getId());
        assertThat(findCourseReview.getCourse().getId()).isEqualTo(savedCourse.getId());
    }

    private Member createMember() {
        Member member = Member.createMember("yonghyunkwon98@gmail.com", "yonghyun", "abcdefg", "2018", "Computer Science");
        return memberRepository.save(member);
    }

    private Course createCourse() {
        Course course = Course.createCourse("COMP3230", "Operating System", "Engineering");
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }

    private CourseReview createCourseReview(Member savedMember, Course savedCourse) {
        CourseReview courseReview = CourseReview.createCourseReview(
                savedMember,
                savedCourse,
                "2024",
                "Dr. Luo",
                "A+",
                5,
                5,
                5,
                5,
                5,
                20,
                20,
                20,
                30,
                10,
                20
        );
        CourseReview savedCourseReview = courseReviewRepository.save(courseReview);
        return savedCourseReview;
    }
}