package com.qihang.friendship.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import com.qihang.friendship.controller.exception.NotAuthorizedException;
import com.qihang.friendship.controller.vo.JwtTokenVo;
import com.qihang.friendship.util.BeanConverter;
import com.qihang.friendship.util.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Date;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.LoginController </p>
 * <p>创建时间: 2019/3/22 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
public class LoginController extends BaseController {
    @Value("${application.consumer-id}")
    private String consumerId;

    @PostMapping("/login/{identityNo}")
    @ResponseStatus(HttpStatus.OK)
    public JwtTokenVo login(@PathVariable String identityNo, String appid) throws Exception {
        // 验证APP身份
        if (!StringUtils.equals(appid, consumerId)) {
            throw new NotAuthorizedException(String.format("非法appid:[%s]", appid));
        }
        // Prepare JWT with claims set
        long timestamp = System.currentTimeMillis();
        Date iatDate = new Date(timestamp);
        Date expDate = new Date(timestamp + Duration.ofHours(2).getSeconds() * 1000);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(identityNo)
                .issuer("qihangsoftware")
                .issueTime(iatDate)
                .expirationTime(expDate)
                .claim("scope", "api")
                .build();
        String token = JwtTokenUtils.issue(claimsSet);
        return BeanConverter.convert(() ->{
            JwtTokenVo tokenVo = new JwtTokenVo();
            tokenVo.setToken(token);
            return tokenVo;
        });
    }
}
