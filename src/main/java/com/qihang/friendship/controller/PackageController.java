package com.qihang.friendship.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qihang.friendship.common.Constants;
import com.qihang.friendship.controller.vo.PackageVo;
import com.qihang.friendship.dao.entity.Package;
import com.qihang.friendship.service.IPackageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.controller.PackageController </p>
 * <p>创建时间: 2019/3/17 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RestController
@RequestMapping(Constants.URL_NAMESPACE)
public class PackageController extends BaseController {
    @Autowired
    private IPackageService packageService;

    @GetMapping("/packages")
    @ResponseStatus(HttpStatus.OK)
    public List<PackageVo> listPackage() {
        List<Package> packages = packageService.list(new QueryWrapper<Package>().orderByAsc("sort"));
        return packages.stream().map(pk -> {
            PackageVo vo = new PackageVo();
            BeanUtils.copyProperties(pk, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
