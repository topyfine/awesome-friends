package com.qihang.friendship.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.dao.entity.Order </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
@TableName("`order`")
public class Order {
    private Long id;
    private String orderNo;
    private String outOrderNo;
    private String packageName;
    private Integer packageDuration;
    private BigDecimal packagePrice;
    private BigDecimal amount;
    private Integer status;
    private String outNotifyId;
    private String outNotifyBody;
    private String memberIdentityNo;
    private LocalDateTime createTime;
    @TableField(update = "now()", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
