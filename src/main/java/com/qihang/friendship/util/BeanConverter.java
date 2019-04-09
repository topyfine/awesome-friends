package com.qihang.friendship.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.util.BeanConverter </p>
 * <p>创建时间: 2019/3/18 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
public class BeanConverter {
    public static <T, R> R convert(T t, Function<T, R> function) {
        return function.apply(t);
    }

    public static <T> T convert(Supplier<T> supplier) {
        return supplier.get();
    }
}
