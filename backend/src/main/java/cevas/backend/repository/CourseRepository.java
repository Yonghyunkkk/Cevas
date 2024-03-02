package cevas.backend.repository;

import cevas.backend.domain.Course;
import cevas.backend.domain.Subclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT s.professor FROM Subclass s WHERE s.course.id = :courseId")
    Optional<List<String>> findSubclassProfessorByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT s.academicYear FROM Subclass s WHERE s.course.id = :courseId")
    Optional<List<String>> findSubclassAcademicYearByCourseId(@Param("courseId") Long courseId);
}
