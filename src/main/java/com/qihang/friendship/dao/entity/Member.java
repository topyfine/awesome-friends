package com.qihang.friendship.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.dao.entity.Member </p>
 * <p>创建时间: 2019/3/17 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Data
@TableName("member")
public class Member {
    private Integer id;
    private String identityNo;
    private LocalDateTime privilegeExpireTime;
    private LocalDateTime createTime;
    @TableField(update = "now()", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
