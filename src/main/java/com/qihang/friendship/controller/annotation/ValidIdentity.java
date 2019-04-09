package com.qihang.friendship.controller.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.annotation.ValidIdentity </p>
 * <p>创建时间: 2019/3/28 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 * @desc 核实访问用户和资源拥有者身份的一致性
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#identityNo == authentication.name")
public @interface ValidIdentity {
}
