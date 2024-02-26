package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(name = "nickname", unique = true, nullable = false, length = 100)
    private String nickname;

    @NotBlank
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank
    @Column(name = "admission_year", nullable = false, length = 4)
    private String admissionYear;

    @NotBlank
    @Column(name = "major", nullable = false, length = 100)
    private String major;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "favorite_course",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> favoriteCourses = new ArrayList<>();

    public void addReviews(CourseReview review) {
        this.reviews.add(review);
        review.setMember(this);
    }
}
