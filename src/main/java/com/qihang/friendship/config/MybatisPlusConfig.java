package com.qihang.friendship.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.config.MybatisPlusConfig </p>
 * <p>创建时间: 2019/3/15 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Configuration
@MapperScan("com.qihang.friendship.dao.mapper")
public class MybatisPlusConfig {
}
