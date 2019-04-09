package com.qihang.friendship.controller;

import com.qihang.friendship.common.Constants;
import com.qihang.friendship.controller.annotation.ValidIdentity;
import com.qihang.friendship.controller.exception.BadRequestException;
import com.qihang.friendship.controller.vo.UserVo;
import com.qihang.friendship.util.BeanConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.UserController </p>
 * <p>创建时间: 2019/3/23 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
@RequestMapping(Constants.URL_NAMESPACE)
public class UserController extends BaseController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${application.max-free-use}")
    private int maxTryTimes;

    /**
     * 查询用户试用次数
     */
    @GetMapping("/users/{identityNo}/try")
    @ResponseStatus(HttpStatus.OK)
    @ValidIdentity
    public UserVo getUserTryTimes(@PathVariable String identityNo) {
        // 无试用记录时初始化可试用次数， 当天有效
        LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
        redisTemplate.opsForValue().setIfAbsent(identityNo, String.valueOf(maxTryTimes), Duration.between(LocalDateTime.now(), tomorrow));
        // 查询可试用次数
        String restOfTimes = redisTemplate.opsForValue().get(identityNo);
        return BeanConverter.convert(() -> {
            UserVo userVo = new UserVo();
            userVo.setIdentityNo(identityNo);
            userVo.setRestOfTryTimes(Integer.valueOf(restOfTimes));
            return userVo;
        });
    }

    /**
     * 更新用户试用次数
     */
    @PostMapping("/users/{identityNo}/try")
    @ResponseStatus(HttpStatus.OK)
    @ValidIdentity
    public Boolean updateUserTryTimes(@PathVariable String identityNo, Long times) {
        if (times == null) {
            throw new BadRequestException("非法参数times");
        }
        String restOfTimes = redisTemplate.opsForValue().get(identityNo);
        // 无剩余次数可减
        if (StringUtils.isBlank(restOfTimes) || Integer.valueOf(restOfTimes) < 1) {
            return Boolean.TRUE;
        }
        // 减持试用次数
        try {
            redisTemplate.opsForValue().decrement(identityNo, times);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
