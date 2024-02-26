package cevas.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subclass {

    @Id
    @GeneratedValue
    @Column(name = "subclass_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotBlank
    @Column(name = "academic_year", nullable = false, length = 4)
    private String academicYear;

    @NotBlank
    @Column(name = "professor", nullable = false, length = 100)
    private String professor;
}