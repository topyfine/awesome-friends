package com.qihang.friendship.config;

import com.alibaba.fastjson.JSON;
import com.qihang.friendship.controller.vo.ErrorVo;
import com.qihang.friendship.util.BeanConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.config.CustomWebSecurityConfig </p>
 * <p>创建时间: 2019/3/20 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeRequests()
                // 静态资源放行\系统公共服务接口放行
                .mvcMatchers("/jwt/**", "/login/**", "/alipay/**").permitAll()
                // API接口访问需要授权
                .mvcMatchers("/api/**").hasAuthority("SCOPE_api")
                // 其他资源暂不开通服务
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(customResponseFilter(), ChannelProcessingFilter.class)
                .oauth2ResourceServer().jwt();
    }

    private Filter customResponseFilter() {
        return (request, response, chain) -> {
            HttpServletResponse res = (HttpServletResponse) response;
            HttpServletRequest req = (HttpServletRequest) request;
            // 不进行前置处理
            chain.doFilter(request, response);
            // 后置处理响应
            HttpStatus httpStatus = HttpStatus.valueOf(res.getStatus());
            switch (httpStatus) {
                case OK:
                    // 不修改响应内容
                    break;
                default:
                    // 定制响应内容
                    ErrorVo errorVo = BeanConverter.convert(() -> {
                        int errorcode = httpStatus.value();
                        String errmsg = httpStatus.getReasonPhrase();
                        String path = req.getRequestURI();
                        return new ErrorVo(errorcode, errmsg, path);
                    });
                    // Spring框架已处理过非业务响应的
                    // 此处不再重复处理
                    if (!response.isCommitted()) {
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        response.getWriter().append(JSON.toJSONString(errorVo));
                    }
            }
        };
    }
}
