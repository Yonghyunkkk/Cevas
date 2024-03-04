package cevas.backend.util;

import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString()); //AnonymousAuthenticationToken [Principal=anonymousUser, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[ROLE_ANONYMOUS]]
        if (authentication == null || authentication.getName() == null) {
            throw  new CustomException(ErrorInfo.SECURITY_CONTEXT_ERROR);
        }

        return Long.parseLong(authentication.getName());
    }
}
