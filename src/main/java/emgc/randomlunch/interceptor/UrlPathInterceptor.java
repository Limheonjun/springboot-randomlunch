package emgc.randomlunch.interceptor;

import emgc.randomlunch.util.HttpReqResUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UrlPathInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("IP Address : {}", HttpReqResUtils.getClientIpAddressIfServletRequestExist());
        log.info("URL PATH : {}", request.getRequestURI());
        return true;
    }
}