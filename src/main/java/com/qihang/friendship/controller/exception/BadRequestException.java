package com.qihang.friendship.controller.exception;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.exception.BadRequestException </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -6739301947737720109L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
