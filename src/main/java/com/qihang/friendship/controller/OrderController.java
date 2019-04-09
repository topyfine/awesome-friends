package com.qihang.friendship.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qihang.friendship.common.Constants;
import com.qihang.friendship.controller.annotation.ValidIdentity;
import com.qihang.friendship.controller.exception.BadRequestException;
import com.qihang.friendship.controller.vo.OrderVo;
import com.qihang.friendship.dao.entity.Order;
import com.qihang.friendship.dao.entity.Package;
import com.qihang.friendship.service.IAlipayService;
import com.qihang.friendship.service.IOrderService;
import com.qihang.friendship.service.IPackageService;
import com.qihang.friendship.util.BeanConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.OrderController </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
@RequestMapping(Constants.URL_NAMESPACE)
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPackageService packageService;
    @Autowired
    private IAlipayService alipayService;

    /**
     * 查询全部订单
     *
     * @return
     */
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderVo> listOrder() {
        List<Order> orders = orderService.list();
        return orders.stream().map(order -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            return orderVo;
        }).collect(Collectors.toList());
    }

    /**
     * 创建订单
     *
     * @param identityNo 会员识别号
     * @param packageId  套餐id
     * @return
     */
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @ValidIdentity
    public OrderVo addOrder(String identityNo, String packageId) {
        // 校验套餐、创建商户订单
        Package pack = packageService.getById(packageId);
        if (pack == null) {
            throw new BadRequestException(String.format("套餐id:[%s]不存在", packageId));
        }
        Order newOrder = BeanConverter.convert(() -> {
            Order order = new Order();
            order.setPackageName(pack.getName());
            order.setPackageDuration(pack.getDuration());
            order.setPackagePrice(pack.getPrice());
            order.setAmount(pack.getPrice());
            order.setOrderNo(IdWorker.getTimeId());
            order.setMemberIdentityNo(identityNo);
            return order;
        });
        orderService.save(newOrder);
        // 获取app支付请求参数
        String orderinfo = alipayService.appPrepay(newOrder);
        // 响应客户端
        Order order = orderService.getById(newOrder.getId());
        return BeanConverter.convert(() -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            orderVo.setOrderinfo(orderinfo);
            return orderVo;
        });
    }
}
