package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @NotBlank
    @Column(name = "course_code", unique = true, nullable = false, length = 100)
    private String courseCode;

    @NotBlank
    @Column(name = "course_name", unique = true, nullable = false, length = 100)
    private String courseName;

    @NotBlank
    @Column(name = "faculty", nullable = false, length = 100)
    private String faculty;

    @Column(name = "avg_gpa")
    @Builder.Default
    private Double avgGpa = 0.0;

    @Column(name = "gpa_tag")
    @Builder.Default
    private String gpaTag = "none";

    @Column(name = "avg_workload")
    @Builder.Default
    private Double avgWorkload = 0.0;

    @Column(name = "workload_tag")
    @Builder.Default
    private String workloadTag = "none";

    @Column(name = "avg_lecture_difficulty")
    @Builder.Default
    private Double avgLectureDifficulty = 0.0;

    @Column(name = "lecture_difficulty_tag")
    @Builder.Default
    private String lectureDifficultyTag = "none";

    @Column(name = "avg_final_exam_difficulty")
    @Builder.Default
    private Double avgFinalExamDifficulty = 0.0;

    @Column(name = "final_exam_difficulty_tag")
    @Builder.Default
    private String finalExamDifficultyTag = "none";

    @Column(name = "avg_lecture_quality")
    @Builder.Default
    private Double avgLectureQuality = 0.0;

    @Column(name = "lecture_quality_tag")
    @Builder.Default
    private String lectureQualityTag = "none";

    @Column(name = "total_reviews")
    @Builder.Default
    private int totalReviews = 0;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Subclass> subclasses = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteCourses")
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    public void addSubclass(Subclass subclass) {
        this.subclasses.add(subclass);
        subclass.setCourse(this);
    }

    public void addReview(CourseReview review) {
        this.reviews.add(review);
        review.setCourse(this);
    }

    public static Course createCourse(
            String courseCode,
            String courseName,
            String faculty
    ) {
        Course course = Course.builder()
                .courseCode(courseCode)
                .courseName(courseName)
                .faculty(faculty)
                .subclasses(new ArrayList<>())
                .reviews(new ArrayList<>())
                .members(new ArrayList<>())
                .build();

        return course;
    }

    public void addCourseStatistics(
            String gpa,
            int workload,
            int lectureDifficulty,
            int finalExamDifficulty,
            int lectureQuality
    ) {
        this.avgGpa = ((this.avgGpa * this.totalReviews) + gpaToDouble(gpa)) / (this.totalReviews + 1);
        this.avgWorkload = ((this.avgWorkload * this.totalReviews) + workload) / (this.totalReviews + 1);
        this.avgLectureDifficulty = ((this.avgLectureDifficulty * this.totalReviews) + lectureDifficulty) / (this.totalReviews + 1);
        this.avgFinalExamDifficulty = ((this.avgFinalExamDifficulty * this.totalReviews) + finalExamDifficulty) / (this.totalReviews + 1);
        this.avgLectureQuality = ((this.avgLectureQuality * this.totalReviews) + lectureQuality) / (this.totalReviews + 1);
        this.totalReviews = this.totalReviews + 1;

        this.gpaTag = getTag(this.avgGpa);
        this.workloadTag = getTag(this.avgWorkload);
        this.lectureDifficultyTag = getTag(this.avgLectureDifficulty);
        this.finalExamDifficultyTag = getTag(this.avgFinalExamDifficulty);
        this.lectureQualityTag = getTag(this.avgLectureQuality);
    }

    public void updateCourseStatistics(
            String oldGpa,
            int oldWorkload,
            int oldLectureDifficulty,
            int oldFinalExamDifficulty,
            int oldLectureQuality,
            String newGpa,
            int newWorkload,
            int newLectureDifficulty,
            int newFinalExamDifficulty,
            int newLectureQuality
    ) {
        this.avgGpa = this.avgGpa + ((gpaToDouble(newGpa) - gpaToDouble(oldGpa)) / this.totalReviews);
        this.avgWorkload = this.avgWorkload + ((newWorkload - oldWorkload) / this.totalReviews);
        this.avgLectureDifficulty = this.avgLectureDifficulty + ((newLectureDifficulty - oldLectureDifficulty) / this.totalReviews);
        this.avgFinalExamDifficulty = this.avgFinalExamDifficulty + ((newFinalExamDifficulty - oldFinalExamDifficulty) / this.totalReviews);
        this.avgLectureQuality = this.avgLectureQuality + ((newLectureQuality - oldLectureQuality) / this.totalReviews);

        this.gpaTag = getTag(this.avgGpa);
        this.workloadTag = getTag(this.avgWorkload);
        this.lectureDifficultyTag = getTag(this.avgLectureDifficulty);
        this.finalExamDifficultyTag = getTag(this.avgFinalExamDifficulty);
        this.lectureQualityTag = getTag(this.avgLectureQuality);
    }

    public void removeCourseStatistics(
            String gpa,
            int workload,
            int lectureDifficulty,
            int finalExamDifficulty,
            int lectureQuality
    ) {
        int newTotalReviews = this.totalReviews - 1;

        if (newTotalReviews > 0) {
            this.avgGpa = ((this.avgGpa * this.totalReviews) - gpaToDouble(gpa)) / newTotalReviews;
            this.avgWorkload = ((this.avgWorkload * this.totalReviews) - workload) / newTotalReviews;
            this.avgLectureDifficulty = ((this.avgLectureDifficulty * this.totalReviews) - lectureDifficulty) / newTotalReviews;
            this.avgFinalExamDifficulty = ((this.avgFinalExamDifficulty * this.totalReviews) - finalExamDifficulty) / newTotalReviews;
            this.avgLectureQuality = ((this.avgLectureQuality * this.totalReviews) - lectureQuality) / newTotalReviews;
        } else {
            this.avgGpa = 0.0;
            this.avgWorkload = 0.0;
            this.avgLectureDifficulty = 0.0;
            this.avgFinalExamDifficulty = 0.0;
            this.avgLectureQuality = 0.0;
        }

        this.totalReviews = newTotalReviews;

        this.gpaTag = getTag(this.avgGpa);
        this.workloadTag = getTag(this.avgWorkload);
        this.lectureDifficultyTag = getTag(this.avgLectureDifficulty);
        this.finalExamDifficultyTag = getTag(this.avgFinalExamDifficulty);
        this.lectureQualityTag = getTag(this.avgLectureQuality);
    }

    private String getTag(Double value) {
        if (value >= 1 && value <= 2) {
            return "very light";
        } else if (value >= 3 && value <= 4) {
            return "light";
        } else if (value >= 5 && value <= 6) {
            return "medium";
        } else if (value >= 7 && value <= 8) {
            return "heavy";
        } else if (value >= 9 && value <= 10) {
            return "very heavy";
        } else {
            return "none";
        }
    }

    private Double gpaToDouble(String gpa) {
        return switch (gpa) {
            case "A+" -> 10.0;
            case "A" -> 9.0;
            case "A-" -> 8.0;
            case "B+" -> 7.0;
            case "B" -> 6.0;
            case "B-" -> 5.0;
            case "C+" -> 4.0;
            case "C" -> 3.0;
            case "C-" -> 2.0;
            case "D+" -> 1.0;
            case "D" -> 0.5;
            default -> 0.0;
        };
    }

}