package com.doumies.mcs.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @desc mybatis 分页类型转换
 * @author Taylor
 * @date 2021/3/14
 */
public class PageMapperUtils {


    public static <S, D> IPage<D> mapPage(final IPage<S> source, final Class<D> destType) {
        final IPage<D> dest = new Page<>();
        if (source == null ) {
            return dest;
        }
        dest.setCurrent(source.getCurrent());
        dest.setPages(source.getPages());
        dest.setSize(source.getSize());
        dest.setTotal(source.getTotal());
         dest.setRecords(BeanMapperUtils.mapList(source.getRecords(),destType));
        return dest;
    }

}
