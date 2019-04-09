package com.qihang.friendship.service;

import com.qihang.friendship.dao.entity.Order;

import java.util.Map;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.service.IAlipayService </p>
 * <p>创建时间: 2019/3/18 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
public interface IAlipayService {
    /**
     * JAVA服务端SDK生成APP支付订单信息
     *
     * @param order
     * @return orderInfo app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接
     */
    String appPrepay(Order order);

    /**
     * JAVA服务端验证异步通知信息参数
     *
     * @param notifyParams
     * @return boolean true-确认成功，false-确认失败
     */
    boolean confirmAppNotify(Map<String, String> notifyParams);
}
