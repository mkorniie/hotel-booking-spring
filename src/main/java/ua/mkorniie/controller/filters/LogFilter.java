package ua.mkorniie.controller.filters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("########## Initiating Logging filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

        filterChain.doFilter(request, response);

        log.info("Logging Response :{}", response.getContentType());
    }

    @Override
    public void destroy() {
     log.info("########## Log Filter destroyed ##########");
    }
}