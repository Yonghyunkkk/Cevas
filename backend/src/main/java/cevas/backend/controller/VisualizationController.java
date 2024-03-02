package cevas.backend.controller;

import cevas.backend.controller.response.GetAvailableProfessorsVisualizationResponse;
import cevas.backend.controller.response.GetCourseOverviewVisualizationResponse;
import cevas.backend.controller.response.GetProfessorStatisticsVisualizationResponse;
import cevas.backend.service.VisualizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        GetCourseOverviewVisualizationResponse responseBody = visualizationService.getCourseOverview(courseId, year, professorName);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/visualization/{courseId}/professor-statistics")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get course visualization for professor statistics")
    public ResponseEntity<GetProfessorStatisticsVisualizationResponse> getProfessorStatisticsVisualization(
            @PathVariable(name = "courseId") Long courseId
    ) {
        GetProfessorStatisticsVisualizationResponse responseBody = visualizationService.getProfessorStatistics(courseId);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/visualization/{courseId}/professors")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get available professors for course visualization")
    public ResponseEntity<List<String>> getAvailableProfessors(
            @PathVariable(name = "courseId") Long courseId
    ) {
        List<String> responseBody = visualizationService.getAvailableProfessors(courseId);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/visualization/{courseId}/academic-years")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get available academic years for course visualization")
    public ResponseEntity<List<String>> getAvailableAcademicYears(
            @PathVariable(name = "courseId") Long courseId
    ) {
        List<String> responseBody = visualizationService.getAvailableAcadmicYears(courseId);

        return ResponseEntity.ok().body(responseBody);
    }
}
