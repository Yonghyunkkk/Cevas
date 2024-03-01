package cevas.backend.repository;


import cevas.backend.domain.QCourse;
import cevas.backend.domain.QCourseReview;
import cevas.backend.dto.CourseReviewCriteriaCountsDto;
import cevas.backend.dto.CourseReviewPentagonDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseReviewQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QCourse course = QCourse.course;
    private static final QCourseReview courseReview = QCourseReview.courseReview;

    public Long findTotalCourseReviews(Long courseId, String year, String professorName) {
        return queryFactory
                .select(courseReview.count())
                .from(courseReview)
                .where(courseIdEq(courseId), yearEq(year), professorNameEq(professorName))
                .fetchOne();
    }

    public List<CourseReviewCriteriaCountsDto> findGpaCounts(Long courseId, String year, String professorName) {
        return queryFactory
                .select(Projections.fields(CourseReviewCriteriaCountsDto.class,
                        courseReview.gpa,
                        courseReview.count()))
                .from(courseReview)
                .where(courseIdEq(courseId), yearEq(year), professorNameEq(professorName))
                .groupBy(courseReview.gpa)
                .fetch();
    }

    public List<CourseReviewPentagonDto> findAverageForPentagon(Long courseId, String year, String professorName) {
        NumberExpression<Double> gpaNumeric = new CaseBuilder()
                .when(courseReview.gpa.eq("A+")).then(4.3)
                .when(courseReview.gpa.eq("A")).then(4.0)
                .when(courseReview.gpa.eq("A-")).then(3.7)
                .when(courseReview.gpa.eq("B+")).then(3.3)
                .when(courseReview.gpa.eq("B-")).then(3.0)
                .when(courseReview.gpa.eq("B")).then(2.7)
                .when(courseReview.gpa.eq("C+")).then(2.3)
                .when(courseReview.gpa.eq("C-")).then(2.0)
                .when(courseReview.gpa.eq("C")).then(1.7)
                .when(courseReview.gpa.eq("D+")).then(1.3)
                .when(courseReview.gpa.eq("D")).then(1.0)
                .otherwise(0.0);

        return queryFactory
                .select(Projections.fields(CourseReviewPentagonDto.class,
                        gpaNumeric.avg().as("gpa"),
                        courseReview.lectureDifficulty.avg(),
                        courseReview.finalExamDifficulty.avg(),
                        courseReview.workload.avg(),
                        courseReview.lectureQuality.avg()))
                .from(courseReview)
                .where(courseIdEq(courseId), yearEq(year), professorNameEq(professorName))
                .fetch();
    }

    public List<CourseReviewCriteriaCountsDto> findWorkloadCounts(Long courseId, String year, String professorName) {
        return findCriteriaCounts(courseId, year, professorName, courseReview.workload);
    }

    public List<CourseReviewCriteriaCountsDto> findLectureDifficultyCounts(Long courseId, String year, String professorName) {
        return findCriteriaCounts(courseId, year, professorName, courseReview.lectureDifficulty);
    }

    public List<CourseReviewCriteriaCountsDto> findFinalExamDifficultyCounts(Long courseId, String year, String professorName) {
        return findCriteriaCounts(courseId, year, professorName, courseReview.finalExamDifficulty);
    }

    private List<CourseReviewCriteriaCountsDto> findCriteriaCounts(Long courseId, String year, String professorName, NumberPath<Integer> criteriaPath) {
        Expression<String> category = new CaseBuilder()
                .when(criteriaPath.between(1, 2)).then("Very Light")
                .when(criteriaPath.between(3, 4)).then("Light")
                .when(criteriaPath.between(5, 6)).then("Medium")
                .when(criteriaPath.between(7, 8)).then("Heavy")
                .otherwise("Very Heavy");

        return queryFactory
                .select(Projections.fields(CourseReviewCriteriaCountsDto.class,
                        category,
                        courseReview.count()))
                .from(courseReview)
                .where(courseIdEq(courseId), yearEq(year), professorNameEq(professorName))
                .groupBy(category)
                .fetch();
    }


    private BooleanExpression courseIdEq(Long courseIdCond) {
        return courseIdCond != null ? courseReview.course.id.eq(courseIdCond) : null;
    }

    private BooleanExpression yearEq(String yearCond) {
        return yearCond != null ? courseReview.academicYear.eq(yearCond) : null;
    }

    private BooleanExpression professorNameEq(String professorNameCond) {
        return professorNameCond != null ? courseReview.professorName.eq(professorNameCond) : null;
    }


}
