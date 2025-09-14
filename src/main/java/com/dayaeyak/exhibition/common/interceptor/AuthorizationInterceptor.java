package com.dayaeyak.exhibition.common.interceptor;

import com.dayaeyak.exhibition.common.annotation.Authorize;
import com.dayaeyak.exhibition.common.enums.UserRole;
import com.dayaeyak.exhibition.common.exception.CustomRuntimeException;
import com.dayaeyak.exhibition.common.exception.type.CommonExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String USER_ROLE_HEADER = "X-User-Role";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Authorize authorize = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Authorize.class);

        if (authorize == null) {
            authorize = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Authorize.class);
        }

        if (authorize == null) {
            return true;
        }

        if (authorize.bypass()) {
            return true;
        }

        String role = request.getHeader(USER_ROLE_HEADER);

        if (!StringUtils.hasText(role)) {
            throw new CustomRuntimeException(CommonExceptionType.REQUEST_ACCESS_DENIED);
        }

        boolean isExists = Arrays.stream(authorize.roles())
                .anyMatch(r -> r.name().equalsIgnoreCase(role));

        if (!isExists) {
            throw new CustomRuntimeException(CommonExceptionType.REQUEST_ACCESS_DENIED);
        }

        return true;
    }
}
