package com.qihang.friendship.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qihang.friendship.common.Constants;
import com.qihang.friendship.controller.annotation.ValidIdentity;
import com.qihang.friendship.controller.vo.MemberVo;
import com.qihang.friendship.dao.entity.Member;
import com.qihang.friendship.service.IMemberService;
import com.qihang.friendship.util.BeanConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.MemberController </p>
 * <p>创建时间: 2019/3/17 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
@RequestMapping(Constants.URL_NAMESPACE)
public class MemberController extends BaseController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("/members/{identityNo}")
    @ResponseStatus(HttpStatus.OK)
    @ValidIdentity
    public MemberVo getMember(@PathVariable String identityNo) {
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("identity_no", identityNo));
        if (member == null) {
            // 未开通过会员的用户也统一返回会员剩余天数为0
            return BeanConverter.convert(() -> {
                MemberVo memberVo = new MemberVo();
                memberVo.setIdentityNo(identityNo);
                memberVo.setRestOfDays(0);
                return memberVo;
            });
        }
        // 开通过会员的用户返回
        return BeanConverter.convert(() -> {
            MemberVo memberVo = new MemberVo();
            BeanUtils.copyProperties(member, memberVo);
            int days = (int) Duration.between(LocalDateTime.now(), member.getPrivilegeExpireTime()).toDays();
            memberVo.setRestOfDays(days < 1 ? 0 : days);
            return memberVo;
        });
    }
}
