package cevas.backend.repository;

import cevas.backend.domain.CourseReview;
import cevas.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {

    CourseReview findByMemberIdAndCourseId(Long memberId, Long courseId);

    @Query("SELECT cr FROM CourseReview cr LEFT JOIN FETCH cr.course WHERE cr.member.id = :memberId AND cr.id = :id")
    Optional<CourseReview> findByMemberIdAndId(@Param("memberId") Long memberId, @Param("id") Long id);

    @Query("SELECT cr FROM CourseReview cr LEFT JOIN FETCH cr.course WHERE cr.member.id = :memberId")
    Optional<List<CourseReview>> findByMemberId(@Param("memberId") Long memberId);
}
