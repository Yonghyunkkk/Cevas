package cevas.backend.exception;

import lombok.Getter;

@Getter
public enum ErrorInfo {
    /**
     * Authentication
     */
    MEMBER_NOT_FOUND(400, "MEMBER_NOT_FOUND", "Member with the given id does not exist."),
    UNAUTHORIZED_OPERATION(400, "UNAUTHORIZED_OPERATION", "Member does not have right for this operation"),
    EMAIL_ALREADY_EXIST(400, "EMAIL_ALREADY_EXIST", "There is already a member registered with the above email address" ),
    NICKNAME_ALREADY_EXIST(400, "NICKNAME_ALREADY_EXIST", "There is already a member registered with the nickname. Please use another nickname." ),
    INVALID_REFRESH_TOKEN(400,"INVALID_REFRESH_TOKEN", "Invalid Refresh Token"),
    LOGOUT(400, "LOGOUT", "User has been logged out"),
    INVALID_TOKEN_CREDENTIALS(400, "INVALID_TOKEN_CREDENTIALS", "Incorrect token user credentials"),
    SECURITY_CONTEXT_ERROR(400, "SECURITY_CONTEXT_ERROR", "There is no verification info in Security Context"),
    WRONG_PASSWORD(400, "WRONG_PW", "Incorrect Password"),
    /**
     * Course
     */
    COURSE_NOT_FOUND(400, "COURSE_NOT_FOUND", "Course with the given id is not found"),

    /**
     * Course Review
     */
    REVIEW_NOT_FOUND(400, "REVIEW_NOT_FOUND", "Review with the given id is not found"),
    REVIEW_ALREADY_EXIST(400, "REVIEW_ALREADY_EXIST", "Member has already left a review for course"),
    TOTAL_RATIO_EXCEEDS(400, "TOTAL_RATIO_EXCEEDS", "Total ratio has exceeded 100");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    ErrorInfo(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

}
