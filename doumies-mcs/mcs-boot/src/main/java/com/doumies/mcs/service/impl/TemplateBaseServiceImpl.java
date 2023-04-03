package com.doumies.mcs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.doumies.common.web.exception.BizException;
import com.doumies.mcs.pojo.domain.McsCouponTemplate;
import com.doumies.mcs.pojo.vo.CouponTemplateVO;
import com.doumies.mcs.service.IMcsCouponTemplateService;
import com.doumies.mcs.service.ITemplateBaseService;
import com.doumies.mcs.util.BeanMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @desc：优惠券模板基础业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Service
public class TemplateBaseServiceImpl implements ITemplateBaseService {

    @Autowired
    private IMcsCouponTemplateService couponTemplateService;

    @Override
    public CouponTemplateVO queryTemplateInfo(Long id) {
        McsCouponTemplate template = couponTemplateService.getById(id);
        if (null == template) {
            throw new BizException("Template Is Not Exist: " + id);
        }
        return BeanMapperUtils.map(template,CouponTemplateVO.class);
    }

    @Override
    public List<McsCouponTemplate> findAllUsableTemplate() {
        return couponTemplateService.findAllUsableTemplate(1, 0);
    }

    @Override
    public Map<Long, McsCouponTemplate> findAllTemplateByIds(Collection<Long> ids) {
        List<McsCouponTemplate> templates = couponTemplateService.listByIds(ids);
        if (CollUtil.isEmpty(templates)) {
            return MapUtil.empty();
        }
        return templates.stream()
                .collect(Collectors.toMap(McsCouponTemplate::getId, Function.identity()));
    }
}
