package com.doumies.mcs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.common.web.exception.BizException;
import com.doumies.mcs.mapper.McsCouponTemplateMapper;
import com.doumies.mcs.pojo.domain.CouponTemplateRule;
import com.doumies.mcs.pojo.domain.McsCouponTemplate;
import com.doumies.mcs.pojo.enums.CouponCategoryEnum;
import com.doumies.mcs.pojo.enums.CouponOfferStateEnum;
import com.doumies.mcs.pojo.enums.CouponVerifyStateEnum;
import com.doumies.mcs.pojo.enums.UserTypeEnum;
import com.doumies.mcs.pojo.form.CouponTemplateForm;
import com.doumies.mcs.pojo.vo.McsCouponTemplateInfoVO;
import com.doumies.mcs.pojo.vo.McsCouponTemplateVO;
import com.doumies.mcs.service.IAsyncService;
import com.doumies.mcs.service.IMcsCouponTemplateService;
import com.doumies.mcs.util.BeanMapperUtils;
import com.doumies.mcs.util.PageMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @desc: 优惠券模板业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Slf4j
@Service
public class McsCouponTemplateServiceImpl extends ServiceImpl<McsCouponTemplateMapper, McsCouponTemplate>
        implements IMcsCouponTemplateService {

    @Autowired
    private IAsyncService asyncService;

    @Override
    public IPage<McsCouponTemplateVO> pageQuery(Integer pageNum, Integer limit, String name) {
        Page<McsCouponTemplate> page = new Page<>(pageNum, limit);
        QueryWrapper<McsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        Page<McsCouponTemplate> pageResult = this.page(page, queryWrapper);
        return PageMapperUtils.mapPage(pageResult, McsCouponTemplateVO.class);
    }

    @Override
    public McsCouponTemplate createTemplate(CouponTemplateForm form) {
        log.info("Create Coupon Template , form={}", form);
        // 1、form 表单参数校验
        if (null != findByCouponTemplateName(form.getName())) {
            throw new BizException("Coupon Template Name Exist");
        }

        // 构造 CouponTemplate 并保存到数据库
        McsCouponTemplate template = formToTemplate(form);
        this.save(template);
        return template;
    }

    @Override
    public McsCouponTemplate updateTemplate(CouponTemplateForm form) {
        log.info("Update Coupon Template, ID={}, form={}", form.getId(), form);
        // 1、判断优惠券的名称有没有变化
        McsCouponTemplate template = findByTemplateId(form.getId());
//        if (CouponTemplateStateEnum.INIT != template.getState()) {
//            throw new BizException("Coupon Template State Not Init");
//        }
        if (!StringUtils.equals(template.getName(), form.getName())) {
            // 如果form提交的名称不一致，需要判断名称是否重复
            if (null != findByCouponTemplateName(form.getName())) {
                throw new BizException("Coupon Template Name Exist");
            }
        }

        // 2、将修改后的数据，覆盖原数据
        BeanMapperUtils.copy(form, template);
        this.updateById(template);

        return template;
    }

    @Override
    public void deleteTemplate(String id) {
        log.info("Delete Coupon Template, ID={}", id);
        // 1、判断优惠券的名称有没有变化
        McsCouponTemplate template = findByTemplateId(id);
        if (template.getVerifyState() != CouponVerifyStateEnum.INIT) {
            throw new BizException("当前优惠券模板记录已生效，无法删除！");
        }
        this.removeById(id);
    }

    @Override
    public void confirmTemplate(String id) {
        // 1、校验优惠券状态
        McsCouponTemplate template = findByTemplateId(id);
//        if (CouponTemplateStateEnum.INIT != template.getState()) {
//            log.info("Finish Confirm Coupon Template State Not Init, TemplateID={}.", id);
//            return;
//        }

        // 2、修改优惠券模板状态，生成优惠券模板编码
//        template.setState(CouponTemplateStateEnum.USED);
        template.setCode(template.getCategory() +
                DateUtil.today().replace("-", "") +
                template.getId());
        this.save(template);

        // 3、根据优惠券模板异步生成优惠券码，放入Redis 缓存中，等待用户领取
        asyncService.asyncConstructCouponByTemplate(template);
        log.info("Finish Confirm Coupon Template, TemplateID={}.", id);
    }


    @Override
    public List<McsCouponTemplate> findAllUsableTemplate(Integer available, Integer expired) {
        QueryWrapper<McsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", available)
                .eq("expired", expired);
        return this.list(queryWrapper);
    }

    @Override
    public List<McsCouponTemplate> findAllNotExpiredTemplate(Integer verifyStateCode, Integer usedStateCode) {
        QueryWrapper<McsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("verify_state", verifyStateCode);
        queryWrapper.lt("used_state", usedStateCode);
        return this.list(queryWrapper);
    }

    @Override
    public McsCouponTemplateInfoVO info(String id) {
        log.info("Query Coupon Template Info,ID={}", id);
        McsCouponTemplate template = findByTemplateId(id);
        return BeanMapperUtils.map(template, McsCouponTemplateInfoVO.class);
    }

    @Override
    public List<McsCouponTemplate> findAllOfferingTemplate(Set<Integer> userTypes) {
        // TODO 优惠券模板是实际业务中请求量比较大的业务，建议将这部分数据放入 Redis 中
        QueryWrapper<McsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("offer_state", CouponOfferStateEnum.GOING.getCode());
        List<McsCouponTemplate> templates = list(queryWrapper);
        long nowTime = System.currentTimeMillis();
        return templates.stream()
                .filter(template -> template.getOfferEndTime().compareTo(nowTime) > 0 &&
                        userTypes.contains(template.getRule().getUserLimit().getUserType().getCode()
                 ))
                .collect(Collectors.toList());
    }

    /**
     * 根据ID查找优惠券模板
     *
     * @param id 优惠券模板ID
     * @return 优惠券模板
     */
    @Override
    public McsCouponTemplate findByTemplateId(String id) {
        McsCouponTemplate template = this.getById(id);
        if (template == null) {
            throw new BizException("Template Not Exist, ID=" + id);
        }
        return template;
    }

    /**
     * 根据模板名称查询实体类
     *
     * @param name 模板名称
     * @return 模板实体类
     */
    private McsCouponTemplate findByCouponTemplateName(String name) {
        QueryWrapper<McsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return getOne(queryWrapper);
    }

    private McsCouponTemplate formToTemplate(CouponTemplateForm form) {
        McsCouponTemplate template = new McsCouponTemplate();
        template.setName(form.getName());
        template.setLogo(form.getLogo());
        template.setCategory(CouponCategoryEnum.of(form.getCategoryCode()));
        template.setTotal(form.getTotal());
        template.setDescription(form.getDescription());
        template.setOfferStartTime(form.getOfferTime()[0]);
        template.setOfferEndTime(form.getOfferTime()[1]);
        template.setUsedStartTime(form.getUsedTime()[0]);
        template.setUsedEndTime(form.getUsedTime()[1]);
        CouponTemplateRule rule = new CouponTemplateRule();

        CouponTemplateRule.Discount discount = new CouponTemplateRule.Discount();
        discount.setBase(form.getRule().getDiscount().getBase());
        discount.setQuota(form.getRule().getDiscount().getQuota());
        rule.setDiscount(discount);

        CouponTemplateRule.Expiration expiration = new CouponTemplateRule.Expiration();
        expiration.setGap(form.getRule().getExpiration().getGap());
        expiration.setPeriod(form.getRule().getExpiration().getPeriod());
        if (CollUtil.isNotEmpty(form.getRule().getExpiration().getTime()) && form.getRule().getExpiration().getTime().size() == 2) {
            expiration.setStartTime(form.getRule().getExpiration().getTime().get(0));
            expiration.setEndTime(form.getRule().getExpiration().getTime().get(1));
        }
        rule.setExpiration(expiration);

        CouponTemplateRule.UserLimit userLimit = new CouponTemplateRule.UserLimit();
        userLimit.setUserType(UserTypeEnum.of(form.getRule().getUserLimit().getUserType()));
        userLimit.setLimit(form.getRule().getUserLimit().getLimit());
        rule.setUserLimit(userLimit);

        rule.setWeight(form.getRule().getWeight());
        rule.setGoodsCategories(form.getRule().getGoodsCategories());

        template.setRule(rule);
        return template;
    }
}
