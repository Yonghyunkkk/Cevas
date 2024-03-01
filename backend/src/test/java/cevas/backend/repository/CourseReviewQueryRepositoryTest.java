package cevas.backend.repository;

import cevas.backend.domain.QCourse;
import cevas.backend.domain.QCourseReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class CourseReviewQueryRepositoryTest {

    @Autowired
    CourseReviewQueryRepository courseReviewQueryRepository;

    @Autowired
    CourseReviewRepository courseReviewRepository;

    @Test
    public void getAllReviews() throws Exception {
        //given
        Long totalCourseReviews = courseReviewQueryRepository.findTotalCourseReviews(1L, null, null);
        //when
        System.out.println("totalCourseReviews = " + totalCourseReviews);

        //then
        Assertions.assertThat(totalCourseReviews).isEqualTo(courseReviewRepository.findByCourseId(1L).stream().count());
    }
}