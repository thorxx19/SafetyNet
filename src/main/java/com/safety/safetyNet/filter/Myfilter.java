package com.safety.safetyNet.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author froid
 */
@Slf4j
@Component
public class Myfilter implements Filter {
    /**
     * method pour loger les requese et response dans le programme
     * @param request la requéte
     * @param response la réponse
     * @param filterChain le filtre
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, responseCacheWrapperObject);
        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
        responseCacheWrapperObject.copyBodyToResponse();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (!httpServletRequest.getServletPath().contains("swagger")
                && !httpServletRequest.getServletPath().contains("api-docs")
                && !httpServletRequest.getServletPath().contains("actuator")
                && !httpServletRequest.getServletPath().isBlank()
                && !httpServletRequest.getServletPath().contains("favicon")
                && httpServletRequest.getQueryString() != null) {
                log.info("Protocol :{} Method :{} Server :{} Port:{} End point:{}", request.getProtocol(), httpServletRequest.getMethod()
                    , request.getServerName(), request.getLocalPort(), httpServletRequest.getServletPath());
                log.info("Parametre :{}", URLDecoder.decode(httpServletRequest.getQueryString(), StandardCharsets.UTF_8));
                filterChain.doFilter(request, response);

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            log.info("code Response :{}", httpServletResponse.getStatus());
            log.info("Response Json :{}", responseStr);
        }
    }
}
