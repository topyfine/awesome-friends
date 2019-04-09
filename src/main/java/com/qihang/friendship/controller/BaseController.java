package com.qihang.friendship.controller;

import com.qihang.friendship.controller.exception.BadRequestException;
import com.qihang.friendship.controller.exception.NotAuthorizedException;
import com.qihang.friendship.controller.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.BaseController </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Slf4j
public class BaseController {

    /**
     * 未找到资源
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(NotFoundException ex) {
        log.info(ex.getMessage());
    }

    /**
     * 请求参数错误
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalid(BadRequestException ex) {
        log.info(ex.getMessage());
    }

    /**
     * 未经授权非法访问
     */
    @ExceptionHandler({NotAuthorizedException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void forbidden(Exception ex) {
        log.warn(ex.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void unknown(Exception ex) {
        log.warn(ex.getMessage());
    }
}