package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "email address must not be left blank")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "Please enter a valid email address.")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "nickname must not be left blank")
    @Column(name = "nickname", unique = true, nullable = false, length = 100)
    private String nickname;

    @NotBlank(message = "password must not be left blank")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "admission year must not be left blank")
    @Column(name = "admission_year", nullable = false, length = 4)
    private String admissionYear;

    @NotBlank(message = "major must not be left blank")
    @Column(name = "major", nullable = false, length = 100)
    private String major;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "favorite_course",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> favoriteCourses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void addReviews(CourseReview review) {
        this.reviews.add(review);
        review.setMember(this);
    }

    // Constructor
    public static Member createMember(
            String email,
            String nickname,
            String password,
            String admissionYear,
            String major,
            Authority authority
    ) {
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .admissionYear(admissionYear)
                .major(major)
                .authority(authority)
                .build();

        return member;
    }

    public void updateMember(
            String email,
            String nickname,
            String admissionYear,
            String major
    ) {
        this.email = email;
        this.nickname = nickname;
        this.admissionYear = admissionYear;
        this.major = major;
    }

    public void changePw (
            String password
    ) {
        this.password = password;
    }


}
