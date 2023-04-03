package com.doumies.ocs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doumies.ocs.pojo.entity.OcsOrder;
import com.doumies.ocs.pojo.query.OrderPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单详情表
 *
 * @author Taylor
 * @date 2021-12-13
 */
@Mapper
public interface OrderMapper extends BaseMapper<OcsOrder> {

    /**
     * 订单分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<OcsOrder> listUsersWithPage(Page<OcsOrder> page, OrderPageQuery queryParams);
}
