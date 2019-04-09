package com.qihang.friendship.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.vo.ErrorVo </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
public class ErrorVo implements Serializable {
    private static final long serialVersionUID = 3973004213958739984L;
    private final Integer errcode;
    private final String errmsg;
    private final Long timestamp = System.currentTimeMillis();
    private String path;

    public ErrorVo(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ErrorVo(Integer errcode, String errmsg, String path) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.path = path;
    }
}
