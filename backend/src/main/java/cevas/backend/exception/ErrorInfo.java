package cevas.backend.exception;

import lombok.Getter;

@Getter
public enum ErrorInfo {
    /**
     * Authentication
     */
    MEMBER_NOT_FOUND(400, "MEMBER_NOT_FOUND", "Member with the given id does not exist."),
    UNAUTHORIZED_OPERATION(400, "UNAUTHORIZED_OPERATION", "Member does not have right for this operation"),

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
