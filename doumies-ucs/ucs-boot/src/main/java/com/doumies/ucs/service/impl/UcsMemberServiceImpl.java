package com.doumies.ucs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.pcs.pojo.vo.ProductHistoryVO;
import com.doumies.ucs.constant.UcsConstants;
import com.doumies.ucs.mapper.UcsUserMapper;
import com.doumies.ucs.pojo.entity.UcsMember;
import com.doumies.ucs.service.IUcsMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UcsMemberServiceImpl extends ServiceImpl<UcsUserMapper, UcsMember> implements IUcsMemberService {

    private final RedisTemplate redisTemplate;

    @Override
    public IPage<UcsMember> list(Page<UcsMember> page, String nickname) {
        List<UcsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = UcsConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(UcsConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }
}
