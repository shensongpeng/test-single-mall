package ink.songsong.mall.controller;/*
 *
 * @ClassName BaseController
 * @Author shensongpeng
 * @Date 2021/6/18 :18:00
 * @Version 1.0
 * */

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import springfox.documentation.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }
    public HttpSession getHttpSeeion() {
        return getRequest().getSession();
    }
}
