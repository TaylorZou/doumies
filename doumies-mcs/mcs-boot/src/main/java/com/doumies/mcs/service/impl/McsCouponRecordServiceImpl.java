package com.doumies.mcs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.mcs.mapper.McsCouponRecordMapper;
import com.doumies.mcs.pojo.domain.McsCouponRecord;
import com.doumies.mcs.service.IMcsCouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @desc 优惠券领取记录业务实现类
 * @author Taylor
 * @date 2021/3/14
 */
@Slf4j
@Service
public class McsCouponRecordServiceImpl extends ServiceImpl<McsCouponRecordMapper, McsCouponRecord> implements IMcsCouponRecordService {
}
