package com.doumies.pcs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doumies.pcs.mapper.PcsBrandMapper;
import com.doumies.pcs.pojo.entity.PcsBrand;
import com.doumies.pcs.service.IPcsBrandService;
import org.springframework.stereotype.Service;

@Service
public class PcsBrandServiceImpl extends ServiceImpl<PcsBrandMapper, PcsBrand> implements IPcsBrandService {
}
