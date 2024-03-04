package cevas.backend.controller;

import cevas.backend.dto.request.ChangePwRequest;
import cevas.backend.dto.request.MemberRequest;
import cevas.backend.dto.response.MemberResponse;
import cevas.backend.service.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "5. Settings")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/setting")
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "get my account details")
    public ResponseEntity<MemberResponse> getMyMemberInfo() {
        MemberResponse myInfoBySecurity = settingsService.getMyInfoBySecurity();
        return ResponseEntity.ok((myInfoBySecurity));
    }

    @PutMapping("/details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update my account details")
    public void changeDetails(@RequestBody MemberRequest request) {
        settingsService.changeMemberDetails(request);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update password")
    public void changePassword(@Valid @RequestBody ChangePwRequest request) {
        settingsService.changeMemberPassword(request.getOldPw(), request.getNewPw());
    }
}
