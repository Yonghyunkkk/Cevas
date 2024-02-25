package cevas.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Subclass {

    @Id
    @GeneratedValue
    @Column(name = "subclass_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private int academicYear;

    private String professorName;

    // Getters and setters
}