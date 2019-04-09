package com.qihang.friendship.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.vo.PackageVo </p>
 * <p>创建时间: 2019/3/17 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
public class PackageVo implements Serializable {
    private static final long serialVersionUID = 250810230197411863L;
    private Integer id;
    private String name;
    private BigDecimal price;
}
