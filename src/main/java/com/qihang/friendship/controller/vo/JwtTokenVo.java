package com.qihang.friendship.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.vo.JwtTokenVo </p>
 * <p>创建时间: 2019/3/22 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
public class JwtTokenVo implements Serializable {
    private static final long serialVersionUID = -6934775949353404199L;
    private String token;
    private long timestamp = System.currentTimeMillis();
}
