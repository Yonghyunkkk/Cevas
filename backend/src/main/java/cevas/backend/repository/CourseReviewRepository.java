package cevas.backend.repository;

import cevas.backend.domain.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {

    CourseReview findByMemberIdAndCourseId(Long memberId, Long courseId);

}
