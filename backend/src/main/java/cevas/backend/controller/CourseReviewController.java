package cevas.backend.controller;

import cevas.backend.controller.request.CreateCourseReviewRequest;
import cevas.backend.controller.response.CreateCourseReviewResponse;
import cevas.backend.service.CourseReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Course Review")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    @PostMapping("/course-reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a course review")
    public ResponseEntity<CreateCourseReviewResponse> createCourseReview(@Valid @RequestBody CreateCourseReviewRequest request) {

        // 추가할 것. 현재 멤버 찾아오는 로직.
//        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
//        Long memberId = jwtUserInfo.getMemberId();

        Long courseReviewId = courseReviewService.createCourseReview(1L, request);
        CreateCourseReviewResponse responseBody = new CreateCourseReviewResponse(courseReviewId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @DeleteMapping("/course-reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a course review")
    public void deleteCourseReview(@PathVariable(name = "id") Long courseReviewId) {
//        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
//        Long memberId = jwtUserInfo.getMemberId();
        courseReviewService.deleteCourseReview(1L, courseReviewId);
    }


}
