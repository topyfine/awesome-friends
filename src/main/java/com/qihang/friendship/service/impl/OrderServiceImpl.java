package com.qihang.friendship.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qihang.friendship.dao.entity.Order;
import com.qihang.friendship.dao.mapper.OrderMapper;
import com.qihang.friendship.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.service.impl.OrderServiceImpl </p>
 * <p>创建时间: 2019/3/16 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
