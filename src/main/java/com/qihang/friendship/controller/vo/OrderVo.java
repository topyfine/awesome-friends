package com.qihang.friendship.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.vo.OrderVo </p>
 * <p>创建时间: 2019/3/18 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = -4255570601150453287L;
    private Long id;
    private String orderNo;
    private String outOrderNo;
    private String packageName;
    private Integer packageDuration;
    private BigDecimal packagePrice;
    private BigDecimal amount;
    private Integer status;
    private String memberIdentityNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String orderinfo;
}
