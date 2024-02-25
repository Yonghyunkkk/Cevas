package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private int admissionYear;

    @NotEmpty
    private String major;

    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "member")
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "favorite_course",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> favoriteCourses = new ArrayList<>();

}
