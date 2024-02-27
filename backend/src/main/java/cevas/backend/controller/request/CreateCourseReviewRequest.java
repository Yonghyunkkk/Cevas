package cevas.backend.controller.request;

import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static cevas.backend.exception.ErrorInfo.TOTAL_RATIO_EXCEEDS;

@Getter
public class CreateCourseReviewRequest {

    @NotNull
    @Schema(example = "1")
    private Long courseId;

    @NotBlank
    @Schema(example = "2024")
    private String academicYear;

    @NotBlank
    @Schema(example = "A+")
    private String gpa;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(example = "1")
    private int workload;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(example = "1")
    private int lectureDifficulty;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(example = "1")
    private int finalExamDifficulty;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(example = "1")
    private int courseEntertainment;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(example = "1")
    private int courseDelivery;

    @NotNull
    @Min(0)
    @Max(100)
    @Schema(example = "60")
    private int finalExamRatio;

    @NotNull
    @Min(0)
    @Max(100)
    @Schema(example = "60")
    private int midTermRatio;

    @NotNull
    @Min(0)
    @Max(100)
    @Schema(example = "60")
    private int assignmentsRatio;

    @NotNull
    @Min(0)
    @Max(100)
    @Schema(example = "60")
    private int projectRatio;

    public int getTotalRatio() {
        return finalExamRatio + midTermRatio + assignmentsRatio + projectRatio;
    }
}
