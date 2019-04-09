package com.qihang.friendship.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.config.AlipayProperties </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayProperties {
    private String serverUrl = "https://openapi.alipay.com/gateway.do";
    private String appId;
    private String appPrivateKey;
    private String format = "json";
    private String charset = "UTF-8";
    private String alipayPublicKey;
    private String signType = "RSA2";
}
