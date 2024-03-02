package cevas.backend.repository;

import cevas.backend.domain.QCourse;
import cevas.backend.domain.QCourseReview;
import cevas.backend.dto.CourseReviewCriteriaCountsDto;
import cevas.backend.dto.CourseReviewLectureQualityDto;
import cevas.backend.dto.CourseReviewPentagonDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        Long expectedTotalReviews = 3L;
        Long totalCourseReviews = courseReviewQueryRepository.findTotalCourseReviews(1L, null, null);

        System.out.println("totalCourseReviews = " + totalCourseReviews);

        assertThat(totalCourseReviews).isEqualTo(expectedTotalReviews);
    }

    @Test
    public void getGpaCounts() throws Exception {
        Long courseId = 1L;
        CourseReviewCriteriaCountsDto expectedDto = new CourseReviewCriteriaCountsDto("A+", 3L);
        List<CourseReviewCriteriaCountsDto> expectedOutput = Arrays.asList(expectedDto);

        List<CourseReviewCriteriaCountsDto> actualOutput = courseReviewQueryRepository.findGpaCounts(courseId, null, null);

        assertThat(actualOutput.get(0).getCount()).isEqualTo(expectedOutput.get(0).getCount());
    }

    @Test
    public void getWorkloadCounts() throws Exception {
        List<CourseReviewCriteriaCountsDto> workloadCounts = courseReviewQueryRepository.findWorkloadCounts(1L, null, null);

        assertThat(workloadCounts.get(0).getCriteria()).isEqualTo("Medium");
        assertThat(workloadCounts.get(0).getCount()).isEqualTo(1);
    }
    
    @Test
    public void getLectureDifficultyCounts() throws Exception {
        List<CourseReviewCriteriaCountsDto> lectureDifficultyCounts = courseReviewQueryRepository.findLectureDifficultyCounts(1L, null, null);

        assertThat(lectureDifficultyCounts.get(0).getCriteria()).isEqualTo("Medium");
        assertThat(lectureDifficultyCounts.get(0).getCount()).isEqualTo(1);
    }
    
    @Test
    public void getFinalExamDifficultyCounts() throws Exception {
        List<CourseReviewCriteriaCountsDto> finalExamDifficultyCounts = courseReviewQueryRepository.findFinalExamDifficultyCounts(1L, null, null);

        assertThat(finalExamDifficultyCounts.get(0).getCriteria()).isEqualTo("Medium");
        assertThat(finalExamDifficultyCounts.get(0).getCount()).isEqualTo(2);
    }
    
    @Test
    public void getAverageForPentagon() throws Exception {
        List<CourseReviewPentagonDto> averageForPentagon = courseReviewQueryRepository.findAverageForPentagon(1L, null, null);

        for (CourseReviewPentagonDto courseReviewPentagonDto : averageForPentagon) {
            assertThat(courseReviewPentagonDto.getGpa()).isEqualTo(10);
            assertThat(courseReviewPentagonDto.getLectureDifficulty()).isEqualTo(8.333333333333334);
            assertThat(courseReviewPentagonDto.getFinalExamDifficulty()).isEqualTo(6.666666666666667);
            assertThat(courseReviewPentagonDto.getWorkLoad()).isEqualTo(8.333333333333334);
            assertThat(courseReviewPentagonDto.getLectureQuality()).isEqualTo(8);
        }
    }

    @Test
    public void getAverageForLectureQuality() throws Exception {
        List<CourseReviewLectureQualityDto> averageForLectureQuality = courseReviewQueryRepository.findAverageForLectureQuality(1L, null, null);

        for (CourseReviewLectureQualityDto courseReviewLectureQualityDto : averageForLectureQuality) {
            assertThat(courseReviewLectureQualityDto.getEntertainment()).isEqualTo(50);
            assertThat(courseReviewLectureQualityDto.getDelivery()).isEqualTo(50);
            assertThat(courseReviewLectureQualityDto.getInteractivity()).isEqualTo(50);
        }
    }

}