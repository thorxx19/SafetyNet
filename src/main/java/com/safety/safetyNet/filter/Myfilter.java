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
        String queryString = URLDecoder.decode(httpServletRequest.getQueryString(), StandardCharsets.UTF_8);
        log.info("Protocol :{}", request.getProtocol());
        log.info("Method :{}", httpServletRequest.getMethod());
        log.info("Server Name :{} ", request.getServerName());
        log.info("Local Port :{}", request.getLocalPort());
        log.info("Mapping :{}", httpServletRequest.getServletPath());
        log.info("Parametre :{}", queryString);

        filterChain.doFilter(request, response);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("code Response :{}", httpServletResponse.getStatus());
        log.info("Response Json :{}", responseStr);

    }
}
