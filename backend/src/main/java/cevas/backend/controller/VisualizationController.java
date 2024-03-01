package cevas.backend.controller;

import cevas.backend.controller.response.GetCourseOverviewVisualizationResponse;
import cevas.backend.service.VisualizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. Visualization")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VisualizationController {

    private final VisualizationService visualizationService;


    @GetMapping("/visualization/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get course visualization for overview")
    public ResponseEntity<GetCourseOverviewVisualizationResponse> getCourseOverviewVisualization(
            @PathVariable(name = "courseId") Long courseId,
            @RequestParam(name = "year", required = false) String year,
            @RequestParam(name = "professorName", required = false) String professorName
    ) {
        GetCourseOverviewVisualizationResponse responseBody = visualizationService.courseOverviewVisualizationResponse(courseId, year, professorName);

        return ResponseEntity.ok().body(responseBody);
    }
}
