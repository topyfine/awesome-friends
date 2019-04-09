package com.qihang.friendship.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.config.AlipayConfig </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Configuration
public class AlipayConfig {
    @Autowired
    private AlipayProperties properties;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AlipayClient alipayClient() {
        //实例化客户端
        return new DefaultAlipayClient(properties.getServerUrl(), properties.getAppId(),
                properties.getAppPrivateKey(), properties.getFormat(), properties.getCharset(),
                properties.getAlipayPublicKey(), properties.getSignType());
    }
}
