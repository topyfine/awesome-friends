package com.qihang.friendship.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qihang.friendship.config.AlipayProperties;
import com.qihang.friendship.common.Constants;
import com.qihang.friendship.dao.entity.Member;
import com.qihang.friendship.dao.entity.Order;
import com.qihang.friendship.service.IAlipayService;
import com.qihang.friendship.service.IMemberService;
import com.qihang.friendship.service.IOrderService;
import com.qihang.friendship.util.BeanConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.service.impl.AlipayServiceImpl </p>
 * <p>创建时间: 2019/3/18 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Service
@Slf4j
public class AlipayServiceImpl implements IAlipayService {
    private static final String QUICK_MSECURITY_PAY = "QUICK_MSECURITY_PAY";
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private AlipayProperties properties;
    @Value("${application.server-url}")
    private String appServerUrl;

    private enum TradeStatus {
        /**
         * 交易完成	true（触发通知）
         */
        TRADE_FINISHED(1),
        /**
         * 支付成功	true（触发通知）
         */
        TRADE_SUCCESS(1),
        /**
         * 交易关闭	true（触发通知）
         */
        TRADE_CLOSED(2)
        ;
        /**
         * 对应商户订单状态
         */
        private int flag;

        TradeStatus(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return flag;
        }
    }

    @Override
    public String appPrepay(Order order) {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(String.format("商品：%s，价格：￥%s", order.getPackageName(), order.getPackagePrice().toPlainString()));
        model.setSubject(order.getPackageName());
        model.setOutTradeNo(order.getOrderNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(order.getAmount().toPlainString());
        model.setProductCode(QUICK_MSECURITY_PAY);
        request.setBizModel(model);
        request.setNotifyUrl(appServerUrl + Constants.ALIPAY_APP_NOTIFY_PATH);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //就是orderString 可以直接给客户端请求，无需再做处理。
            String orderinfo = response.getBody();
            log.info(">>>支付宝APP支付请求参数orderinfo => {}", orderinfo);
            return orderinfo;
        } catch (AlipayApiException e) {
            log.warn(">>>支付宝APP支付API[{}]异常：{}", request.getApiMethodName(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean confirmAppNotify(Map<String, String> notifyParams) {
        //过滤重复通知
        String notifyId = notifyParams.get("notify_id");
        Order record = orderService.getOne(new QueryWrapper<Order>().eq("out_notify_id", notifyId));
        if (record != null) {
            //重复通知不要再发送啦
            return true;
        }
        //校验订单的有效性
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", notifyParams.get("out_trade_no")));
        if (order != null) {
            //确认订单金额和appid
            if (properties.getAppId().equals(notifyParams.get("app_id")) &&
                    order.getAmount().compareTo(new BigDecimal(notifyParams.get("total_amount"))) == 0) {
                //同步订单状态
                Order updateOrder = BeanConverter.convert(() -> {
                    Order order1 = new Order();
                    order1.setId(order.getId());
                    order1.setOutOrderNo(notifyParams.get("trade_no"));
                    TradeStatus tradeStatus = TradeStatus.valueOf(notifyParams.get("trade_status"));
                    order1.setStatus(tradeStatus.getFlag());
                    order1.setOutNotifyId(notifyId);
                    order1.setOutNotifyBody(notifyParams.toString());
                    return order1;
                });
                orderService.updateById(updateOrder);
                //付款成功则开通会员特权
                if (updateOrder.getStatus() == Constants.PAY_FINISHED) {
                    String identityNo = order.getMemberIdentityNo();
                    Member member = memberService.getOne(new QueryWrapper<Member>().eq("identity_no", identityNo));
                    Member newMember = BeanConverter.convert(() -> {
                        Member mem = new Member();
                        if (member != null) {
                            LocalDateTime expireTime = member.getPrivilegeExpireTime();
                            if (expireTime.isBefore(LocalDateTime.now())) {
                                // 已到期会员，重新开通
                                mem.setPrivilegeExpireTime(LocalDateTime.now().plusMonths(order.getPackageDuration()));
                            } else {
                                // 会员续费
                                mem.setPrivilegeExpireTime(expireTime.plusMonths(order.getPackageDuration()));
                            }
                            mem.setId(member.getId());
                        } else {
                            // 开通会员
                            mem.setIdentityNo(identityNo);
                            //服务到期时间计算
                            LocalDateTime expireTime = LocalDateTime.now().plusMonths(order.getPackageDuration());
                            mem.setPrivilegeExpireTime(expireTime);
                        }
                        return mem;
                    });
                    memberService.saveOrUpdate(newMember);
                }
                //接收通知成功，无需再发送啦
                return true;
            }
        }
        //其他情况请继续通知吧
        return false;
    }
}
