package com.qihang.friendship;

import com.qihang.friendship.dao.entity.Package;
import com.qihang.friendship.service.IPackageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.DatabaseTest </p>
 * <p>创建时间: 2019/3/19 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTest {
    @Autowired
    private IPackageService packageService;

    @Test
    public void testSql() {
        /*Package aPackage = new Package();
        aPackage.setName("33");
        aPackage.setDuration(1);
        aPackage.setPrice(BigDecimal.TEN);
        aPackage.setSort(1);
        packageService.save(aPackage);*/

        /*Package pack = new Package();
        pack.setId(7);
        pack.setPrice(BigDecimal.ONE);
        packageService.updateById(pack);*/
    }
}
